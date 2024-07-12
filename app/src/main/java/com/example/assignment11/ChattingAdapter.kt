package com.example.assignment11

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment11.databinding.LeftchattingSampleBinding
import com.example.assignment11.databinding.RightchattingSampleBinding

class ChattingAdapter(val chattingList:ArrayList<ChatData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent:ViewGroup,viewType: Int):RecyclerView.ViewHolder {
        return if(viewType==0){
            val binding=LeftchattingSampleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            LeftChattingViewHolder(binding)
        }
        else{
            val binding=RightchattingSampleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            RightChattingViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return chattingList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is LeftChattingViewHolder){
            holder.chat.text=chattingList[position].chat
            holder.itemView.setOnClickListener{
                Toast.makeText(holder.itemView.context,holder.chat.text, Toast.LENGTH_SHORT).show()
            }
        }
        else if(holder is RightChattingViewHolder){
            holder.chat.text=chattingList[position].chat
            Toast.makeText(holder.itemView.context,holder.chat.text,Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemViewType(position: Int):Int{
        return chattingList[position].viewType
    }

}