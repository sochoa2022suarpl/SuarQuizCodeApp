package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentResultsBinding


class ResultsFragment : Fragment() {
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    //Recuperando resultados
    private val arg: ResultsFragmentArgs by navArgs()
    private lateinit var rightAnswers : String
    private lateinit var wrongAnswers : String
    private lateinit var secsBonus : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btVolverHome2.setOnClickListener {
            findNavController().navigate(R.id.action_resultsFragment_to_homeFragment)
        }
        secsBonus = arg.secondsBonus.toString()
        //Recuperando valores desde Quiz
        rightAnswers = arg.resRight.toString()
        wrongAnswers = arg.resWrong.toString()



        showResults()
        
    }
    private fun showResults(){
        var totalScore = rightAnswers.toInt()*100
        binding.tvAcertadas.text = rightAnswers
        binding.tvFallidas.text = wrongAnswers
        //Penalización por 5 o más preguntas fallidas
        if (wrongAnswers.toInt()>=5){
            if (rightAnswers.toInt()>0){
                totalScore -= 100
            }
        }

        binding.tvCantidadMonedas.text = totalScore.toString()
    }

}