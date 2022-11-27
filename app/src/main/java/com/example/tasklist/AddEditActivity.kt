package com.example.tasklist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tasklist.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            finish()
        }

        title = if (intent.extras == null) {
            "Add"
        } else {
            "Edit"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}