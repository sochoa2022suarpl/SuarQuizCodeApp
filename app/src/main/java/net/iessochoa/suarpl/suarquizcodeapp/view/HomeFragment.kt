package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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

    //Variables para volcar items en el RecyclerView
    private var qzCategoryMutableList: MutableList<QzCategory> =
        QzCategoryProvider.qzCategoryList.toMutableList()
    private val llmanager = LinearLayoutManager(context)
    private lateinit var adapter: CategoryAdapter

    //Segundos restantes que luego pasaremos a QuizFragment
    private var secondsLeft: Int = 100

    //String que pasamos a QuizFragment para identificar el cuestionario
    private var quizCategoryString :String = "JAVA"
    //Instancia a Firestore
    val db = Firebase.firestore

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
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getConnectedUserName()
        getUserCoins()

        binding.btLogout.setOnClickListener {
            //Salir
            exitApp()
        }
        //Añadimos listener al filtro de categorías, y filtramos la lista de ellas
        binding.etFilterCat.addTextChangedListener {userFilter ->
            //La almacenamos en una constante, usamos lowerkeys para ignorar diferencias entre mays/mins
            val qzCategoryListFiltered = qzCategoryMutableList.filter { qzCategory -> qzCategory.catName.lowercase().contains(userFilter.toString().lowercase())}
            //Llamamos al método del adaptador que actualiza la lista de categorías
            adapter.updateQuizList(qzCategoryListFiltered)
        }

        binding.btRanking.setOnClickListener {
            //Mostrar el ranking, feature opcional finalmente no implementada por falta de tiempo
            Toast.makeText(activity, "Feature opcional, aún no implementada", Toast.LENGTH_LONG).show()
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
    //Función que controla el item del RecyclerView Pulsado
    private fun onItemselected(quizCategory:QzCategory){
        //Asignamos el valor de la categoría pulsada a la variable que pasaremos al fragment home
        quizCategoryString = quizCategory.catName
        //Navegamos al fragment Home pasando segundos restantes y categoría pulsada
        binding.apply {
            val next = HomeFragmentDirections.actionHomeFragmentToQuizFragment(secondsLeft, quizCategoryString)
            findNavController().navigate(next)
        }
    }
    /*
    AlertDialog para salir de la aplicación
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
    //Método que obtiene el nombre del usuario logueado
    private fun getConnectedUserName() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()

        val docRef = db.collection("users").document(currentUser)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val document = docRef.get().await()
                withContext(Dispatchers.Main) {
                    if (document != null) {
                        binding.tvUsername.text = document.get("name").toString()
                    } else {
                        Log.d(TAG, "No existe el documento")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error al obtener el nombre de usuario ", e)
            }
        }
    }
    //Método que obtiene las monedas actuales del usuario logueado, usando corrutinas
    private fun getUserCoins() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val document = docRef.get().await()
                withContext(Dispatchers.Main) {
                    if (document.get("coins") != null) {
                        binding.tvCantidadMonedas.text = document.get("coins").toString()
                    } else {
                        setUserDefaults()
                    }
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "Error al obtener las monedas", e)
            }
        }
    }
    //Método que fija las monedas en 3000 en caso de que no exista el campo en la BD Firestore, usando corrutinas
    private fun setUserDefaults() = CoroutineScope(Dispatchers.IO).launch {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)
        docRef.update("coins", "3000").await()
        getUserCoins()
    }

}