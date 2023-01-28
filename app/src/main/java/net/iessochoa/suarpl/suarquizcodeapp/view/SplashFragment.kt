package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import net.iessochoa.suarpl.suarquizcodeapp.R


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        /*Usamos Handler para crear un subproceso
        y a través de Looper manejamos el delay de 5 segs hasta
         que navegamos hasta el siguiente Fragment de Login
         a través del NavController*/
        Handler(Looper.myLooper()!!).postDelayed({
                                                 findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

        },1000)
        return view
    }

}