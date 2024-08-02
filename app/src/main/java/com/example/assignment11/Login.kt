package com.example.assignment11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment11.databinding.ActivityLoginBinding
import com.example.assignment11.model.AuthUserDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPassword.text.toString()

            val body = mapOf(
                "id" to id,
                "pw" to pw
            )

            // 로그인 요청
            RetrofitClient.sejongApi.login(body).enqueue(object : Callback<SejongAuthResponse> {
                override fun onResponse(call: Call<SejongAuthResponse>, response: Response<SejongAuthResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val auth = response.body()?.result
                        if (auth?.isAuth == "true") {
                            val userData = auth.body
                            showSuccess("로그인 성공!")
                            requestJwtToken(userData)
                        } else {
                            showError("로그인 실패: 인증되지 않았습니다.")
                        }
                    } else {
                        showError("로그인 실패: 서버 응답이 올바르지 않습니다.")
                    }
                }

                override fun onFailure(call: Call<SejongAuthResponse>, t: Throwable) {
                    showError("로그인 실패: 네트워크 오류가 발생했습니다.")
                }
            })
        }
    }

    private fun requestJwtToken(userData: SejongAuthResponseResultBodyJson) {
        // 한국어 상태 값을 영어로 매핑
        val statusMap = mapOf(
            "재학" to RegistrationStatus.ATTENDING,
            "휴학" to RegistrationStatus.TAKEOFFSCHOOL,
            "졸업" to RegistrationStatus.GRADUATE
        )

        // 상태 값을 매핑
        val registrationStatus = statusMap[userData.status]
            ?: throw IllegalArgumentException("Unknown registration status: ${userData.status}")

        // AuthUserDTO 생성
        val userDTO = AuthUserDTO(
            username = binding.etId.text.toString(),
            name = userData.name,
            major = userData.major,
            studentGrade = userData.grade,
            registrationStatus = registrationStatus
        )

        // JWT 요청
        RetrofitClient.userApi.requestJwtToken(userDTO).enqueue(object : Callback<JwtResponse> {
            override fun onResponse(call: Call<JwtResponse>, response: Response<JwtResponse>) {
                if (response.isSuccessful) {
                    val authHeader = response.headers().get("Authorization")
                    if (authHeader != null) {
                        val token = authHeader.removePrefix("Bearer ")
                        Log.e("TOKEN", "JWT Token: $token")
                        if (!token.isNullOrEmpty()) {
                            JwtProvider.setToken(token)
                            saveJwtToken(token)
                            showSuccess("JWT 발급 성공!")
                            sendUserInfoToServer(userDTO)
                        } else {
                            showError("JWT 발급 실패: 토큰이 없습니다.")
                        }
                    } else {
                        showError("JWT 발급 실패: Authorization 헤더가 올바르지 않습니다.")
                    }
                } else {
                    showError("JWT 발급 실패: 서버 응답이 올바르지 않습니다.")
                }
            }

            override fun onFailure(call: Call<JwtResponse>, t: Throwable) {
                showError("JWT 발급 실패: 네트워크 오류가 발생했습니다.")
                Log.e("JWT_REQUEST", "JWT 발급 오류", t)
            }
        })


    }

    private fun sendUserInfoToServer(userDTO: AuthUserDTO) {
        // 사용자 정보를 서버에 전송
        RetrofitClient.userApi.sendUserInfoToServer(userDTO).enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val serverResponse = response.body()
                    if (serverResponse?.success == true) {
                        showSuccess("사용자 정보 전송 성공!")
                        navigateToChatting()
                    } else {
                        // 성공 여부만 체크하고, 메시지 관련 처리는 생략
                        showError("사용자 정보 전송 실패")
                    }
                } else {
                    showError("사용자 정보 전송 실패: 서버 응답이 올바르지 않습니다.")
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                showError("사용자 정보 전송 실패: 네트워크 오류가 발생했습니다.")
            }
        })
    }


    private fun saveJwtToken(token: String) {
        val sharedPref = getSharedPreferences("auth", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("jwt_token", token)
            apply()
        }
    }

    private fun navigateToChatting() {
        val intent = Intent(this@LoginActivity, Chatting::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccess(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}
