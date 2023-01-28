package net.iessochoa.suarpl.suarquizcodeapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.suarpl.suarquizcodeapp.databinding.ItemCategoryBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategory
/*
Recibe y procesa cada objeto recibido de la lista a través del adaptador
 */
class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val binding = ItemCategoryBinding.bind(view)

    fun render(
        qzCategoryModel: QzCategory
    ){
        binding.tvCategoryName.text = qzCategoryModel.catName

    }
}