package com.example.netologyvoiceassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val tag: String = "MainActivity"
        Log.d(tag, "start of onCreate function")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name: String = "Ivan"
        val surname: String = "Ivanov"
        var age: Int = 37
        var height: Double = 172.2

        val summary = "name: $name surname: $surname age: $age height: $height"

        val output: TextView = findViewById(R.id.output)
        output.text = summary
        Log.d(tag, "end of onCreate function")
    }
}
