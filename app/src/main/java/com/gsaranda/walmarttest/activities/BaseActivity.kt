package com.gsaranda.walmarttest.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gsaranda.walmarttest.R

abstract class BaseActivity : AppCompatActivity(){

    protected var estaEnPausa=false


    protected open fun onUiReady(){}

    protected fun despliegaModal(message:String,textAccept:String,aceptCallback:(dialog:DialogInterface,id:Int)->Unit){
        val dialogBuilder = AlertDialog.Builder(this)


        dialogBuilder.setTitle(getString(R.string.app_name))
        dialogBuilder.setMessage(message)
            .setCancelable(false)

            .setPositiveButton(textAccept, aceptCallback)
            // negative button text and action
            .setNegativeButton(getString(R.string.modal_cancel), {
                    dialog, id -> dialog.cancel()
            }).show()

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