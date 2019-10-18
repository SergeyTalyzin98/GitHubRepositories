package sergeytalyzin.githubrepositories.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import sergeytalyzin.githubrepositories.R

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val repositories = mutableListOf<Repository.Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount() = repositories.size

    fun setList(list: List<Repository.Item>) {
        repositories.clear()
        repositories.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() = repositories.clear()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.name_item)
        private val description = itemView.findViewById<TextView>(R.id.description_item)
        private val avatar = itemView.findViewById<ImageView>(R.id.avatar_item)

        fun bind(repository: Repository.Item) {
            name.text = repository.name
            description.text = repository.description
            Picasso.with(itemView.context).load(repository.owner!!.avatarUrl).into(avatar)
        }
    }
}