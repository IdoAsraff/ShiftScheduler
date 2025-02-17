package com.talido.samalto.view

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talido.samalto.R

class PostHolder(itemView: View, val adapter: PostsAdapter) : RecyclerView.ViewHolder(itemView) {
    val postCard: CardView = itemView.findViewById(R.id.postCard)
    val etSufferingLevel: EditText = itemView.findViewById(R.id.sufferingLevel)
    val etName: EditText = itemView.findViewById(R.id.postName)
    val shiftList: RecyclerView = itemView.findViewById(R.id.shiftsList)
    val addPost: ImageView = itemView.findViewById(R.id.addPost)
    val expand: ImageView = itemView.findViewById(R.id.expand)
    var currentPosition: Int? = null

    // Listeners
    init {
        etName.doAfterTextChanged {
            if (currentPosition != null) {
                adapter.adapterPosts[currentPosition!!].name = it.toString()
                Log.d("TAL_DEBUG", "Updating $currentPosition to ${it.toString()}")
            }
        }

        etSufferingLevel.doAfterTextChanged {
            val inputSuffering = it.toString()
            if (currentPosition != null) {
                adapter.adapterPosts[currentPosition!!].sufferingLevel =
                    if (inputSuffering != "") inputSuffering.toInt() else 0
            }
        }
    }

    fun bindPost(position: Int) {
        val post = adapter.adapterPosts[position]
        currentPosition = position

        // Set fields
        etName.setText(post.name)
        etSufferingLevel.setText(post.sufferingLevel.toString())

        // Set Shift list
        shiftList.layoutManager = LinearLayoutManager(postCard.context)
        shiftList.adapter =
            ShiftAdapter(postCard.context, adapter.startTime, post.name, post.shifts)
        shiftList.visibility = if (post.isExpanded) View.VISIBLE else View.GONE
        addPost.visibility = View.GONE

        // Expand
        expand.visibility = View.VISIBLE
        if (post.isExpanded)
            expand.setImageDrawable(adapter.context.getDrawable(R.drawable.ic_expand_less_black_24dp))
        else
            expand.setImageDrawable(adapter.context.getDrawable(R.drawable.ic_expand_more_black_24dp))
        postCard.setOnClickListener {
            post.isExpanded = !post.isExpanded
            adapter.notifyItemChanged(position)
        }
    }

    fun bindAddPost() {
        etName.setText("")
        etSufferingLevel.setText("0")
        etName.hint = "שם עמדה"
        addPost.visibility = View.VISIBLE
        expand.visibility = View.GONE
        addPost.setOnClickListener {
            val inputSuffering = etSufferingLevel.text.toString()
            val newPost = AdapterPost(
                etName.text.toString(),
                if (inputSuffering != "") inputSuffering.toInt() else 0
            )
            newPost.isExpanded = true
            adapter.adapterPosts.add(newPost)
            postCard.clearFocus()
            adapter.notifyItemChanged(adapter.adapterPosts.size - 1) // Remove + from added item
            adapter.notifyItemInserted(adapter.adapterPosts.size) // Add new empty item
        }
    }
}
