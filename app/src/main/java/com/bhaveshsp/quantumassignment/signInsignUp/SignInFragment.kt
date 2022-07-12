package com.bhaveshsp.quantumassignment.signInsignUp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bhaveshsp.quantumassignment.HomeActivity
import com.bhaveshsp.quantumassignment.R
import com.bhaveshsp.quantumassignment.utils.EmailValidator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {
    private lateinit var emailText : EditText
    private lateinit var passwordText : EditText
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerNowText : TextView = view.findViewById(R.id.registerNowText)
        val signInButton : Button = view.findViewById(R.id.signInButton)
        val googleSignInImage : ImageView = view.findViewById(R.id.googleSignInImage)
        val facebookSignInImage : ImageView = view.findViewById(R.id.facebookSignInImage)

        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleSignInImage.setOnClickListener { googleSignIn() }
        facebookSignInImage.setOnClickListener { facebookSignIn() }

        emailText = view.findViewById(R.id.signInEmailText)
        passwordText = view.findViewById(R.id.signInPasswordText)
        signInButton.setOnClickListener {
            validateData()
        }
        registerNowText.setOnClickListener { changeTab() }
    }

    private fun googleSignIn(){

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_CODE){

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user : FirebaseUser?){
        user?.let {
            startActivity(Intent(activity, HomeActivity::class.java))
        }
    }

    private fun facebookSignIn(){
        Toast.makeText(activity, "Not Implementated", Toast.LENGTH_SHORT).show()
    }


    private fun validateData(){
        if (!EmailValidator.isValidEmail(email = emailText.text)){
            Toast.makeText(activity, "Please Enter Email ID", Toast.LENGTH_SHORT).show()
            return
        }
        if (passwordText.text == null || passwordText.text.isEmpty()){
            Toast.makeText(activity, "Please Enter Password", Toast.LENGTH_SHORT).show()
            return
        }

        val email = emailText.text.toString()
        val password = passwordText.text.toString()
        if (email == "test123@gmail.com" || password == "test123"){
            startActivity(Intent(activity,HomeActivity::class.java))
            activity?.finish()
        }else{
            Toast.makeText(activity, "Please Enter Valid Email Id or Password", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun changeTab(){
        val tabs : TabLayout = requireActivity().findViewById(R.id.tabLayout)
        tabs.getTabAt(1)?.select()
    }

    companion object{
        private const val GOOGLE_CODE = 2324
        private const val TAG = "SIGN_IN_TESTING"
    }

}