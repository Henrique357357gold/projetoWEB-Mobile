const admin = require("firebase-admin");

const checkSessionCookie = async (req, res, next) => {
  const sessionCookie = req.cookies.session || "";

  try {
    const decodedClaims = await admin
      .auth()
      .verifySessionCookie(sessionCookie, true);
    req.user = decodedClaims; // Armazena os dados do usuário no objeto req
    next(); // Permite continuar para a próxima rota
  } catch (error) {
    console.error("Erro ao verificar o session cookie:", error);
    res.redirect("/login"); // Redireciona para login se o cookie for inválido ou não existir
  }
};

module.exports = checkSessionCookie;
