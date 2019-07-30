package com.gsaranda.walmarttest.fragments

import android.os.Handler
import androidx.fragment.app.Fragment


open abstract class BaseFragment: Fragment(){
var estaEnPausa=false
val animHandler:Handler=Handler()

    override fun onPause() {
        super.onPause()
        estaEnPausa=true
    }

    override fun onResume() {
        super.onResume()
        if(estaEnPausa)
            estaEnPausa=!estaEnPausa
        else {
            animHandler.postDelayed({
                animaEntrada()
            }, 200)
        }

    }

    override fun onDetach() {
        super.onDetach()
        animHandler.removeCallbacksAndMessages(null)
    }

    //funciones para ser sobre escritas
    protected open fun animaEntrada() {}
}