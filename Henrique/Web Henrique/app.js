// Imports from config folder
const { app } = require('./config/config')
const cookieParser = require("cookie-parser");

const checkSessionCookie = require("./middlewares/checkSession");

// PORT variable
const PORT = 3000;

const {
  initializeApp,
  applicationDefault,
  cert,
} = require("firebase-admin/app");

const {
    getAuth
} = require("firebase-admin/auth")

const {
  getFirestore,
  Timestamp,
  FieldValue,
} = require("firebase-admin/firestore");

const admin = require("firebase-admin")

const serviceAccount = require("./AccountService.json");

initializeApp({
  credential: cert(serviceAccount),
});

const db = getFirestore();

const auth = getAuth();

app.use(cookieParser())

// Initializing server
app.listen(PORT, () => {
    console.log(`Servidor aberto na link: http://localhost:${PORT}/`)
})

// Main route
app.get('/', checkSessionCookie, (req, res) => {
   
    res.redirect('/read')
   
})

app.get('/login', (req, res) => {

    res.render('login')
    
})

app.post("/login", async(req, res) => {

    const expiresIn = 60 * 60 * 24 * 5 * 1000

    try {
        const decodedToken = await auth.verifyIdToken(req.body.idToken)
        auth.createSessionCookie(req.body.idToken, { expiresIn })
        .then(
            (sessionCookie) => {
                const options = { maxAge: expiresIn, httpOnly: true }
                res.cookie("session", sessionCookie, options)
                console.log("Cookie Criado")
                res.end()
            }
        )
        
    } catch (e){
        console.log("Erro de Login")
    }

});

app.get('/register', (req, res) => {
    
    res.render('singup')

})

app.get('/read', checkSessionCookie, async (req, res) => {

    const dataSnapshot = await db
      .collection("Users")
      .doc(req.user.uid)
      .collection("Tenis")
      .get();
    const items = [];
    dataSnapshot.forEach((doc) => {
      items.push({
        id: doc.id,
        marca: doc.get("marca"),
        modelo: doc.get("modelo"),
        tamanho: doc.get("tamanho"),
        cor: doc.get("cor")
      });
    });

    res.render('read', { items })

})

app.get('/create', checkSessionCookie, (req, res) => {
    
    res.render("create");

})

app.post("/create", checkSessionCookie, (req, res) => {

  var result = db
    .collection("Users")
    .doc(req.user.uid)
    .collection("Tenis")
    .add({
      id: "",
      marca: req.body.marca,
      modelo: req.body.modelo,
      tamanho: req.body.tamanho,
      cor: req.body.cor
    })
    .then(() => {
      res.redirect("/read");
    });
});

app.get('/details/:id', checkSessionCookie, async (req, res) => {

    const dataSnapshot = await db
      .collection("Users")
      .doc(req.user.uid)
      .collection("Tenis")
      .doc(req.params.id)
      .get();

    const item = {
      id: dataSnapshot.id,
      marca: dataSnapshot.get("marca"),
      modelo: dataSnapshot.get("modelo"),
      tamanho: dataSnapshot.get("tamanho"),
      cor: dataSnapshot.get("cor"),
    };

    res.render('details', { item })

})

app.post("/edit", checkSessionCookie, async (req, res) => {

  db.collection("Users")
    .doc(req.user.uid)
    .collection("Tenis")
    .doc(req.body.id)
    .set({
      marca: req.body.marca,
      modelo: req.body.modelo,
      tamanho: req.body.tamanho,
      cor: req.body.cor,
    })
    .then(function () {
      console.log("Updated document");
      res.redirect("/read");
    });
});

app.post("/delete/:id", checkSessionCookie, (req, res) => {
  db.collection("Users")
    .doc(req.user.uid)
    .collection("Tenis")
    .doc(req.params.id)
    .delete()
    .then(function () {
      console.log("Deleted document");
      res.redirect("/read");
    });
});

app.get('/signout', async (req, res) => {

    res.clearCookie("session")
    res.redirect("/login")

})