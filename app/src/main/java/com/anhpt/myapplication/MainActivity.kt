package com.anhpt.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// MainActivity.kt
// Import các thư viện cần thiết

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()
    }

    private fun fetchData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://lebavui.github.io/jsons/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: retrofit2.Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        showData(users)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showData(users: List<User>) {
        val adapter = UserAdapter(users) { user ->
            // Xử lý sự kiện khi nhấn vào một phần tử trong danh sách
            // Mở bản đồ Google Map với tọa độ lat và lng của người được chọn
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:${user.address.geo.lat},${user.address.geo.lng}?q=${user.address.street},${user.address.city}")
            )
            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }
}
