package ru.netology.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.customview.ui.StatsView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<StatsView>(R.id.StatsView).data = listOf(
   //         500F, 500F, 200F, 300F // Для задания 2

             0.25F, 0.25F,  //Для задания 3

            )
    }
}