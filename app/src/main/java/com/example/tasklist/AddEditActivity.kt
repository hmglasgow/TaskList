package com.example.tasklist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.tasklist.database.Database
import com.example.tasklist.databinding.ActivityAddEditBinding
import com.example.tasklist.viewmodel.AddEditViewModel

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding

    private lateinit var viewModel: AddEditViewModel

    private lateinit var database: Database

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AddEditViewModel::class.java]

        binding = ActivityAddEditBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            if (viewModel.description.isNotEmpty()) {
                store()
                finish()
            }
        }

        title = if (intent.extras == null) {
            "Add"
        } else {
            "Edit"
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val task = parent?.findViewById<EditText>(R.id.task)
        task?.doOnTextChanged { text, _, _, _ ->
            viewModel.description = text.toString()
        }
        task?.setText(viewModel.description)

        parent?.findViewById<ImageButton>(R.id.dateButton)?.setOnClickListener {
            enterDate(parent)
        }

        parent?.findViewById<ImageButton>(R.id.timeButton)?.setOnClickListener {
            enterTime(parent)
        }

        parent?.findViewById<ImageButton>(R.id.repeatButton)?.setOnClickListener {
            enterRepeat(parent)
        }

        parent?.findViewById<EditText>(R.id.date)
            ?.setText(Utils.formatDate(viewModel.year, viewModel.month, viewModel.day))
        parent?.findViewById<EditText>(R.id.time)?.setText(Utils.formatTime(6, 0))

        parent?.context?.let { database = Database(it) }

        return super.onCreateView(parent, name, context, attrs)
    }

    private fun enterRepeat(parent: View) {
        val builder = AlertDialog.Builder(parent.context)
        builder.setTitle("Select repeat")
        builder.setSingleChoiceItems(
            arrayOf(
                "No repeat",
                "Once a day",
                "Once a day (Mon to Fri)",
                "Once a week",
                "Once a month",
                "Once a year"
            ), viewModel.repeat
        )
        { dialog, item ->
            viewModel.repeat = item
            parent.findViewById<EditText>(R.id.repeat)?.setText(Utils.formatRepeat(item))
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun enterTime(parent: View) {
        TimePickerDialog(
            parent.context, { _, hour, minute ->
                viewModel.hour = hour
                viewModel.minute = minute
                parent.findViewById<EditText>(R.id.time)?.setText(Utils.formatTime(hour, minute))
            },
            viewModel.hour, viewModel.minute, true
        ).show()
    }

    private fun enterDate(parent: View) {
        DatePickerDialog(parent.context, { _, year, month, day ->
            viewModel.year = year
            viewModel.month = month
            viewModel.day = day
            parent.findViewById<EditText>(R.id.date)?.setText(Utils.formatDate(year, month, day))
        }, viewModel.year, viewModel.month, viewModel.day).show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun store() {
        database.insert(
            description = viewModel.description,
            day = viewModel.day,
            month = viewModel.month,
            year = viewModel.year,
            hour = viewModel.hour,
            minute = viewModel.minute,
            repeat = viewModel.repeat
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}