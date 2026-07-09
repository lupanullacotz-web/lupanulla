const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const morgan = require('morgan');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const { Pool } = require('pg');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(helmet());
app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

// PostgreSQL Connection
const pool = new Pool({
    connectionString: process.env.DATABASE_URL,
    ssl: { rejectUnauthorized: false }
});

// --- AUTH MIDDLEWARE ---
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) return res.status(401).json({ error: 'Mamlaka inahitajika (Access Denied)' });

    jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
        if (err) return res.status(403).json({ error: 'Token sio halali' });
        req.user = user;
        next();
    });
};

// --- AUTH ROUTES ---

// 1. Local Registration
app.post('/api/auth/register', async (req, res) => {
    try {
        const { fullName, email, password } = req.body;
        const hashedPassword = await bcrypt.hash(password, 10);
        
        const result = await pool.query(
            'INSERT INTO users (full_name, email, password_hash) VALUES ($1, $2, $3) RETURNING id, full_name, role',
            [fullName, email, hashedPassword]
        );
        
        res.status(201).json(result.rows[0]);
    } catch (err) {
        res.status(500).json({ error: 'Imeshindikana kusajili mtumiaji' });
    }
});

// 2. Local Login (JWT)
app.post('/api/auth/login', async (req, res) => {
    try {
        const { email, password } = req.body;
        const user = await pool.query('SELECT * FROM users WHERE email = $1', [email]);
        
        if (user.rows.length === 0) return res.status(400).json({ error: 'Mtumiaji hajapatikana' });

        const validPassword = await bcrypt.compare(password, user.rows[0].password_hash);
        if (!validPassword) return res.status(400).json({ error: 'Nenosiri sio sahihi' });

        const token = jwt.sign(
            { id: user.rows[0].id, role: user.rows[0].role },
            process.env.JWT_SECRET,
            { expiresIn: '7d' }
        );

        res.json({ token, user: { name: user.rows[0].full_name, role: user.rows[0].role } });
    } catch (err) {
        res.status(500).json({ error: 'Tatizo la kuingia' });
    }
});

// 3. OAuth2 Handshake Placeholder (Google)
app.post('/api/auth/oauth-google', async (req, res) => {
    const { googleToken } = req.body;
    // In production, verify this token with Google Auth Library
    // If valid, find or create user and return our own JWT
    res.json({ message: "Google OAuth structures are ready for proxying" });
});

// --- RESOURCE ROUTES ---

// Get All Subjects
app.get('/api/subjects', async (req, res) => {
    try {
        const result = await pool.query('SELECT * FROM subjects ORDER BY level ASC');
        res.json(result.rows);
    } catch (err) {
        res.status(500).json({ error: 'Imeshindikana kupata masomo' });
    }
});

// Search Notes
app.get('/api/notes/search', async (req, res) => {
    const { q } = req.query;
    try {
        const result = await pool.query(
            'SELECT * FROM notes WHERE title ILIKE $1 AND status = \'approved\'',
            [`%${q}%`]
        );
        res.json(result.rows);
    } catch (err) {
        res.status(500).json({ error: 'Tatizo la kutafuta' });
    }
});

// Admin Route: Approve Content
app.patch('/api/admin/approve/:id', authenticateToken, async (req, res) => {
    if (req.user.role !== 'admin') return res.status(403).json({ error: 'Si Admin' });
    
    try {
        await pool.query('UPDATE notes SET status = \'approved\' WHERE id = $1', [req.params.id]);
        res.json({ message: 'Notes zimeidhinishwa' });
    } catch (err) {
        res.status(500).json({ error: 'Tatizo la uidhinishaji' });
    }
});

// Server Listen
app.listen(PORT, () => {
    console.log(`Lupanulla Backend running on port ${PORT}`);
});
