package shyam.retrofit.testing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewAdapter(val clickListener: OnItemEditClickListener):RecyclerView.Adapter<RecyclerviewAdapter.MyviewHolder>() {

    var userList = mutableListOf<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter.MyviewHolder {
       val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_design,parent,false)
        return MyviewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerviewAdapter.MyviewHolder, position: Int) {
       holder.bind(userList[position])
        holder.itemView.setOnClickListener{
            clickListener.onItemClick(userList[position])
        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    class MyviewHolder(view: View): RecyclerView.ViewHolder(view){

        val name = view.findViewById<TextView>(R.id.name)
        val email = view.findViewById<TextView>(R.id.email)
        val status = view.findViewById<TextView>(R.id.status)
        fun bind(data: User){
            name.text = data.name
            email.text = data.email
            status.text = data.status

        }
    }

    interface OnItemEditClickListener{
        fun onItemClick(user: User)
    }
}