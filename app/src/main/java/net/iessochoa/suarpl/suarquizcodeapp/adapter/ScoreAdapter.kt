package net.iessochoa.suarpl.suarquizcodeapp.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.model.Score

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {
    private val scoresList = mutableListOf<Score>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }


    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scoresList[position]
        holder.bind(score)
    }

    override fun getItemCount(): Int {
        return scoresList.size
    }

    fun setScores(scores: List<Score>) {
        scoresList.clear()
        scoresList.addAll(scores)
        notifyDataSetChanged()
    }

    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val coinsTextView: TextView = itemView.findViewById(R.id.coinsTextView)

        fun bind(score: Score) {
            nameTextView.text = score.name
            coinsTextView.text = score.coins
        }
    }
}
