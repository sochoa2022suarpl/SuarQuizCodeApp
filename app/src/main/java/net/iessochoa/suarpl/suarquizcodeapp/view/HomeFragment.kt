package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import net.iessochoa.suarpl.suarquizcodeapp.adapter.CategoryAdapter
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentHomeBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategory
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProvider
import kotlin.system.exitProcess

class HomeFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var qzCategoryMutableList: MutableList<QzCategory> = QzCategoryProvider.qzCategoryList.toMutableList()
    private val llmanager = LinearLayoutManager(context)
    private lateinit var adapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()


        binding.btLogout.setOnClickListener{
            /*Construyendo AlertDialog para salir de la aplicación
            al pulsar en botón salir*/
            //TODO IMPLEMENTAR CIERRE DE CONEXIÓN EN UN FUTURO
            val salirDialog = AlertDialog.Builder(activity)
            salirDialog.setMessage("¿Quieres salir de la aplicación ???")
                .setCancelable(false)
                .setPositiveButton("Sí") { _, _ -> exitProcess(0) }
                .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            val alert = salirDialog.create()
            alert.setTitle("Dialog Header")
            alert.show()
        }

        binding.btRanking.setOnClickListener {
            //Mostrar el ranking
            //TODO FEATURE OPCIONAL, IMPLEMENTAR SI DA TIEMPO
            Toast.makeText(activity, "Feature opcional, aún no implementada", Toast.LENGTH_LONG).show()


        }



    }

    private fun initRecyclerView() {
        adapter = CategoryAdapter(
            qzCategoryList = qzCategoryMutableList
        )
        binding.recyclerView.layoutManager = llmanager
        binding.recyclerView.adapter = adapter

    }

}