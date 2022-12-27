package CS330.finalProject.fakegram

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import org.w3c.dom.Text
import java.io.File
import java.io.FileNotFoundException

class newPost : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 2
    private val PICK_IMAGE_REQUEST = 1

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val user = FirebaseAuth.getInstance().currentUser

    var email: String? = null
    var file: File? = null
    var imageRef: StorageReference? = null
    var imageRefCopy: StorageReference? = null
    var uploadTask: UploadTask? = null
    var uploadTaskCopy: UploadTask? = null
    var postTitleString: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        var postToProfile = findViewById<Button>(R.id.postToProfile)
        postToProfile.isEnabled = false
        postToProfile.setError("Please enter a title and select an image")


        var buttonGallery = findViewById<Button>(R.id.imagePickerBtn)
        buttonGallery.isEnabled = false
        buttonGallery.setError("Please enter a title first!")

        var imageView = findViewById<ImageView>(R.id.imageViewPost)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val user = FirebaseAuth.getInstance().currentUser

        val fileName = user?.displayName // or use any other unique identifier for the user

        var postTitle = findViewById<TextView>(R.id.postTitle)

//        postTitle.isEnabled = false
//        postTitle.setError("You need to select an image first!")



        postTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                postTitleString = postTitle.text.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                buttonGallery.isEnabled = true
                buttonGallery.setError(null)
            }

            override fun afterTextChanged(s: Editable?) {
                postTitleString = postTitle.text.toString()
            }

            // other TextWatcher methods go here
        })

        buttonGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
            postToProfile.isEnabled = true
            postToProfile.setError(null)

        }

        postToProfile.setOnClickListener {

            uploadTask?.addOnSuccessListener {
                postTitle.isEnabled = true
                val intent = Intent(this, profile_page::class.java)
                startActivity(intent)
            }?.addOnFailureListener {
                // the image upload failed
            }

            uploadTaskCopy?.addOnSuccessListener {
                postTitle.isEnabled = true

            }?.addOnFailureListener {
                // the image upload failed
            }
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var imageView = findViewById<ImageView>(R.id.imageViewPost)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            Glide.with(this).load(uri).into(imageView)
            try {
                email = user?.email
                file = uri?.let { File(it.path) }
                imageRef = storageRef.child("$email/$postTitleString.jpg")
                imageRefCopy = storageRef.child("$postTitleString.jpg")
                uploadTask = uri?.let { imageRef!!.putFile(it) }
                uploadTaskCopy = uri?.let { imageRefCopy!!.putFile(it) }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            // You can use the URI to get the file path, or upload the file to a server, etc.
        }
    }
    override fun onBackPressed() {
        // Refresh the activity when the back button is clicked
        finish()
        val intent = Intent(this, profile_page::class.java)
        startActivity(intent)
    }



}