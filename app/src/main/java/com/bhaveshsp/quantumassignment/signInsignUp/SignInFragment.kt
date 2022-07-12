package com.bhaveshsp.quantumassignment.signInsignUp

import android.content.Context.MODE_PRIVATE
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
import com.bhaveshsp.quantumassignment.models.User
import com.bhaveshsp.quantumassignment.network.dao.FirebaseDao
import com.bhaveshsp.quantumassignment.utils.EmailValidator
import com.bhaveshsp.quantumassignment.utils.PHONE_NUMBER_KEY
import com.bhaveshsp.quantumassignment.utils.SHARED_PREF
import com.bhaveshsp.quantumassignment.utils.USER_NAME_KEY
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.*
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
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleSignInImage.setOnClickListener { googleSignIn() }

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


    private fun validateData(){
        if (!EmailValidator.isValidEmail(email = emailText.text)){
            Toast.makeText(activity, "Please Enter Email ID", Toast.LENGTH_SHORT).show()
            return
        }
        if (passwordText.text == null || passwordText.text.isEmpty()){
            Toast.makeText(activity, "Please Enter Password", Toast.LENGTH_SHORT).show()
            return
        }
        val phoneNumber = requireActivity().getSharedPreferences(SHARED_PREF,MODE_PRIVATE).getString(
            PHONE_NUMBER_KEY,"")
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        FirebaseDao.userDatabaseReference(phoneNumber!!).addOnCompleteListener {
            if (it.isSuccessful){
                val result = it.result
                try{
                    val user = result.getValue(User::class.java) as User
                    val editor = requireContext().getSharedPreferences(
                        SHARED_PREF,
                        MODE_PRIVATE).edit()
                    editor.putString(USER_NAME_KEY,user.username)
                    editor.putString(PHONE_NUMBER_KEY,user.phoneNumber)
                    editor.apply()
                    Log.d(TAG, "onViewCreated: Sucucess $user")
                    if(email == user.email && password == user.password){
                        goToHomeActivity()
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "Invalid Username or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }catch (e : Exception){
                    Toast.makeText(
                        requireContext(),
                        "User Data not found please Create an Acoount ",
                        Toast.LENGTH_SHORT
                    ).show()
                }



            }else{
                Log.d(TAG, "onViewCreated: Error Snap")
            }
        }


    }

    private fun goToHomeActivity(){
        requireContext().startActivity(Intent(requireContext(),HomeActivity::class.java))
        requireActivity().finish()
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