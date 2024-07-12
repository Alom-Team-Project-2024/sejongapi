package com.example.assignment11

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment11.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()

            // Credentials 객체 생성
            val credentials = Credentials(username, password)

            // Retrofit을 사용하여 서버에 로그인 요청
            RetrofitClient.api.authenticate(credentials)
                .enqueue(object : Callback<SejongAuthResponse> {
                    override fun onResponse(call: Call<SejongAuthResponse>, response: Response<SejongAuthResponse>) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            body?.let {
                                val name = it.result.body.name
                                val major = it.result.body.major
                                Toast.makeText(this@Login, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                                // 로그인에 성공하면 메인 화면으로 이동
                                val intent = Intent(this@Login, Chatting::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@Login, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<SejongAuthResponse>, t: Throwable) {
                        Toast.makeText(this@Login, "로그인 요청에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
