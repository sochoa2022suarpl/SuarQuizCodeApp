package net.iessochoa.suarpl.suarquizcodeapp.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    //Firebase
    val db = Firebase.firestore
    //variable puntuacion
    var totalScore:Int = 0
    var currentCoins:Int =0

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
        getUserCoins()
        
    }
    private fun showResults(){
        totalScore = rightAnswers.toInt()*100
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
    //Método que obtiene las monedas actuales del usuario logueado
    //Y le suma las ganadas
    private fun getUserCoins(){
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.get("coins") != null) {
                    currentCoins = document.get("coins").toString().toInt()
                    println("Actual" + currentCoins)
                    totalScore += currentCoins
                    println("Total"+totalScore)
                    updateUserCoins()
                } else {

                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }

    private fun updateUserCoins() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)
        docRef.update("coins", (totalScore).toString())

    }

}