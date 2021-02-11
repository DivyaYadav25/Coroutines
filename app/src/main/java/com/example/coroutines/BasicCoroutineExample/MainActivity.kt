package com.example.coroutines.BasicCoroutineExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.coroutines.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private var button : Button? = null
    private var textView : TextView? = null
    private val RESULT1 ="result1"
    private val RESULT2 ="result2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        button?.setOnClickListener{
            //call a suspend function in CoroutineScope
            CoroutineScope(IO).launch {
                callResult1()
            }
        }
    }
    private suspend fun callResult1(){
        val result1 = getResult1()
        setTextOnMainThread(result1)

        val result2 = getResult2()
        setTextOnMainThread(result2)
    }

    //sets text on TextView using main thread
    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setText(input)
        }
    }

    private fun setText(input:String){
        val newText = textView?.text.toString()+"\n$input"
        textView?.text = newText
    }

    private suspend fun getResult1():String{
        println("result1 : ${"getResult1"}: ${Thread.currentThread().name}")
        //delays coroutine for 1 sec, it DOESN'T block UI thread
        delay(1000)
        return RESULT1
    }

    private suspend fun getResult2():String{
        println("result2 :${"getResult2"}: ${Thread.currentThread().name}")
        delay(1000)
        return RESULT2
    }

}