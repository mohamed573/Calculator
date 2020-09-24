package learnprogramming.academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

// after adding synthetic import , all the wedget in the layout can be accessed just like any property
// the kotlin extention pugin creates properties corresponding to each of the view in the  layout
// there's no need to write findViewByid
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

@Suppress("PLUGIN_WARNING")
class MainActivity : AppCompatActivity() {

//    private lateinit var result: EditText
//    private lateinit var newNumber : EditText

    //you're defining  a fun that'll be called to assign to the property
    // called the fun the 1st time the property accessed then the value is cached
    // the lazy fun is thready safe coz will be called once
  //  private val displayOperation by lazy(LazyThreadSafetyMode.NONE){findViewById<TextView>(R.id.operation)}

    // Variable to hold the operand and type of calculation
    private var operand1 : Double? = null
    private var pendingOperation = " = "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)

        //Data input button
//        val button0 : Button = findViewById(R.id.button0)
//        val button1 : Button = findViewById(R.id.button1)
//        val button2 : Button = findViewById(R.id.button2)
//        val button3 : Button = findViewById(R.id.button3)
//        val button4 : Button = findViewById(R.id.button4)
//        val button5 : Button = findViewById(R.id.button5)
//        val button6 : Button = findViewById(R.id.button6)
//        val button7 : Button = findViewById(R.id.button7)
//        val button8 : Button = findViewById(R.id.button8)
//        val button9 : Button = findViewById(R.id.button9)
//        val buttonDot : Button = findViewById(R.id.buttonDot)

        // Operation buttons
//        val buttonEqual : Button = findViewById(R.id.buttonEqual)
//        val buttonDivide: Button  = findViewById(R.id.buttonDivide)
//        val buttonMultiply: Button  = findViewById(R.id.buttonMultiply)
//        val buttonMinus : Button = findViewById(R.id.buttonMinus)
//        val buttonPlus : Button = findViewById(R.id.buttonPlus)

        val listener = View.OnClickListener { v->
            val b = v as Button
            newNumber.append(b.text)

        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performeOperation(value , op)
            }
            catch (e : NumberFormatException){
                newNumber.setText("")
            }
            pendingOperation = op
            operation.text = pendingOperation
        }
        buttonEqual.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)

        buttonNeg.setOnClickListener { v ->
            val value = newNumber.text.toString()
            if(value.isEmpty()){
                newNumber.setText("-")
            }
            else{
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                }catch (e : NumberFormatException){
                    newNumber.setText(" ")
                }
            }


        }
        buttonClear.setOnClickListener {
            operand1 = null
            pendingOperation = "="
            operation.text = pendingOperation
            result.text.clear()
            newNumber.text.clear()
        }

    }

    private fun performeOperation (value : Double , operation : String){
        if(operand1 == null){
            operand1  =value
        }
        else{
            if( pendingOperation == "="){
                pendingOperation = operation
            }

            when (pendingOperation){
                "=" -> operand1 = value
                "/" -> if(value == 0.0){
                    Double.NaN    // handle attempt to divide by zero
                }else{
                    operand1!! / value
                }

                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value

            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG , "onSaveInstanceState : Called")
        super.onSaveInstanceState(outState)

        if (operand1 != null){
            outState.putDouble(STATE_OPERAND1 , operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED , true)
        }
        outState.putString(STATE_PENDING_OPERATION , pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG , "onRestoreInstanceState : Called")
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(STATE_OPERAND1_STORED , false)){
            savedInstanceState.getDouble(STATE_OPERAND1)
        }
        else{
            null
        }
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION , " = ")
        operation.text = pendingOperation

    }
}
