// firebaseModel.js

const admin = require("firebase-admin");
const serviceAccount = require("../mdev1004-lab6-firebase-adminsdk-8dn4q-6561f35880.json"); // Firebase service account key

if (!admin.apps.length) {
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://mdev1004-lab6-default-rtdb.firebaseio.com",
  });
}
module.exports = admin;
