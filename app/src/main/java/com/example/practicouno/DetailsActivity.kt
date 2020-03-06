package com.example.practicouno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import java.text.DecimalFormat

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val data = extras.getSerializable("data") as Triple<Double, Double, Double>
            val dataEmployee = extras.getSerializable("employeeData") as Pair<String, Int>

            name_employee_text?.text = dataEmployee.first
            val decimalFormat = DecimalFormat("$ ####.##")
            wage_employee_text?.text = decimalFormat.format(dataEmployee.second)
            health_employee_text?.text = decimalFormat.format(data.first)
            provision_employee_text?.text = decimalFormat.format(data.second)
            isr_employee_text?.text = decimalFormat.format(data.third)
            net_wage_text?.text = decimalFormat.format(
                (dataEmployee.second.toString()
                    .toDouble()).minus(data.first + data.second + data.third)
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
