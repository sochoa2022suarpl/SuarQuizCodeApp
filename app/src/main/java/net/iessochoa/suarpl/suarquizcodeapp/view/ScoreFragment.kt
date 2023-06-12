package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.adapter.ScoreAdapter
import net.iessochoa.suarpl.suarquizcodeapp.model.Score

class ScoreFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScoreAdapter
    private lateinit var buttonBack: Button
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val scoresCollection = firestore.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
      
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewScore)
        buttonBack = view.findViewById(R.id.btBack)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ScoreAdapter()
        recyclerView.adapter = adapter

        fetchScoresFromFirestore()

        buttonBack.setOnClickListener {
            val next = ScoreFragmentDirections.actionScoreFragmentToHomeFragment()
            findNavController().navigate(next)
        }
        return view
    }

    private fun fetchScoresFromFirestore() {
        scoresCollection.orderBy("coins", Query.Direction.DESCENDING).limit(25)
            .get()
            .addOnSuccessListener { documents ->
                val scoresList = mutableListOf<Score>()
                for (document in documents) {
                    val name = document.getString("name")
                    val coins = document.getString("coins")
                    if (name != null && coins != null) {
                        scoresList.add(Score(name, coins))

                    }
                }
                adapter.setScores(scoresList)
            }
            .addOnFailureListener { exception ->

            }
    }
}
