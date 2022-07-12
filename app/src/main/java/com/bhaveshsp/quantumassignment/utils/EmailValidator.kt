package com.bhaveshsp.quantumassignment.utils

import java.util.regex.Pattern

class EmailValidator {

    companion object{
        private val emailPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
        )

        fun isValidEmail(email: CharSequence?) : Boolean{
            return email!=null && emailPattern.matcher(email).matches()
        }
    }
}