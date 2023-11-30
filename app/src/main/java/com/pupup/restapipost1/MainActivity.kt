package com.pupup.restapipost1

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.attribute.UserPrincipal

class MainActivity : AppCompatActivity() {
    lateinit var submitBtn : Button
    lateinit var nameTxt : EditText
    lateinit var emailTxt : EditText
    lateinit var userId : EditText
    lateinit var id : EditText
    lateinit var textView3 : TextView


    lateinit var processDialog: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitBtn = findViewById(R.id.submitBtn)
        nameTxt=findViewById(R.id.nameText)
        emailTxt = findViewById(R.id.emailText)
        userId = findViewById(R.id.userId)
        id = findViewById(R.id.id)
        textView3 = findViewById(R.id.textView3)



        processDialog = ProgressDialog(this)
        processDialog.setCancelable(false)
        processDialog.setMessage("Please Wait...")
        submitBtn.setOnClickListener {
            processDialog.show()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build()

            val userApi = retrofit.create(UserInterfaceApi::class.java)
            try {
                userApi.postData(UserDataModel(id.text.toString().toInt(),userId.text.toString().toInt(),nameTxt.text.toString(),emailTxt.text.toString()))
                    .enqueue(object : Callback<UserDataModel?> {
                        override fun onResponse(
                            call: Call<UserDataModel?>, response: Response<UserDataModel?>) {
                            val responseBody = response.body()
                            textView3.text = response.code().toString()
                            processDialog.dismiss()
                        }
                        override fun onFailure(call: Call<UserDataModel?>, t: Throwable) {
                            processDialog.dismiss()
                        }
                    })
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
            emailTxt.text.clear()
            nameTxt.text.clear()
            userId.text.clear()
            id.text.clear()
            userId.requestFocus()

        }

    }
}