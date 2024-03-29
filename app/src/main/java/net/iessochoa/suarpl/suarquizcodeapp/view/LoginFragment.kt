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
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding
import kotlin.system.exitProcess

//Fragmento Login
class LoginFragment : Fragment() {

    //Variables ViewBinding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //Variables login
    lateinit var password: String
    lateinit var mail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Asignando al botón back una acción personalizada para no volver al Splash
        y poder salir de la app
         */
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val salirDialog = AlertDialog.Builder(activity)
                salirDialog.setMessage(getString(R.string.quieres_salir))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.si)) { _, _ -> exitProcess(0) }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                val alert = salirDialog.create()
                alert.setTitle(getString(R.string.bt_salir))
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

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        /*
        Autenticación usando Firebase Auth
         */
        binding.buttonLogin.setOnClickListener {
            password = binding.editTextTextPassword.text.toString()
            mail = binding.editTextTextEmailAddress.text.toString()
            //Inicio de sesión Usando Coroutines
            if (mail.isValidEmail() && password.isNotEmpty()) {
                //contexto de tipo Dispatchers.IO para realizar la operación de inicio de sesión en segundo plano
                CoroutineScope(Dispatchers.IO).launch {
                    delay(3000L)
                    try {
                        //suspensión await() para que se pueda llamar dentro del contexto de IO.
                        val authResult =
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, password)
                                .await()
                        //cambio de contexto y resolución en el hilo principal
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                activity,
                                getString(R.string.ses_iniciada),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity, getString(R.string.ses_error), Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            } else {
                Toast.makeText(activity, getString(R.string.ses_notvalid), Toast.LENGTH_LONG).show()
            }
        }
    }

    /*
    Validador usando API de Google
     */
    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}