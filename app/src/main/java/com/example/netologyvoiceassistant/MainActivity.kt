package com.example.netologyvoiceassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView(){
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar) //связываем переменную с тем,
        // что отражается на экране
        setSupportActionBar(toolbar) //связываем toolbar с приложением
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //переопределяем метод
        menuInflater.inflate(R.menu.menu_toolbar,menu)//объект отрисовывает приложение
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //чтобы на иконки можно нажимать
        when(item.itemId){
            R.id.action_stop -> {
                Log.d("TAG","action_stop")
                return true
            }
            R.id.action_clear -> {
                Log.d("TAG","action_clear")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
