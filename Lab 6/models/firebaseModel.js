// firebaseModel.js

const admin = require("firebase-admin");
const serviceAccount = require("../firebase-adminsdk.json"); // Firebase service account key

if (!admin.apps.length) {
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://mdev1004-lab6-default-rtdb.firebaseio.com",
  });
}
module.exports = admin;
