package net.iessochoa.suarpl.suarquizcodeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategory

class CategoryAdapter(
    private var qzCategoryList: List<QzCategory>,
    private val onClickListener: (QzCategory) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false))
    }
    /*
    Clasifica los objetos de la lista por posición y se los envía
    al ViewHolder, que los procesa con el método render
     */
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = qzCategoryList[position]
        holder.render(item, onClickListener)

    }
    /*
    Obtiene el tamaño de la lista, en este caso se ajusta a size
    para no obtener un posible NullPointerException, pero puede ser un valor fijo
     */
    override fun getItemCount(): Int =qzCategoryList.size

    /*
    La dejamos por si queremos meter luego un observer
    que actualice los posibles cambios en la lista
     */
    fun updateQuizList(qzCategoryList: List<QzCategory>){
        this.qzCategoryList = qzCategoryList
        notifyDataSetChanged()
    }

}