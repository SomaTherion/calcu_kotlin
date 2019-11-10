package com.example.calculadora

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.WebViewClient


class MainActivity : AppCompatActivity() {

    var a=0.00
    var b=0.00
    var cuenta = 0.00
    var op = 'n'
    var mem=0.00
    var temp = 0.00
    var operar = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide(); // hide the title bar
        setContentView(R.layout.activity_main)
        val web = WebView(this)

        //controlamos orientacion
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            //Si es horizontal cargamos navegador web
            web.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            web.loadUrl("https://www.google.es/")
        }

    }

        //funciones

    fun cargarWeb(v: View){
        var substring = tf_url.text.toString().substring(0,7)
            if (substring.equals("http://") || substring.equals("https:/")){
                web.loadUrl(tf_url.text.toString())
            }
            else{
                web.loadUrl("https://www.google.es/search?q="+tf_url.text.toString())
            }
        }




    fun numero(v: View){
        val botonPulsado = findViewById<Button>(v.id)

        if(resultado.text.equals("0.0") || resultado.text.equals("error")){
            if(botonPulsado==suma || botonPulsado==restar || botonPulsado==multi){
                resultado.text = "0.0"
            }
            else if (botonPulsado==dividir){
                resultado.text= "error"
            }
            else{
                resultado.text = ""
            }
        }

        if(botonPulsado!=suma && botonPulsado!=igual && botonPulsado!=restar
            && botonPulsado!=multi && botonPulsado!=dividir && botonPulsado!=memo
        ){
            resultado.text = resultado.text.toString() + botonPulsado.text.toString()
        }
        when(botonPulsado){
            ce -> {resultado.text = ""
            cuenta = 0.00
            temp = 0.00
            mem = 0.00
            }
            suma -> {
                a = resultado.text.toString().toDouble()
                resultado.text = ""
                op = 's'

            }
            restar -> {
                a = resultado.text.toString().toDouble()
                resultado.text = ""
                op='r'

            }
            multi -> {
                a = resultado.text.toString().toDouble()
                resultado.text = ""
                op='m'

            }
            dividir -> {
                try{
                    a = resultado.text.toString().toDouble()
                    resultado.text = ""
                    op='d'
                }
                catch (ex : NumberFormatException){
                    errorSintactico()
                }

            }
            igual -> {
                try{
                    b = resultado.text.toString().toDouble()
                    when (op) {
                        's' -> cuenta = (a + b).toDouble()
                        'r' -> cuenta = (a - b).toDouble()
                        'm' -> cuenta = (a * b).toDouble()
                        'd' -> cuenta = (a / b).toDouble()
                    }
                    resultado.text = cuenta.toString()
                }
                catch (ex: java.lang.NumberFormatException){
                    errorSintactico()
                }
            }
            memo -> {
                if (operar){
                    mem = resultado.text.toString().toDouble()
                    mem = mem + temp
                    resultado.text = mem.toString()
                    operar = false
                }
                else{
                    temp = resultado.text.toString().toDouble()
                    resultado.text = ""
                    operar = true
                }


            }


            }

    }

    fun errorSintactico(){
        resultado.text = "error"
        a = 0.0
        b = 0.0
        cuenta = 0.0
    }




}
