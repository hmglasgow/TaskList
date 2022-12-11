package com.example.tasklist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasklist.databinding.ActivityAddEditBinding
import com.example.tasklist.viewmodel.AddEditViewModel

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding

    private lateinit var viewModel: AddEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AddEditViewModel::class.java]

        binding = ActivityAddEditBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            store()
            finish()
        }

        title = if (intent.extras == null) {
            "Add"
        } else {
            "Edit"
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val task = parent?.findViewById<EditText>(R.id.task)

        parent?.findViewById<ImageButton>(R.id.dateButton)?.setOnClickListener {
            enterDate(parent)
        }

        parent?.findViewById<ImageButton>(R.id.timeButton)?.setOnClickListener {
            enterTime(parent)
        }

        parent?.findViewById<ImageButton>(R.id.repeatButton)?.setOnClickListener {
            enterRepeat(parent)
        }

        parent?.findViewById<EditText>(R.id.date)?.setText(Utils.formatDate(viewModel.year, viewModel.month, viewModel.day))
        parent?.findViewById<EditText>(R.id.time)?.setText(Utils.formatTime(6, 0))

        return super.onCreateView(parent, name, context, attrs)
    }

    private fun enterRepeat(parent: View) {
        TODO("Not yet implemented")
    }

    private fun enterTime(parent: View) {
        TimePickerDialog(parent.context, {
                _, hour, minute ->

            viewModel.hour = hour
            viewModel.minute = minute
            parent.findViewById<EditText>(R.id.time)?.setText(Utils.formatTime(hour, minute))
        },
        viewModel.hour, viewModel.minute, true).show()
    }

    private fun enterDate(parent: View) {
        DatePickerDialog(parent.context, { _, year, month, day ->
            viewModel.year = year
            viewModel.month = month
            viewModel.day = day
            parent.findViewById<EditText>(R.id.date)?.setText(Utils.formatDate(year, month, day))
        }, viewModel.year, viewModel.month, viewModel.day).show()
    }

    private fun store() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}