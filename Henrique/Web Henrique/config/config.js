// Imports

    // Express
    const express = require('express')

    // Handlebars
    const handlebars = require('express-handlebars').engine;

    // Body Parser
    const bodyParser = require('body-parser')

    // Firebase
    const { initializeApp } = require('firebase/app')

    // Firestore
    const { getFirestore } = require('firebase/firestore')

// End Imports

// Imports Config

    // Express Config
    const app = express()

    // Handlebars Config
    app.engine('handlebars', handlebars({ 
        defaultLayout: 'main',
        helpers: {
            eq: function (a, b) {
              return a === b;
            }
          }
     }))
    app.set('view engine', 'handlebars')
    app.use(bodyParser.urlencoded({ extended: false }))
    app.use(bodyParser.json())

    app.use(express.static('public')) // Helps to use CSS more efficiently

// End Imports Config

// Exporting modules
module.exports = {

    app

}