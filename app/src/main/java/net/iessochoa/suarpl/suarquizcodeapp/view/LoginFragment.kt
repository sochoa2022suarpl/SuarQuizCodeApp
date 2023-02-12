package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding
import kotlin.system.exitProcess


class LoginFragment : Fragment() {

    //Variables ViewBinding
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Asignando al botón back una acción personalizada para no volver al Splash
        y poder salir de la app
         */
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val salirDialog = AlertDialog.Builder(activity)
                salirDialog.setMessage("¿Quieres salir de la aplicación ???")
                    .setCancelable(false)
                    .setPositiveButton("Sí") { _, _ -> exitProcess(0) }
                    .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                val alert = salirDialog.create()
                alert.setTitle("Salir")
                alert.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    //ViewBinding al fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

        //TODO CAMBIAR ESTE LOGIN PROVISIONAL EN FIREBASE E INTEGRAR EN MVVM
        /*
        Autenticación rudimentaria para dar funcionalidad al
        wireframe, se implementará autenticación
        desde Firebase auth/ROOM
         */
        binding.buttonLogin.setOnClickListener{
            var password = binding.editTextTextPassword.text.toString()
            var mail = binding.editTextTextEmailAddress.text.toString()

            if (mail == "suar@prueba.com" && password == "1234"){
            //if (mail.isValidEmail() && password == "1234"){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                Toast.makeText(activity, "Usuario no registrado", Toast.LENGTH_LONG).show()
            }
        }
    }
    /*
    Validador usando API de Google, pero no interesa por menor compatibilidad
    de dispositivos
     */
    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}