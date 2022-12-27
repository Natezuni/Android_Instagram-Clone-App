package CS330.finalProject.fakegram

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

// Custom adapter for the ListView
class MyAdapter(val context: Context, val dataList: List<Uri>, val dataNameList: List<String>, val dataListUsers: List<String>) : BaseAdapter() {

    // ViewHolder pattern to improve list view performance
    private class ViewHolder(row: View?) {
        var imageView: ImageView? = null
        var textView: TextView? = null
        var textViewUser: TextView? = null
        init {
            this.imageView = row?.findViewById(R.id.imageView2)
            this.textView = row?.findViewById(R.id.textView2)
            this.textViewUser = row?.findViewById(R.id.textViewUserName)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val imageUri = dataList[position]
        viewHolder.imageView?.let { Glide.with(context).load(imageUri).into(it) }
        viewHolder.textView?.text = dataNameList[position]
        viewHolder.textViewUser?.text = dataListUsers[position]
        return view as View
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataList.size
    }
}
