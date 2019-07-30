package com.gsaranda.walmarttest.activities

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActivity : AppCompatActivity(){

    protected var estaEnPausa=false


    //se setea el tema por default para evitar la pantalla blanca al iniciar por primera vez

    protected fun despliegaToastProvisional(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
    }

    protected fun despliegaModal(titulo:String,mensaje:String,esExito:Boolean,onAceptado: () -> Unit){
//        val alerta=ModalFragment()
//        alerta.setModalEsExito(esExito)
//        alerta.setTitulo(titulo)
//        alerta.setMensaje(mensaje)
//        alerta.setAceptarListener(onAceptado)
//        alerta!!.show(fragmentManager.beginTransaction(), "modal")
    }


    protected fun despliegaModalCancelar(titulo:String,mensaje:String,esExito:Boolean,onAceptado: () -> Unit){
//        val alerta=ModalFragment()
//        alerta.setModalEsExito(esExito)
//        alerta.setTitulo(titulo)
//        alerta.setCancelar(true)
//        alerta.setMensaje(mensaje)
//        alerta.setAceptarListener(onAceptado)
//        alerta!!.show(fragmentManager.beginTransaction(), "modal")
    }
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
    }

}