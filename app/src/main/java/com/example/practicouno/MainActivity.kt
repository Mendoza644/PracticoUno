package com.example.practicouno

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var valid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_calculate.setOnClickListener {
            calculateProfits()
        }

    }

    private fun calculateProfits() {
        btn_calculate.visibility = View.INVISIBLE
        val value = edit_hours.text.toString()
        if (validateFields() && value.toInt() >= 1) {
            getDeductions(getHoursToCalculate(value.toInt()).toDouble())
            val intent = Intent(this, DetailsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("data", getDeductions(getHoursToCalculate(value.toInt()).toDouble()))
            intent.putExtra("employeeData", sendEmployeeData())
            startActivity(intent)
            edit_code.setText("")
            edit_name.setText("")
            edit_hours.setText("")
        } else {
            Toast.makeText(this, getString(R.string.require_text), Toast.LENGTH_LONG).show()
            btn_calculate.visibility = View.VISIBLE
        }
    }

    private fun sendEmployeeData(): Pair<String, Int> {
        return Pair(
            edit_name.text.toString().trim(),
            getHoursToCalculate(edit_hours.text.toString().trim().toInt())
        )
    }

    private fun getDeductions(wage: Double): Triple<Double, Double, Double> {
        val isssPercentage = 0.0525
        val afpPercentage = 0.0688
        val isrPercentage = 0.10

        return Triple(
            first = (isssPercentage * wage),
            second = afpPercentage * wage,
            third = isrPercentage * wage
        )
    }

    private fun getHoursToCalculate(workedHours: Int): Int {
        val limitA = 160
        val limitB = 200
        val limitC = 250
        var result = 1

        when (workedHours) {
            in 1..limitA -> {
                result = workedHours * 2
            }
            in limitA + 1..limitB -> {
                result = (((workedHours - limitA) * 3) + (limitA * 2))
            }
            in limitB + 1..limitC -> {
                result =
                    (((workedHours - limitB) * 4) + ((((workedHours - (workedHours - limitB)) - limitA) * 3) + (limitA * 2)))
            }
        }
        return result
    }

    private fun validateFields(): Boolean {
        when {
            edit_code.text.toString().trim().isEmpty() -> {
                layout_code.error = getString(R.string.require_text)
                valid = false
            }
            edit_name.text.toString().trim().isEmpty() -> {
                layout_name.error = getString(R.string.require_text)
                valid = false
            }
            edit_hours.text.toString().trim().isEmpty() -> {
                layout_hours.error = getString(R.string.require_text)
                valid = false
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    override fun onResume() {
        super.onResume()
        btn_calculate.visibility = View.VISIBLE
    }
}
