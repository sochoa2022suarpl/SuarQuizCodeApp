package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.adapter.CategoryAdapter
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentHomeBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategory
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProvider
import kotlin.system.exitProcess
//ToDo añadir diseño y onclicklistener al RecycledView para acceder a los cuestionarios
//ToDo sustituir el fake provider por repositorios una vez integrada la BD

class HomeFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //Variables RecyclerView, uso MutableList por si se
    // implementa la compra de categorías ingame, en ese caso
    // habría que aumentar los elementos
    private var qzCategoryMutableList: MutableList<QzCategory> =
        QzCategoryProvider.qzCategoryList.toMutableList()
    private val llmanager = LinearLayoutManager(context)
    private lateinit var adapter: CategoryAdapter

    //Segundos restantes que luego pasaremos al fragmento del cuestionario
    private var secondsLeft: Int = 100

    //String que pasamos para identificar el cuestionario
    private var quizCategoryString :String = "JAVA"

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

        binding.btLogout.setOnClickListener {

            //Salir
            exitApp()
        }
        /*
        binding.btRanking.setOnClickListener {
            //Mostrar el ranking
            //ToDo FEATURE OPCIONAL, IMPLEMENTAR SI DA TIEMPO
            Toast.makeText(activity, "Feature opcional, aún no implementada", Toast.LENGTH_LONG).show()
        }
         */

        /*Navegación provisional al siguiente fragment en este botón hasta el fragmento cuestionario
        hasta integrar
        onclicklistener en las diferentes opciones de la MutableList y la BD. Mediante safeargs
        pasamos el valor de los segundos restantes al siguiente fragmento
        */
        binding.apply {
            binding.btRanking.setOnClickListener {
                val next = HomeFragmentDirections.actionHomeFragmentToQuizFragment(secondsLeft, quizCategoryString)
                findNavController().navigate(next)
            }
        }
        /*
        Al cambiar la dificultad con los radiobuttons, cambia el color
        del radiobutton y cambia la variable que contiene los segundos restantes
        que pasaremos al fragmento del cuestionario
         */
        binding.rgDifficulty.setOnCheckedChangeListener { group, i ->
            when (i) {
                binding.rbNormal.id -> {
                    binding.rgDifficulty.setBackgroundResource(R.drawable.radiusgreen)
                    secondsLeft = 100
                    Snackbar.make(
                        binding.homeFragment,
                        "Tiempo total fijado a 100 segundos",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                binding.rbDificil.id -> {
                    binding.rgDifficulty.setBackgroundResource(R.drawable.radiusred)
                    secondsLeft = 60
                    Snackbar.make(
                        binding.homeFragment,
                        "Tiempo total fijado a 60 segundos",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    //Función para iniciar RecyclerView
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
        //Toast.makeText(this.context, quizCategory.catName, Toast.LENGTH_SHORT).show()
        var selected = quizCategory.catName

        when (selected){
            "KOTLIN" -> quizCategoryString = "KOTLIN"
            "JAVA" -> quizCategoryString = "JAVA"
            "REACT" -> quizCategoryString = "REACT"
        }
        binding.apply {
            val next = HomeFragmentDirections.actionHomeFragmentToQuizFragment(secondsLeft, quizCategoryString)
            findNavController().navigate(next)
        }
    }

    /*
    Construyendo AlertDialog para salir de la aplicación
    */
    private fun exitApp() {
        val salirDialog = AlertDialog.Builder(activity)
        salirDialog.setMessage("¿Quieres salir de la aplicación?")
            .setCancelable(false)
            .setPositiveButton("Sí") { _, _ -> exitProcess(0) }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        val alert = salirDialog.create()
        alert.setTitle("Salir")
        alert.show()
    }

}