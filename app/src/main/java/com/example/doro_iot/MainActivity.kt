package com.example.doro_iot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.doro_iot.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ledOn.setOnClickListener {
            var text = "/On"
            sendText(text)
            text = "/Switch"
            Handler(Looper.getMainLooper()).postDelayed({
                sendText(text)
            }, 200)


        }

        binding.ledOff.setOnClickListener {
            var text = "/Off"
            sendText(text)
            text = "/Switch"
            Handler(Looper.getMainLooper()).postDelayed({
                sendText(text)
            }, 200)
        }
    }

    private fun sendText(text: String) {
        val model = LightModel(text)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if(text == "/On"){
                    val response = LightObject.getRetrofitLightService.controlOn(LightModel(text.toString()))
                    if (response.isSuccessful){
                        Log.d("success","data sent successfully")
                    } else {
                        Log.e("error","failed to send data")
                    }
                } else if(text == "/Off"){
                    val response = LightObject.getRetrofitLightService.controlOff(LightModel(text.toString()))
                    if (response.isSuccessful){
                        Log.d("success","data sent successfully")
                    } else {
                        Log.e("error","failed to send data")
                    }
                } else if(text == "/Switch"){
                    val response = LightObject.getRetrofitLightService.control(LightModel(text.toString()))
                    if (response.isSuccessful){
                        Log.d("success","data sent successfully")
                    } else {
                        Log.e("error","failed to send data")
                    }
                }
            } catch (e: IOException){
                Log.e("error", "IOException: ${e.message}")
            }
        }
    }
}