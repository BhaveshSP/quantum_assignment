package com.bhaveshsp.quantumassignment.signInsignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bhaveshsp.quantumassignment.R
import com.bhaveshsp.quantumassignment.utils.EmailValidator
import com.google.android.material.tabs.TabLayout

class SignUpFragment : Fragment() {

    private lateinit var nameText : EditText
    private lateinit var emailText : EditText
    private lateinit var phoneText : EditText
    private lateinit var passwordText : EditText
    private lateinit var iAgreeCheck : CheckBox
    private lateinit var countryCodeSpinner: Spinner
    private val countryCodesList : Array<String> = arrayOf("91","93","213","376","244","672","297")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signUpButton : Button = view.findViewById(R.id.signUpButton)
        val signInText  : TextView = view.findViewById(R.id.signInText)
        countryCodeSpinner  = view.findViewById(R.id.signUpPhoneSpinner)
        val adapter : ArrayAdapter<String> = ArrayAdapter(requireActivity(),R.layout.snipper_layout_item,countryCodesList)
        countryCodeSpinner.adapter = adapter
//        countryCodeSpinner.
        nameText = view.findViewById(R.id.signUpNameText)
        emailText = view.findViewById(R.id.signUpEmailText)
        phoneText = view.findViewById(R.id.signUpPhoneNumber)
        passwordText = view.findViewById(R.id.signUpPassword)
        iAgreeCheck = view.findViewById(R.id.iAgreeCheckBox)
        signUpButton.setOnClickListener { validateData() }
        signInText.setOnClickListener { changeTab() }
    }

    private fun validateData(){
        if (!iAgreeCheck.isChecked){
            Toast.makeText(activity, "Please Agree to Terms and Conditions", Toast.LENGTH_SHORT).show()
            return
        }
        if (nameText.text == null || nameText.text.isEmpty()){
            Toast.makeText(activity, "Please Valid Name", Toast.LENGTH_SHORT).show()
            return
        }
        if (EmailValidator.isValidEmail(emailText.text)){
            Toast.makeText(activity, "Please Valid Email", Toast.LENGTH_SHORT).show()
            return
        }
        if (phoneText.text == null || phoneText.text.isEmpty()){
            Toast.makeText(activity, "Please Valid Phone Number", Toast.LENGTH_SHORT).show()
            return
        }
        if (passwordText.text == null || passwordText.text.isEmpty()){
            Toast.makeText(activity, "Please Valid Password", Toast.LENGTH_SHORT).show()
            return
        }
        val name = nameText.text.toString()
        val email = emailText.text.toString()
        val phoneNo =  "${countryCodesList[countryCodeSpinner.selectedItemPosition]}${phoneText.text}"
        val password = passwordText.text.toString()
        Toast.makeText(activity, "Account Created Successfully ", Toast.LENGTH_SHORT).show()
        changeTab()

    }

    private fun changeTab(){
        val tabs : TabLayout = requireActivity().findViewById(R.id.tabLayout)
        tabs.getTabAt(0)?.select()
    }

}