package net.iessochoa.suarpl.suarquizcodeapp.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import net.iessochoa.suarpl.suarquizcodeapp.R
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.iessochoa.suarpl.suarquizcodeapp.databinding.FragmentShopBinding
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProvider
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProviderEng
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProviderPremium
import net.iessochoa.suarpl.suarquizcodeapp.model.QzCategoryProviderPremiumEng
// Fragmento utilizado para hacer compras y desbloquear elementos in app
class ShopFragment : Fragment() {
    //Variables ViewBinding
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    //Instancia a Firestore
    val db = Firebase.firestore
    //Monedas actuales
    var currentCoins:Int =0
    //Valor del artículo 1
    var item1Cost:Int = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        getUserPremium()
        getUserCoins()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBack.setOnClickListener {
            val next = ShopFragmentDirections.actionShopFragmentToHomeFragment()
            findNavController().navigate(next)
        }

        binding.btShopItem1.setOnClickListener {
            if(currentCoins>=5000){
                updateUserCoins(item1Cost)
                updateUserToPremium()
                getUserPremium()

            }else{
                Toast.makeText(activity, getString(R.string.coin_notenough), Toast.LENGTH_LONG).show()
            }
        }
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
                        binding.shopItem1Image.isGone = document.get("premium") == true
                        binding.shopDescItem1.isGone = document.get("premium") == true
                        binding.btShopItem1.isGone = document.get("premium") == true
                        binding.allPurchased.isEnabled = document.get("premium") == true
                    } else {
                        Log.d(ContentValues.TAG, "Campo premium nulo")
                    }
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "Error al obtener el estado Premium", e)
            }
        }
    }

    //Actualizando monedas, sumando resultado obtenido usando coroutines
    private fun updateUserToPremium() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                docRef.update("premium", true)
                withContext(Dispatchers.Main){
                    Toast.makeText(activity, getString(R.string.shop_articulo_comprado), Toast.LENGTH_SHORT)
                        .show()
                }


            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "update failed with ", e)
            }
        }
    }
    //Restamos 5000 monedas que cuesta el artículo Premium
    private fun updateUserCoins(itemCost: Int) {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                docRef.update("coins", (currentCoins- itemCost).toString()).await()
                withContext(Dispatchers.Main){
                    getUserCoins()
                }

            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "update failed with ", e)
            }
        }
    }
    //Obtiene monedas desde FB
    private fun getUserCoins() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        val docRef = db.collection("users").document(currentUser)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val document = docRef.get().await()
                if (document.get("coins") != null) {
                    currentCoins = document.get("coins").toString().toInt()
                    binding.shopCurrentCoins.text = currentCoins.toString()
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "get failed with ", e)
            }
        }
    }

}