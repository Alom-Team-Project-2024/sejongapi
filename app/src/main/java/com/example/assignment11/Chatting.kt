package com.example.assignment11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment11.databinding.ActivityMainBinding

class Chatting : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState:Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val chattingList = ArrayList<ChatData>()
        binding.rcvChatting.adapter = ChattingAdapter(chattingList)

        val adapter = binding.rcvChatting.adapter
        binding.rcvChatting.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)

        binding.btnLeft.setOnClickListener {
            if(binding.etTextinput.length()>0){
                chattingList.add(ChatData(binding.etTextinput.text.toString(),0))
                adapter!!.notifyDataSetChanged()
                binding.etTextinput.setText("")
            }
        }
        binding.btnRight.setOnClickListener {
            if(binding.etTextinput.length()>0){
                chattingList.add(ChatData(binding.etTextinput.text.toString(),1))
                adapter!!.notifyDataSetChanged()
                binding.etTextinput.setText("")
            }
        }


    }
}