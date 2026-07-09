-- Lupanulla Production Database Schema (PostgreSQL)

-- 1. Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT, -- Nullable for OAuth users
    role VARCHAR(20) DEFAULT 'student', -- 'student', 'teacher', 'admin'
    avatar_url TEXT,
    streak_count INTEGER DEFAULT 0,
    last_check_in TIMESTAMP,
    provider VARCHAR(20) DEFAULT 'local', -- 'local', 'google'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Subjects Table
CREATE TABLE subjects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(50) UNIQUE NOT NULL,
    level VARCHAR(20) NOT NULL, -- 'Form 1', 'Form 2', etc.
    icon TEXT,
    color_theme VARCHAR(20)
);

-- 3. Notes Table
CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    subject_id INTEGER REFERENCES subjects(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    author_id INTEGER REFERENCES users(id),
    status VARCHAR(20) DEFAULT 'pending', -- 'pending', 'approved'
    view_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Quizzes Table
CREATE TABLE quizzes (
    id SERIAL PRIMARY KEY,
    subject_id INTEGER REFERENCES subjects(id),
    title VARCHAR(200) NOT NULL,
    questions JSONB NOT NULL, -- Array of objects: {q: "", options: [], correct: 0}
    reward_xp INTEGER DEFAULT 5
);

-- 5. User Progress (Learning Tracks)
CREATE TABLE user_progress (
    user_id INTEGER REFERENCES users(id),
    note_id INTEGER REFERENCES notes(id),
    completed BOOLEAN DEFAULT FALSE,
    last_read TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, note_id)
);
