// Firebase and Google Drive Configuration
// These values are typically injected at build time or handled via environment variables
// For this web-based frontend in assets, we use a central config object

const firebaseConfig = {
    apiKey: "FIREBASE_API_KEY_PLACEHOLDER",
    authDomain: "FIREBASE_AUTH_DOMAIN_PLACEHOLDER",
    projectId: "FIREBASE_PROJECT_ID_PLACEHOLDER",
    storageBucket: "FIREBASE_STORAGE_BUCKET_PLACEHOLDER",
    messagingSenderId: "FIREBASE_MESSAGING_SENDER_ID_PLACEHOLDER",
    appId: "FIREBASE_APP_ID_PLACEHOLDER"
};

const googleDriveConfig = {
    clientId: "GOOGLE_DRIVE_CLIENT_ID_PLACEHOLDER",
    apiKey: "GOOGLE_DRIVE_API_KEY_PLACEHOLDER",
    scopes: "https://www.googleapis.com/auth/drive.file https://www.googleapis.com/auth/drive.install",
    discoveryDocs: ["https://www.googleapis.com/discovery/v1/apis/drive/v3/rest"]
};

export { firebaseConfig, googleDriveConfig };
