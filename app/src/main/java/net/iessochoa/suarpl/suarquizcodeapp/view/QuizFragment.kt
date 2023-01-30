package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentHomeBinding
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    //Variables para recuperar segundos restantes desde HomeFragment
    private val args: QuizFragmentArgs by navArgs()
    private var secsLeft = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Anular back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recuperando segundos restantes
        secsLeft = args.secondsLeft

        //asignando valores al tetxview
        binding.tvTimer.text = secsLeft.toString()

        binding.btVolverHome.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
        }
    }

}