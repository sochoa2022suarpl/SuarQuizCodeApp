package net.iessochoa.suarpl.suarquizcodeapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.iessochoa.suarpl.suarquizcodeapp.databinding.ItemCategoryBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategory
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProvider

/*
Recibe y procesa cada objeto recibido de la lista a través del adaptador
 */
class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val binding = ItemCategoryBinding.bind(view)

    fun render(
        qzCategoryModel: QzCategory,
        onClickListener: (QzCategory) -> Unit
    ){
        binding.tvCategoryName.text = qzCategoryModel.catName
        Glide.with(binding.ivLanguageLogo.context).load(qzCategoryModel.catPhoto).into(binding.ivLanguageLogo)

        binding.cvItemCategory.setOnClickListener{
            onClickListener(qzCategoryModel)
        }


    }
}