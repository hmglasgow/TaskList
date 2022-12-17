package com.example.tasklist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.tasklist.data.Task
import com.example.tasklist.database.Database
import com.example.tasklist.databinding.ActivityAddEditBinding
import com.example.tasklist.viewmodel.AddEditViewModel

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding

    private lateinit var viewModel: AddEditViewModel

    private lateinit var database: Database

    private var task: Task? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Database(this)

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

    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

        val view = super.onCreateView(parent, name, context, attrs)

        title = if (intent.extras == null) {
            "Add"
        } else {
            "Edit"
        }

        val taskEditView = parent?.findViewById<EditText>(R.id.taskEditView)
        taskEditView?.doOnTextChanged { text, _, _, _ ->
            viewModel.description = text.toString()
        }
        taskEditView?.setText(viewModel.description)

        parent?.findViewById<ImageButton>(R.id.dateButton)?.setOnClickListener {
            enterDate(parent)
        }

        parent?.findViewById<ImageButton>(R.id.timeButton)?.setOnClickListener {
            enterTime(parent)
        }

        parent?.findViewById<ImageButton>(R.id.repeatButton)?.setOnClickListener {
            enterRepeat(parent)
        }

        parent?.findViewById<EditText>(R.id.dateEditView)
            ?.setText(Utils.formatDate(viewModel.year, viewModel.month, viewModel.day))
        parent?.findViewById<EditText>(R.id.timeEditView)?.setText(Utils.formatTime(6, 0))

        parent?.findViewById<ImageButton>(R.id.otherButton)?.setOnClickListener {
            enterOther()
        }

        return view
    }

    private fun enterOther() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_other)

        val numberAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("two", "three", "four", "five", "six", "seven", "eight", "nine")
        )
        val numberSpinner = dialog.findViewById<Spinner>(R.id.dialogOtherNumberSpinner)
        numberSpinner.adapter = numberAdapter
        numberSpinner.setSelection(viewModel.otherNumber - 2)

        val typeAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("days", "weeks", "months", "years")
        )
        val typeSpinner = dialog.findViewById<Spinner>(R.id.dialogOtherTypeSpinner)
        typeSpinner.adapter = typeAdapter
        typeSpinner.setSelection(viewModel.otherType)

        dialog.findViewById<Button>(R.id.dialogOtherCancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.dialogOtherSaveButton).setOnClickListener {
            viewModel.otherNumber = numberSpinner.selectedItemPosition + 2
            viewModel.otherType = typeSpinner.selectedItemPosition
            redisplay()
            dialog.dismiss()
        }

        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()

        intent.extras?.getInt("id")
            ?.let { database.readTask(it) }
            ?.let { task = it }

        task?.let {
            viewModel.description = it.description
            viewModel.year = it.year
            viewModel.month = it.month
            viewModel.day = it.day
            viewModel.hour = it.hour
            viewModel.minute = it.minute
            viewModel.repeat = it.repeat
            viewModel.otherType = it.otherType
            viewModel.otherNumber = it.otherNumber
            viewModel.specificNumber = it.specificNumber
        }

        redisplay()
    }

    private fun redisplay() {
        findViewById<EditText>(R.id.taskEditView)?.setText(viewModel.description)

        findViewById<EditText>(R.id.timeEditView)?.setText(
            Utils.formatTime(
                hour = viewModel.hour,
                minute = viewModel.minute
            )
        )

        findViewById<EditText>(R.id.dateEditView)?.setText(
            Utils.formatDate(
                year = viewModel.year,
                month = viewModel.month,
                day = viewModel.day
            )
        )

        findViewById<EditText>(R.id.repeatEditView)?.setText(Utils.formatRepeat(repeat = viewModel.repeat))

        findViewById<LinearLayout>(R.id.otherLayout)?.visibility =
            if (viewModel.repeat == Task.repeatOther) VISIBLE else GONE

        val type = when (viewModel.otherType) {
            Task.otherDays -> "day"
            Task.otherWeeks -> "week"
            Task.otherMonths -> "month"
            Task.otherYears -> "year"
            else -> "?"
        }
        val text =
            "Every ${viewModel.otherNumber} $type${if (viewModel.otherNumber == 1) "" else "s"}"
        findViewById<EditText>(R.id.otherEditView)?.setText(text)
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
                "Once a year",
                "Other...",
                "Specific...",
            ), viewModel.repeat
        )
        { dialog, item ->
            viewModel.repeat = item
            parent.findViewById<EditText>(R.id.repeatEditView)?.setText(Utils.formatRepeat(item))
            setupOtherLayout(parent)
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun setupOtherLayout(parent: View) {
        parent.findViewById<LinearLayout>(R.id.otherLayout)?.visibility =
            if (viewModel.repeat == Task.repeatOther) VISIBLE else GONE
    }

    private fun enterTime(parent: View) {
        TimePickerDialog(
            parent.context, { _, hour, minute ->
                viewModel.hour = hour
                viewModel.minute = minute
                parent.findViewById<EditText>(R.id.timeEditView)
                    ?.setText(Utils.formatTime(hour, minute))
            },
            viewModel.hour, viewModel.minute, true
        ).show()
    }

    private fun enterDate(parent: View) {
        DatePickerDialog(parent.context, { _, year, month, day ->
            viewModel.year = year
            viewModel.month = month
            viewModel.day = day
            parent.findViewById<EditText>(R.id.dateEditView)
                ?.setText(Utils.formatDate(year, month, day))
        }, viewModel.year, viewModel.month, viewModel.day).show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun store() {
        if (task == null) {
            database.insert(
                Task(
                    id = -1,
                    description = viewModel.description,
                    day = viewModel.day,
                    month = viewModel.month,
                    year = viewModel.year,
                    hour = viewModel.hour,
                    minute = viewModel.minute,
                    repeat = viewModel.repeat,
                    otherType = viewModel.otherType,
                    otherNumber = viewModel.otherNumber,
                    specificNumber = viewModel.specificNumber
                )
            )
        } else {
            task?.let {
                database.update(
                    id = it.id,
                    description = viewModel.description,
                    day = viewModel.day,
                    month = viewModel.month,
                    year = viewModel.year,
                    hour = viewModel.hour,
                    minute = viewModel.minute,
                    repeat = viewModel.repeat,
                    otherType = viewModel.otherType,
                    otherNumber = viewModel.otherNumber,
                    specificNumber = viewModel.specificNumber
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}