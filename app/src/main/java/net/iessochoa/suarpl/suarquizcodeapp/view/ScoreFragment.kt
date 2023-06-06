package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val scoresCollection = firestore.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewScore)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ScoreAdapter()
        recyclerView.adapter = adapter

        fetchScoresFromFirestore()

        return view
    }

    private fun fetchScoresFromFirestore() {
        scoresCollection.orderBy("coins", Query.Direction.DESCENDING).limit(10)
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
                // Manejar la excepción de manera adecuada
            }
    }
}
