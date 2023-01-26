package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentHomeBinding
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding

class HomeFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}