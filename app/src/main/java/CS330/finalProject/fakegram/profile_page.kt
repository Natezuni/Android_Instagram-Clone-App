package CS330.finalProject.fakegram

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class profile_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        var listView = findViewById<ListView>(R.id.listView2)
        //var profileButton = findViewById<Button>(R.id.buttonProfile)

        var user = FirebaseAuth.getInstance().currentUser
        var email = user?.email

        var downloadUrl: Uri? = null
        val storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child("$email")
        val listAllTasks = storageRef.listAll()
        val gsReference = storage.getReferenceFromUrl("gs://fakegram-8c4a9.appspot.com/BU.png")
        val httpsReference = storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/v0/b/fakegram-8c4a9.appspot.com/o/BU.png?alt=media&token=1dca369d-7485-48e5-8f7a-1ef5a9966fc9")






        val scrollView = ScrollView(this)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        scrollView.layoutParams = layoutParams


        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        val images = listOf(R.drawable.stories, R.drawable.stories, R.drawable.stories)

        images.forEach {
            val imageView = ImageView(this)

            imageView.setImageResource(it)
            val layoutParams2 = LinearLayout.LayoutParams(300, 300)
            imageView.layoutParams = layoutParams2

            linearLayout.addView(imageView)
        }


        scrollView.addView(linearLayout)


        val container = findViewById<ViewGroup>(R.id.container)


        container.addView(scrollView)




        var newPostBtn = findViewById<Button>(R.id.newPostBtn)
        var profileName = findViewById<TextView>(R.id.userProfileEmail)
        profileName.text = email + "'s Profile Page"


        newPostBtn.setOnClickListener {
            val intent = Intent(this, newPost::class.java)
            startActivity(intent)

        }



    val dataList = mutableListOf<Uri>()
    val dataNameList = mutableListOf<String>()
    val dataListUsers = mutableListOf<String>()
    val adapter = MyAdapter(this, dataList, dataNameList, dataListUsers)
    listView.adapter = adapter


    val listAllTask = pathReference.listAll()
    listAllTask.addOnSuccessListener { listResult ->
        // Get the download URL for each object
        for (item in listResult.items) {
            val downloadUrlTask = item.getDownloadUrl()
            downloadUrlTask.addOnSuccessListener { downloadUrl ->
                dataNameList.add("Title: " + item.name.substringBefore((".")))
                dataList.add(Uri.parse(downloadUrl.toString()))
                item.parent?.let { dataListUsers.add("User: " + it.name.substringBefore(("@"))) }

                //dataList.add(Uri.parse(downloadUrl.toString()))
                println(dataList)
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }
        }
    }
    }
    override fun onBackPressed() {
        // Refresh the activity when the back button is clicked
        finish()
        val intent = Intent(this, main_feed::class.java)
        startActivity(intent)
    }

}