package com.wikilift.blogapp.core.auth

class CompareLogin {
    companion object{
        fun isPasswordValid(password: String): Boolean {
            val PASSWORD_REGEX =
                """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%!\-_?&])(?=\S+${'$'}).{8,}""".toRegex()
            return PASSWORD_REGEX.matches(password)
        }

         fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        fun isBlankField(string:String):Boolean{
            if (string.isBlank()){
                return true
            }
            return false
        }
        fun isPasswordEqual(password:String,newPassword:String):Boolean= password == newPassword

    }
}