// firebaseModel.js

const admin = require("firebase-admin");
const serviceAccount = require("../mdev1004-lab6-firebase-adminsdk-8dn4q-a2683ea76a.json"); // Firebase service account key

if (!admin.apps.length) {
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://mdev1004-lab6-default-rtdb.firebaseio.com",
  });
}
module.exports = admin;
