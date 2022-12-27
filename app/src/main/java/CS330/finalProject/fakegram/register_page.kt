package CS330.finalProject.fakegram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class register_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        lateinit var auth: FirebaseAuth
        auth = Firebase.auth
        val registerBtn = findViewById<Button>(R.id.registerBtn2)
        val backButton = findViewById<Button>(R.id.backButton)

        registerBtn.setOnClickListener {

            val email = findViewById<TextView>(R.id.txtUsername3).text.toString()
            val password = findViewById<TextView>(R.id.txtPassword3).text.toString()
            val passwordConfirm = findViewById<TextView>(R.id.txtPassword4).text.toString()

            if (password == passwordConfirm && password != "" && email != "") {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success")
                            val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            } else {
                Toast.makeText(baseContext, "Password and confirm password does not match",
                    Toast.LENGTH_SHORT).show()
            }
        }
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}