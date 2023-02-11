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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentQuizBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModel
import net.iessochoa.suarpl.suarquizcodeapp.model.QuestionModelProvider
import net.iessochoa.suarpl.suarquizcodeapp.viewmodel.QuestionViewModel

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
    //Variable para recuperar categoría seleccionada en home
    private lateinit var selectedCategory : String
    //Booleana para poder contestar
    var canAnswer : Boolean = false
    //Recuento de aciertos/errores
    var rightAnswers : Int = 0
    var wrongAnswers : Int = 0

    //ViewModel
    private val questionViewModel : QuestionViewModel by viewModels()

    private var questionModelList : List<QuestionModel> = QuestionModelProvider.questionModelProviderListKotlin.shuffled()

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
            Toast.makeText(activity, "Respuesta correcta ", Toast.LENGTH_SHORT).show()
        }else{
            wrongAnswers++
            Toast.makeText(activity, "Respuesta Incorrecta", Toast.LENGTH_SHORT).show()
        }
        nextQuestion()
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

    //Método para cargar pregunta/respuestas usando ViewModel/LiveData
    fun loadQuestions(int: Int){
        questionViewModel.getLiveDataFromCategory(selectedCategory).observe(viewLifecycleOwner, Observer {
            var i = int
            answer = it[i].answer
            //Pregunta y botones
            binding.tvPregunta.text = it[i].question
            binding.btRespuesta1.text = it[i].option1
            binding.btRespuesta2.text = it[i].option2
            binding.btRespuesta3.text = it[i].option3
            binding.btRespuesta4.text = it[i].option4
            //Elementos UI, contador de preguntas
            binding.tvCurrentPreg.text = (i+1).toString()
            binding.tvTotalPregunta.text = it.size.toString()

        })
        canAnswer = true
    }

    //Método para recuperar categoría seleccionada por safeargs
    fun loadCategory(){
        selectedCategory = args.category
    }

    //Asignación de comprobación de respuestas a botones
    fun setButtonBindCheck(){
        if(canAnswer==true){
            binding.btRespuesta1.setOnClickListener{checkAnswer(binding.btRespuesta1)}
            binding.btRespuesta2.setOnClickListener{checkAnswer(binding.btRespuesta2)}
            binding.btRespuesta3.setOnClickListener{checkAnswer(binding.btRespuesta3)}
            binding.btRespuesta4.setOnClickListener{checkAnswer(binding.btRespuesta4)}
        }else{

        }

    }
    fun setDefaults(){
        rightAnswers = 0
        wrongAnswers = 0

    }
    fun nextQuestion(){
        if (currentQuestionNumber<questionModelList.size-1){
            currentQuestionNumber++
            loadQuestions(currentQuestionNumber)
        } else{
            Toast.makeText(activity,
                "Se acabó el cuestionario, ACERTADAS: $rightAnswers FALLIDAS: $wrongAnswers", Toast.LENGTH_LONG).show()
            timer.cancel()
            canAnswer=false
            goToResults()
        }
    }

    //Ir al Fragment resultado pasando argumentos
    fun goToResults(){
        binding.apply {
            val next = QuizFragmentDirections.actionQuizFragmentToResultsFragment(secsLeft,rightAnswers,wrongAnswers)
            findNavController().navigate(next)
        }

    }



}