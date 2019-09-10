package com.gsaranda.walmarttest.activities

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

    protected var estaEnPausa=false


    protected open fun onUiReady(){}

    //se setea el tema por default para evitar la pantalla blanca al iniciar por primera vez

    protected  fun cambiaActividad( actividad: Class<out Activity>) {
        val intent= Intent(this,actividad)
        startActivity(intent)
        finish()
    }


    override fun onPause() {
        super.onPause()
        estaEnPausa=true
    }

    override fun onResume() {
        super.onResume()
        if(estaEnPausa)
            estaEnPausa=!estaEnPausa
        else{
            // definimos metodo en el cual se hacen animaciones u otras operaciones ependientes de que el UI este listo
            //Lo cual solo pasa caundo se ejecuta el onResume por primera vez
            onUiReady()
        }
    }

}