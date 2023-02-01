package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentQuizBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModelProvider

class QuizFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    //Variables para recuperar segundos restantes desde HomeFragment
    private val args: QuizFragmentArgs by navArgs()
    private var secsLeft = 0
    //Temporizador
    private lateinit var timer: CountDownTimer
    //Pregunta actual
    private var currentQuestionNumber: Int = 0
    //Valor de la respuesta actual
    private var answer : String =""

    private var questionModelList : List<QuestionModel> = QuestionModelProvider.questionModelProviderList.shuffled()

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

        //Iniciamos temporizador
        startTimerQuiz()
        loadQuestions(currentQuestionNumber)

        binding.btRespuesta1.setOnClickListener{checkAnswer(binding.btRespuesta1)}
        binding.btRespuesta2.setOnClickListener{checkAnswer(binding.btRespuesta2)}
        binding.btRespuesta3.setOnClickListener{checkAnswer(binding.btRespuesta3)}
        binding.btRespuesta4.setOnClickListener{checkAnswer(binding.btRespuesta4)}

        binding.btVolverHome.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
            timer.cancel()
        }

        /*binding.btRespuesta1.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_resultsFragment)
        }*/






    }

    private fun checkAnswer(button: Button) {
        if (button.text==answer){
            Toast.makeText(activity, "Respuesta correcta loquete", Toast.LENGTH_SHORT).show()
            if (currentQuestionNumber<questionModelList.size-1){
                currentQuestionNumber++
                loadQuestions(currentQuestionNumber)
            } else{
                Toast.makeText(activity, "Se acabó el cuestionario", Toast.LENGTH_SHORT).show()
                timer.cancel()
                findNavController().navigate(R.id.action_quizFragment_to_resultsFragment)

            }

        }else{
            Toast.makeText(activity, "Eres mu tonto", Toast.LENGTH_SHORT).show()
        }



    }

    /*
    Método para iniciar el temporizador
     */
    fun startTimerQuiz(){
        //Recuperamos los segundos con safeargs y seteamos el temporizador
        secsLeft = args.secondsLeft
        binding.tvTimer.text = secsLeft.toString()

        timer = object :CountDownTimer (secsLeft.toLong()*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(activity, "Se acabó el tiempo!!", Toast.LENGTH_SHORT).show()
            }

        }.start()


    }
    fun loadQuestions(int: Int){

        var i = int
        answer = questionModelList[i].answer
        binding.tvPregunta.text = questionModelList[i].question
        binding.btRespuesta1.text = questionModelList[i].option1
        binding.btRespuesta2.text = questionModelList[i].option2
        binding.btRespuesta3.text = questionModelList[i].option3
        binding.btRespuesta4.text = questionModelList[i].option4

    }



}