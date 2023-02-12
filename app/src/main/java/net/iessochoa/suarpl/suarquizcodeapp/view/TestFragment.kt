package net.iessochoa.suarpl.suarquizcodeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.adapter.CategoryAdapter
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentLoginBinding
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentTestBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategory
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProvider

//Fragmento usado como PlayGround para integrar unidades funcionales
class TestFragment : Fragment() {
    /*
    //Variables ViewBinding
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private var qzCategoryMutableList: MutableList<QzCategory> = QzCategoryProvider.qzCategoryList.toMutableList()
    private val llmanager = LinearLayoutManager(context)
    private lateinit var adapter:CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = CategoryAdapter(
            qzCategoryList = qzCategoryMutableList,
            onClickListener = {QzCategory -> onItemselected(QzCategory)}
        )
        //Se le asigna un layout y un adaptador
        binding.recyclerView.layoutManager = llmanager
        binding.recyclerView.adapter = adapter

    }
    private fun onItemselected(quizCategory:QzCategory){
        Toast.makeText(this.context, quizCategory.catName, Toast.LENGTH_SHORT).show()
    }
*/
}