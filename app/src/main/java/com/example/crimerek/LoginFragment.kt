package com.example.crimerek

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Find the login button and input fields
        val emailField = view.findViewById<EditText>(R.id.username)
        val passwordField = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        // Set the onClickListener for the login button
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    // Method to log in a user
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign-in success, navigate to HomeFragment
                    Log.d("LoginFragment", "signInWithEmail:success")
                    val homeFragment = HomeFragment()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainActivityFrameLayout, homeFragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    // If sign-in fails, display a message to the user
                    Log.w("LoginFragment", "signInWithEmail:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
