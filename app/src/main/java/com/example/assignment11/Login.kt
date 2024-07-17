package com.example.assignment11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Callback
import com.example.assignment11.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView((binding.root))

        binding.btnLogin.setOnClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPassword.text.toString()
            val body = mapOf(
                "id" to id,
                "pw" to pw
            )
            RetrofitClient.instance.login(body).enqueue(object: Callback<SejongAuthResponse> {
                override fun onResponse(
                    call: Call<SejongAuthResponse>,
                    response: Response<SejongAuthResponse>
                ) {
                    Log.d("MainActivity", response.toString())
                    if (response.isSuccessful && response.body() != null) {
                        val auth = response.body()?.result
                        if (auth?.isAuth == "true") {
                            val intent = Intent(this@Login, Chatting::class.java)
                            Toast.makeText(this@Login, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@Login, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@Login, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SejongAuthResponse>, t: Throwable) {
                    Toast.makeText(this@Login, "로그인 실패", Toast.LENGTH_SHORT).show()
                }

            })


        }
    }
}