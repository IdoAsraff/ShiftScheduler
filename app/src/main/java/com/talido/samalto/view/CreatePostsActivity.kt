package com.talido.samalto.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.talido.samalto.R
import com.talido.samalto.databinding.ActivityCreatePostsBinding
import com.talido.samalto.databinding.ActivityScheduleStartBinding

class CreatePostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.prev.setOnClickListener {
            finish()
        }
        binding.next.setOnClickListener {
            val intent = Intent(this, GuardsAmountActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
}