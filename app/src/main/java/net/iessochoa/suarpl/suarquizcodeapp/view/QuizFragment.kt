package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentQuizBinding
import net.iessochoa.suarpl.suarquizcodeapp.viewmodel.QuestionViewModel

class QuizFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    //Variables para recuperar arguments desde HomeFragment
    private val args: QuizFragmentArgs by navArgs()
    private var secsLeft = 0
    //Temporizador
    private lateinit var timer: CountDownTimer
    //Índice pregunta actual
    private var currentQuestionNumber: Int = 0
    //Valor de la respuesta actual
    private var answer : String =""
    //Variable para recuperar categoría seleccionada en home
    private lateinit var selectedCategory : String
    //Booleana para poder contestar
    var canAnswer : Boolean = false
    //Recuento de aciertos/errores
    var rightAnswers : Int = 0
    var wrongAnswers : Int = 0
    //ViewModel
    private val questionViewModel : QuestionViewModel by viewModels()

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
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Recuperamos la categoría seleccionada en el RecyclerView
        loadCategory()
        //Cargamos las preguntas usando el ViewModel/LiveData
        loadQuestions(currentQuestionNumber)
        //Iniciamos temporizador
        startTimerQuiz()
        //Seteamos la comprobación de respuestas en los botones correspondientes
        setButtonBindCheck()

        //Binds onclick
        binding.btVolverHome.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
            timer.cancel()
        }
    }
    //Método comprobación de respuestas
    private fun checkAnswer(button: Button) {
        if (button.text==answer){
            rightAnswers++
            Toast.makeText(activity, getString(R.string.resp_correcta), Toast.LENGTH_SHORT).show()
        }else{
            wrongAnswers++
            Toast.makeText(activity, getString(R.string.resp_incorrecta), Toast.LENGTH_SHORT).show()
        }
        nextQuestion()
    }

    /*
    Método para controlar el temporizador
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
                timer.cancel()
                canAnswer=false
                goToResults()
            }
        }.start()
    }

    //Método para cargar pregunta/respuestas usando ViewModel/LiveData/Repository desde Firebase
    private fun loadQuestions(indice: Int){
        questionViewModel.getLiveDataFromCategory(selectedCategory).observe(viewLifecycleOwner, Observer {
            val i = indice
            answer = it[i].answer.toString()
            //Fijamos valores de cada objeto (Pregunta y botones) usando el índice de este
            binding.tvPregunta.text = it[i].question.toString()
            binding.btRespuesta1.text = it[i].option1.toString()
            binding.btRespuesta2.text = it[i].option2.toString()
            binding.btRespuesta3.text = it[i].option3.toString()
            binding.btRespuesta4.text = it[i].option4.toString()
            //Elementos UI, contador de preguntas
            binding.tvCurrentPreg.text = (i+1).toString()
            binding.tvTotalPregunta.text = it.size.toString()
        })
        //Permitimos contestar
        canAnswer = true
    }


    //Método para recuperar categoría seleccionada por safeargs
    fun loadCategory(){
        selectedCategory = args.category
    }

    //Asignación de comprobación de respuesta correcta según botón pulsado
    fun setButtonBindCheck(){
        if(canAnswer){
            binding.btRespuesta1.setOnClickListener{checkAnswer(binding.btRespuesta1)}
            binding.btRespuesta2.setOnClickListener{checkAnswer(binding.btRespuesta2)}
            binding.btRespuesta3.setOnClickListener{checkAnswer(binding.btRespuesta3)}
            binding.btRespuesta4.setOnClickListener{checkAnswer(binding.btRespuesta4)}
        }else{
            //No permite contestar, pero es redundante
        }
    }
    //Pasamos a la siguiente pregunta, actualizamos contadores de aciertos/fallos
    fun nextQuestion(){
        if (currentQuestionNumber<9){
            currentQuestionNumber++
            loadQuestions(currentQuestionNumber)
        } else{
            timer.cancel()
            canAnswer=false
            goToResults()
        }
    }
    //Ir al Fragment resultado pasando argumentos de acertadas/fallidas y la dificultad
    fun goToResults(){
        binding.apply {
            val next = QuizFragmentDirections.actionQuizFragmentToResultsFragment(secsLeft,rightAnswers,wrongAnswers)
            findNavController().navigate(next)
        }
    }



}