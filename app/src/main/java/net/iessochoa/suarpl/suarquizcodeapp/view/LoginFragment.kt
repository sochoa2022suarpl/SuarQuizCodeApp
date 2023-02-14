package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding
import kotlin.system.exitProcess


class LoginFragment : Fragment() {

    //Variables ViewBinding
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var password:String
    lateinit var mail:String

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
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

        /*
        Autenticación usando Firebase Auth
         */
        binding.buttonLogin.setOnClickListener{
             password = binding.editTextTextPassword.text.toString()
             mail = binding.editTextTextEmailAddress.text.toString()
/*
//Inicio de sesión normal
            if (mail.isValidEmail() && password.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                    Toast.makeText(activity, "Sesión iniciada correctamente", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }else{
                    Toast.makeText(activity, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
                     }
                    }
            }else{
                Toast.makeText(activity, "Introduce todos los caracteres", Toast.LENGTH_LONG).show()
            }

 */
            //Inicio de sesión Usando Coroutines
            if (mail.isValidEmail() && password.isNotEmpty()) {
                //contexto de tipo Dispatchers.IO para realizar la operación de inicio de sesión en segundo plano
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        //suspensión await() para que se pueda llamar dentro del contexto de IO.
                        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, password).await()
                        //cambio de contexto y resolución en el hilo principal
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity, "Sesión iniciada correctamente", Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Toast.makeText(activity, "Introduce todos los caracteres", Toast.LENGTH_LONG).show()
            }
        }
    }
    /*
    Validador usando API de Google
     */
    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}