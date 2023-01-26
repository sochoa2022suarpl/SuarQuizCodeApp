package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    //Variables ViewBinding
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ToDo IMPLEMENTAR CONFIRMACIÓN DE SALIDA
        /*Asignando al botón back una acción personalizada
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(activity, "Test back", Toast.LENGTH_LONG).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        */

    }
    //Metemos ViewBinding al fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //Probando ViewBinding

        //TODO CAMBIAR ESTE LOGIN PROVISIONAL EN FIREBASE E INTEGRAR EN MVVM
        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        binding.buttonLogin.setOnClickListener{
            var password = binding.editTextTextPassword.text.toString()
            var mail = binding.editTextTextEmailAddress.text.toString()

            if (mail == "suar@prueba.com" && password == "1234"){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                Toast.makeText(activity, "Usuario no registrado", Toast.LENGTH_LONG).show()
            }

        }

    }
}