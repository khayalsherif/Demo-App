package com.vholodynskyi.assignment.presentation.contactslist.adapter

import android.animation.Animator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vholodynskyi.assignment.base.BaseDiffUtil
import com.vholodynskyi.assignment.data.local.contact.ContactLocalDto
import com.vholodynskyi.assignment.databinding.ItemContactListBinding
import com.vholodynskyi.assignment.common.ClickListener
import com.vholodynskyi.assignment.common.SwipeListener
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder

class ContactAdapter(
    private val clickListener: ClickListener,
    val swipeListener: SwipeListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var items = emptyList<ContactLocalDto>()

    class ContactViewHolder(val binding: ItemContactListBinding) :
        RecyclerView.ViewHolder(binding.root), AnimateViewHolder {

        override fun animateRemoveImpl(
            holder: RecyclerView.ViewHolder,
            listener: Animator.AnimatorListener
        ) {
            itemView.animate().apply {
                translationY(-itemView.height * 0.3f)
                alpha(0f)
                duration = 300
                setListener(listener)
            }.start()
        }

        override fun preAnimateRemoveImpl(holder: RecyclerView.ViewHolder) {}

        override fun animateAddImpl(
            holder: RecyclerView.ViewHolder,
            listener: Animator.AnimatorListener
        ) {
            itemView.animate().apply {
                translationY(0f)
                alpha(1f)
                duration = 300
                setListener(listener)
            }.start()
        }

        override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
            itemView.translationY = -itemView.height * 0.3f
            itemView.alpha = 0f
        }

        fun bindItem(contact: ContactLocalDto) {
            binding.text.text = "${contact.firstName} ${contact.lastName}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)
        holder.binding.root.setOnClickListener {
            clickListener.onClick(it, position)
        }
    }

    override fun getItemCount() = items.size

    fun setData(newItems: List<ContactLocalDto>) {
        val diffUtil = BaseDiffUtil(items, newItems)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        items = newItems
        diffUtilResult.dispatchUpdatesTo(this)
    }

}


