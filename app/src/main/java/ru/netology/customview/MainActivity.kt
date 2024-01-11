package ru.netology.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.customview.databinding.ActivityMainBinding
import ru.netology.customview.ui.StatsView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding) {
            btn.setOnClickListener {

                when (radioGroup.checkedRadioButtonId) {
                    R.id.normal_button -> StatsView.realisation = 1
                    R.id.rotation_button -> StatsView.realisation = 2
                    R.id.bidirectional_button -> StatsView.realisation = 3
                    else -> StatsView.realisation = 4
                }

                StatsView.data = listOf(0.25F, 0.25F, 0.25F, 0.25F)
            }
        }
    }
}