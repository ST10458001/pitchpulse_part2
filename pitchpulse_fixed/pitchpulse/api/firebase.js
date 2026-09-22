const admin = require('firebase-admin');

let credential;
if (process.env.FIREBASE_SERVICE_ACCOUNT_JSON) {
  credential = admin.credential.cert(JSON.parse(process.env.FIREBASE_SERVICE_ACCOUNT_JSON));
} else {
  credential = admin.credential.cert(require('./serviceAccountKey.json'));
}

admin.initializeApp({ credential });

const db = admin.firestore();

module.exports = { admin, db };
