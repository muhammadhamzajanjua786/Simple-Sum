package com.app.simplesum

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.simplesum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Hi
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.sum.observe(this) {
            if (it.isNotEmpty()) {
                binding.tvSumResult.text = "Sum is: $it"
            }
        }

        viewModel.resultMessage.observe(this) {
            if (it.isNullOrEmpty().not()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}
