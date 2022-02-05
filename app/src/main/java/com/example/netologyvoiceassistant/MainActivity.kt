package com.example.netologyvoiceassistant

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SimpleAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

lateinit var requestInput: TextInputEditText
lateinit var podsAdapter: SimpleAdapter
lateinit var progressBar: ProgressBar

var pods = mutableListOf<HashMap<String, String>>(
    HashMap<String, String>().apply {
        put("Title", "Title 1")
        put("Content", "Content 1")
    },
    HashMap<String, String>().apply {
        put("Title", "Title 2")
        put("Content", "Content 2")
    },
    HashMap<String, String>().apply {
        put("Title", "Title 3")
        put("Content", "Content 3")
    },
    HashMap<String, String>().apply {
        put("Title", "Title 4")
        put("Content", "Content 4")
    }
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar) //связываем переменную с тем,
        // что отражается на экране
        setSupportActionBar(toolbar) //связываем toolbar с приложением
        requestInput = findViewById(R.id.text_input_edit)
        val podsList: ListView = findViewById(R.id.pods_list)
        podsAdapter = SimpleAdapter(
            applicationContext,
            pods,
            R.layout.item_pod,
            arrayOf("Title", "Content"),
            intArrayOf(R.id.title, R.id.content)
        )
        podsList.adapter = podsAdapter

        val voiceInputButton: FloatingActionButton = findViewById(R.id.voice_input_button)
        voiceInputButton.setOnClickListener {
            Log.d("TAG", "FAB")
        }
        progressBar = findViewById(R.id.progress_bar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //переопределяем метод
        menuInflater.inflate(R.menu.menu_toolbar, menu)//объект отрисовывает приложение
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //чтобы на иконки можно нажимать
        when (item.itemId) {
            R.id.action_stop -> {
                Log.d("TAG", "action_stop")
                return true
            }
            R.id.action_clear -> {
                Log.d("TAG", "action_clear")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
