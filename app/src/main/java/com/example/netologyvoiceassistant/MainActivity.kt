package com.example.netologyvoiceassistant

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SimpleAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.wolfram.alpha.WAEngine
import com.wolfram.alpha.WAPlainText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder


lateinit var requestInput: TextInputEditText
lateinit var podsAdapter: SimpleAdapter
lateinit var progressBar: ProgressBar
lateinit var waEngine: WAEngine

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
        initWolframEngine()

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

    fun initWolframEngine() {
        waEngine = WAEngine().apply {
            appID = "7HWPYX-GHGY6U99L5"
            addFormat("plaintext")
        }
    }

    fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
            .apply {
                setAction(android.R.string.ok) {
                    dismiss()
                }
                show()
            }
    }

    fun askWolfram(request: String) {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val query = waEngine.createQuery().apply { input = request }
            runCatching {
                waEngine.performQuery(query)

            }.onSuccess { result ->
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (result.isError) {
                        showSnackbar(result.errorMessage)
                        return@withContext
                    }
                    if (!result.isSuccess) {
                        requestInput.error = getString(R.string.error_do_not_understand)
                        return@withContext
                    }
                    for (pod in result.pods){
                        if (pod.isError) continue
                        val content = StringBuilder()
                        for (subpod in pod.subpods) {
                          for (element in subpod.contents) {
                            if (element is WAPlainText) {
                                content.append(element.text)
                            }
                        }
                    }
                        pods.add(0,HashMap<String,String>().apply {
                            put("Title",pod.title)
                            put("Content", content.toString())
                        })
                }
                    podsAdapter.notifyDataSetChanged()
            }

        }.onFailure { t ->
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.GONE
                showSnackbar(t.message ?: getString(R.string.error_something_went_wrong))

            }
        }
    }
}
