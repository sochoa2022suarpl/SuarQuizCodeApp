package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentRegisterBinding
/*
ToDo implementar registro usando BD Firebase/ROOM
 */

class RegisterFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            var password = binding.RegisterPassword.text.toString()
            var mail = binding.RegisterEmailAddress.text.toString()

            if (mail.isValidEmail() && password.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,password).addOnCompleteListener {
                    Toast.makeText(activity, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }else{
                Toast.makeText(activity, "Error al registrar usuario", Toast.LENGTH_LONG).show()
            }
        }
        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


}