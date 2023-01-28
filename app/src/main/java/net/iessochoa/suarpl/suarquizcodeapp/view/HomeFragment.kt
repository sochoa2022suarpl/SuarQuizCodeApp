package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
    //Variables RecyclerView, uso MutableList por si se
    // implementa la compra de categorías ingame, en ese caso
    // habría que aumentar los elementos
    private var qzCategoryMutableList: MutableList<QzCategory> = QzCategoryProvider.qzCategoryList.toMutableList()
    private val llmanager = LinearLayoutManager(context)
    private lateinit var adapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

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

            //TODO IMPLEMENTAR CIERRE DE CONEXIÓN EN UN FUTURO
            exitApp()

        }

        binding.btRanking.setOnClickListener {
            //Mostrar el ranking
            //TODO FEATURE OPCIONAL, IMPLEMENTAR SI DA TIEMPO
            Toast.makeText(activity, "Feature opcional, aún no implementada", Toast.LENGTH_LONG).show()


        }

    }
    //Función para iniciar RecyclerView
    private fun initRecyclerView() {
        adapter = CategoryAdapter(
            qzCategoryList = qzCategoryMutableList
        )
        //Se le asigna un layout y un adaptador
        binding.recyclerView.layoutManager = llmanager
        binding.recyclerView.adapter = adapter

    }
    /*
    Construyendo AlertDialog para salir de la aplicación
    */
    private fun exitApp(){
        val salirDialog = AlertDialog.Builder(activity)
        salirDialog.setMessage("¿Quieres salir de la aplicación?")
            .setCancelable(false)
            .setPositiveButton("Sí") { _, _ -> exitProcess(0) }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        val alert = salirDialog.create()
        alert.setTitle("Dialog Header")
        alert.show()

    }

}