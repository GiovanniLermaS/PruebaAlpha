package com.example.pruebaalpha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import android.widget.EditText
import android.text.Editable
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listTypeUser =
            arrayListOf(getString(R.string.freeInvestment), getString(R.string.portfolioPurchase))
        val spinnerAdapter = ArrayAdapter<String>(this, R.layout.view_spinner, listTypeUser)
        spinnerAdapter.setDropDownViewResource(R.layout.view_spinner)
        spCreditApplication.adapter = spinnerAdapter
        spCreditApplication.onItemSelectedListener = this

        tIEPeriod.addTextChangedListener(MyTextWatcher(tIEPeriod))

        tIEIncomes.addTextChangedListener(MyTextWatcher(tIEIncomes))

        tIEOutgoings.addTextChangedListener(MyTextWatcher(tIEOutgoings))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, p2: Int, p3: Long) {
        parent?.getPositionForView(view)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onClick(v: View?) {
        when (v) {
            btAskCredit -> {
                if (svMain.setWarningsRequest()) return
                else {
                    if (tIEPeriod.text.toString().isNotEmpty())
                        if (tIEPeriod.text.toString().toInt() in 0 until 11 || tIEPeriod.text.toString().toInt() > 60) {
                            tIEPeriod.error = "Ingresa un número dentro de 12 a 60"
                            return
                        }
                    if (tIEIncomes.text.toString().isNotEmpty())
                        if (tIEOutgoings.text.toString().isNotEmpty()) {
                            val outgoings = tIEOutgoings.text.toString().toInt()
                            val incomes = tIEIncomes.text.toString().toInt()
                            if (incomes < outgoings)
                                tIEIncomes.error = "Los ingresos deben ser mayores a los gastos."
                        }
                }
            }
        }
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            when (view) {
                view.findViewById<TextInputEditText>(R.id.tIEPeriod) -> {
                    if (s.toString().isNotEmpty()) {
                        val count = s.toString().toInt()
                        if (count in 0 until 11 || count > 60)
                            tIEPeriod.error = "Ingresa un número dentro de 12 a 60"
                    }
                }
                view.findViewById<TextInputEditText>(R.id.tIEPeriod) -> {
                    if (s.toString().isNotEmpty()) {
                        val incomes = s.toString().toInt()
                        if (tIEOutgoings.text.toString().isNotEmpty()) {
                            val outgoings = tIEOutgoings.text.toString().toInt()
                            if (incomes < outgoings)
                                tIEIncomes.error = "Los ingresos deben ser mayores a los gastos."
                        }
                    }
                }
                view.findViewById<TextInputEditText>(R.id.tIEOutgoings) -> {
                    if (s.toString().isNotEmpty()) {
                        val outgoings = s.toString().toInt()
                        if (tIEIncomes.text.toString().isNotEmpty()) {
                            val incomes = tIEIncomes.text.toString().toInt()
                            if (incomes < outgoings)
                                tIEOutgoings.error = "Los ingresos deben ser mayores a los gastos."
                        }
                    }
                }
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}
