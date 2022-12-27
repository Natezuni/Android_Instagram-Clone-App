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
import com.google.firebase.ktx.initialize

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        lateinit var auth: FirebaseAuth
        auth = Firebase.auth


        registerBtn.setOnClickListener {
            val intent = Intent(this, register_page::class.java)
            startActivity(intent)
        }


        loginBtn.setOnClickListener {
            val email = findViewById<TextView>(R.id.txtUsername).text.toString()
            val password = findViewById<TextView>(R.id.txtPassword).text.toString()

            if(email != "" && password != "0") {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success")
                            val user = auth.currentUser
                            val intent = Intent(this, main_feed::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.exception)
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success")
                                        val user = auth.currentUser
                                        val intent = Intent(this, main_feed::class.java)
                                        startActivity(intent)
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                                        Toast.makeText(baseContext, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show()

                                    }
                                }

                        }
                    }
            } else {
                Toast.makeText(baseContext, "You need to enter something!",
                    Toast.LENGTH_SHORT).show()
            }



        }
    }



}