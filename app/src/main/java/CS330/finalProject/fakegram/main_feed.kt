package CS330.finalProject.fakegram

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*

class main_feed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_feed)
        var profileButton = findViewById<Button>(R.id.buttonProfile)
        var listView = findViewById<ListView>(R.id.listView)
        var downloadUrl: Uri? = null
        val storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child("BU.png")
        val listAllTasks = storageRef.listAll()
        val gsReference = storage.getReferenceFromUrl("gs://fakegram-8c4a9.appspot.com/BU.png")
        val httpsReference = storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/v0/b/fakegram-8c4a9.appspot.com/o/BU.png?alt=media&token=1dca369d-7485-48e5-8f7a-1ef5a9966fc9")

        //val imageView = findViewById<ImageView>(R.id.imageView2)

//        storageRef.child("BU.png").downloadUrl.addOnSuccessListener {
//            downloadUrl = it
//            val imageUri = Uri.parse(downloadUrl.toString())
//
//            Glide.with(this /* context */)
//                .load(imageUri)
//                .into(imageView)
//        }.addOnFailureListener {
//            // Handle any errors
//        }
//
//        val feed = arrayOf<Uri>()
//        listAllTasks.addOnSuccessListener { listResult ->
//            // Get the download URL for each object
//            for (item in listResult.items) {
//                val downloadUrlTask = item.getDownloadUrl()
//                downloadUrlTask.addOnSuccessListener { downloadUrl ->
//
//                val imageUri = Uri.parse(downloadUrl.toString())
//                feed.plus(imageUri)
//                Glide.with(this /* context */)
//                    .load(imageUri)
//                    .into(imageView)
//                }
//            }
//        }

        val dataList = mutableListOf<Uri>()
        val dataNameList = mutableListOf<String>()
        val dataListUsers = mutableListOf<String>()
        val adapter = MyAdapter(this, dataList, dataNameList, dataListUsers)
        listView.adapter = adapter


        val listAllTask = storageRef.listAll()
        listAllTask.addOnSuccessListener { listResult ->
            // Get the download URL for each object
            for (item in listResult.items) {
                val downloadUrlTask = item.getDownloadUrl()
                var users = item.parent?.name
                dataListUsers.add("User: " + users)
                downloadUrlTask.addOnSuccessListener { downloadUrl ->
                    dataNameList.add("Title: " + item.name.substringBefore((".")))
                    dataList.add(Uri.parse(downloadUrl.toString()))
                    dataListUsers.add(item.parent?.name.toString())


                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged()
                }
            }
        }

    profileButton.setOnClickListener {
        val intent = Intent(this, profile_page::class.java)
        startActivity(intent)
        println("test")
    }

    }



}