package net.iessochoa.suarpl.suarquizcodeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategory

class CategoryAdapter(
    private var qzCategoryList: List<QzCategory>
) : RecyclerView.Adapter<CategoryViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = qzCategoryList[position]
        holder.render(item)

    }

    override fun getItemCount(): Int =qzCategoryList.size

    fun updateQuizList(qzCategoryList: List<QzCategory>){
        this.qzCategoryList = qzCategoryList
        notifyDataSetChanged()
    }

}