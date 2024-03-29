package net.iessochoa.suarpl.suarquizcodeapp.view

import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import net.iessochoa.suarpl.suarquizcodeapp.model.*
import java.util.*
import kotlin.system.exitProcess

//Fragmento principal, desde el que accedemos a todo
class HomeFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //Variables para volcar items en el RecyclerView
    private var currentLang = obtenerIdiomaSistema()

    private var qzCategoryMutableList: MutableList<QzCategory> =
        if (currentLang == "es") {
            QzCategoryProvider.qzCategoryList.toMutableList()
        } else {
            QzCategoryProviderEng.qzCategoryListEng.toMutableList()
        }
    private val llmanager = LinearLayoutManager(context)
    private lateinit var adapter: CategoryAdapter

    //Segundos restantes que luego pasaremos a QuizFragment
    private var secondsLeft: Int = 100

    //String que pasamos a QuizFragment para identificar el cuestionario
    private var quizCategoryString: String = "JAVA"

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

        getUserPremium()
        initRecyclerView()
        getConnectedUserName()
        getUserCoins()

        binding.btLogout.setOnClickListener {
            //Salir
            exitApp()
        }
        //Añadimos listener al filtro de categorías, y filtramos la lista de ellas
        binding.etFilterCat.addTextChangedListener { userFilter ->
            //La almacenamos en una constante, usamos lowerkeys para ignorar diferencias entre mays/mins
            val qzCategoryListFiltered = qzCategoryMutableList.filter { qzCategory ->
                qzCategory.catName.lowercase().contains(userFilter.toString().lowercase())
            }
            //Llamamos al método del adaptador que actualiza la lista de categorías
            adapter.updateQuizList(qzCategoryListFiltered)
        }

        binding.btRank.setOnClickListener {
            //Mostrar el ranking, feature opcional finalmente no implementada por falta de tiempo
            //Cambiada por borrar cuenta
            deleteAccount()
        }
        binding.btDeleteAcc.setOnClickListener {
            //Mostrar el ranking, feature opcional finalmente no implementada por falta de tiempo
            //Cambiada por borrar cuenta
            val next = HomeFragmentDirections.actionHomeFragmentToScoreFragment()
            findNavController().navigate(next)
        }
        binding.floatingActionButton.setOnClickListener {
            val next = HomeFragmentDirections.actionHomeFragmentToShopFragment()
            findNavController().navigate(next)
        }

        binding.tvUsername.setOnLongClickListener {
            showNameInputDialog()
            true
        }

        binding.imageView4.setOnClickListener{
            logout()
        }

        /*
        Al cambiar la dificultad con los radiobuttons, cambia el color
        del radiobutton y cambia la variable que contiene los segundos restantes
        que pasaremos al fragmento del cuestionario
         */
        binding.rgDifficulty.setOnCheckedChangeListener { _, i ->
            when (i) {
                binding.rbNormal.id -> {
                    binding.rgDifficulty.setBackgroundResource(R.drawable.radiusgreen)
                    secondsLeft = 100
                    Snackbar.make(
                        binding.homeFragment,
                        getString(R.string.tiempo_100),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                binding.rbDificil.id -> {
                    binding.rgDifficulty.setBackgroundResource(R.drawable.radiusred)
                    secondsLeft = 60
                    Snackbar.make(
                        binding.homeFragment,
                        getString(R.string.tiempo_60),
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
            onClickListener = { QzCategory -> onItemselected(QzCategory) }
        )
        //Se le asigna un layout y un adaptador
        binding.recyclerView.layoutManager = llmanager
        binding.recyclerView.adapter = adapter
    }

    //Función que controla el item del RecyclerView Pulsado
    private fun onItemselected(quizCategory: QzCategory) {
        //Asignamos el valor de la categoría pulsada a la variable que pasaremos al fragment home
        quizCategoryString = quizCategory.catFBValue
        //Navegamos al fragment Home pasando segundos restantes y categoría pulsada
        binding.apply {
            val next = HomeFragmentDirections.actionHomeFragmentToQuizFragment(
                secondsLeft,
                quizCategoryString
            )
            findNavController().navigate(next)
        }
    }

    /*
    AlertDialog para salir de la aplicación
    */
    private fun exitApp() {
        val salirDialog = AlertDialog.Builder(activity)
        salirDialog.setMessage(getString(R.string.quieres_salir))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.si)) { _, _ ->
                logoutFirebaseAuth()
                exitProcess(0)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        val alert = salirDialog.create()
        alert.setTitle(getString(R.string.bt_salir))
        alert.show()
    }
    //Cambiar nombre, mostrar diálogo
    private fun showNameInputDialog() {
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_name_input, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextName)

        val alertDialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("ACEPTAR") { _, _ ->
                val enteredName = nameEditText.text.toString()
                Toast.makeText(activity, "Nombre introducido: $enteredName", Toast.LENGTH_SHORT).show()
                updateUserName(enteredName)
            }
            .setNegativeButton("CANCELAR") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.setTitle("Introducir nombre")
        alertDialog.show()
    }
    //Actualizando nombre en FB
    private fun updateUserName(name: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                docRef.update("name", name).await()
                withContext(Dispatchers.Main) {
                    getConnectedUserName()
                    Toast.makeText(activity, "Nombre actualizado correctamente", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(activity, "Error actualizando nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //Logout FB
    fun logoutFirebaseAuth() {
        FirebaseAuth.getInstance().signOut()
    }
    private fun logout() {
        val logoutDialog = AlertDialog.Builder(activity)
        logoutDialog.setMessage(getString(R.string.alert_logout))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.si)) { _, _ ->
                logoutFirebaseAuth()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)

            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        val alert = logoutDialog.create()
        alert.setTitle(getString(R.string.alert_title_logout))
        alert.show()
    }

    //Borrar la cuenta, dialog
    private fun deleteAccount() {
        val deleteDialog = AlertDialog.Builder(activity)
        deleteDialog.setMessage(getString(R.string.quieres_borrar))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.si)) { _, _ -> deleteCurrentAccount() }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        val alert = deleteDialog.create()
        alert.setTitle(getString(R.string.borrar_cuenta))
        alert.show()
    }

    private fun deleteCurrentAccount() {
        val user = FirebaseAuth.getInstance().currentUser!!

        CoroutineScope(Dispatchers.IO).launch {
            try {
                user.delete().await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, getString(R.string.cuenta_borrada), Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)

                }
            } catch (e: Exception) {
                Toast.makeText(activity, getString(R.string.cuenta_borrada), Toast.LENGTH_SHORT)
                    .show()
            }
        }
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
                Log.d(TAG, "Error al obtener las monedas", e)
            }
        }
    }

    //Método que fija las monedas en 3000 en caso de que no exista el campo en la BD Firestore,
    // usando corrutinas
    private fun setUserDefaults() = CoroutineScope(Dispatchers.IO).launch {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)
        docRef.update("coins", "3000").await()
        getUserCoins()
    }

    fun obtenerIdiomaSistema(): String {
        return Locale.getDefault().language
    }

    //Método que comprueba si el usuario logueado es premium, usando corrutinas
    private fun getUserPremium() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val document = docRef.get().await()
                withContext(Dispatchers.Main) {
                    if (document.get("premium") != null) {
                        if (document.get("premium") == false && currentLang == "es") {
                            qzCategoryMutableList =
                                QzCategoryProvider.qzCategoryList.toMutableList()
                            initRecyclerView()
                        } else if (document.get("premium") == false && currentLang != "es") {

                            qzCategoryMutableList =
                                QzCategoryProviderEng.qzCategoryListEng.toMutableList()
                            initRecyclerView()
                        } else if (document.get("premium") == true && currentLang != "es") {
                            binding.imageUser.setImageResource(R.drawable.premium)
                            qzCategoryMutableList =
                                QzCategoryProviderPremiumEng.qzCategoryListEng.toMutableList()
                            initRecyclerView()
                        } else if (document.get("premium") == true && currentLang == "es") {
                            binding.imageUser.setImageResource(R.drawable.premium)
                            qzCategoryMutableList =
                                QzCategoryProviderPremium.qzCategoryList.toMutableList()
                            initRecyclerView()
                        } else {
                            Log.d(TAG, "Imposible determinar Premium")
                        }
                    } else {
                        qzCategoryMutableList = QzCategoryProvider.qzCategoryList.toMutableList()
                        initRecyclerView()
                        Log.d(TAG, "Campo premium nulo")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error al obtener el estado Premium", e)
            }
        }
    }

}