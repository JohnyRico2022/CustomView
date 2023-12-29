package ru.netology.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.netology.customview.ui.StatsView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<StatsView>(R.id.StatsView)
        val button = findViewById<Button>(R.id.btn)

        button.setOnClickListener {
            view.data = listOf(0.25F, 0.25F, 0.25F, 0.25F)
        }
    }
}