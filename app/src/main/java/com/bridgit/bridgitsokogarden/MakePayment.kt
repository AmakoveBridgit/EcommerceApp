package com.bridgit.bridgitsokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class MakePayment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_make_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name=intent.getStringExtra("product_name")
        val cost=intent.getIntExtra("product_cost",0)


//        find views by their ids
        val txtname=findViewById<TextView>(R.id.txtProductName)
        val txtcost=findViewById<TextView>(R.id.txtProductCost)
        val pay=findViewById<Button>(R.id.pay)
        val edtphone=findViewById<EditText>(R.id.phone)


//        updating the textviews with the values passed from the intent

        txtname.text=name
        txtcost.text="Ksh $cost"

//        set onclick listener
        pay.setOnClickListener {

            val api="https://modcom2.pythonanywhere.com/api/mpesa_payment"


            val phonenumber=edtphone.text.toString().trim()

            val data=RequestParams()

            data.put("amount",cost)
            data.put("phone",phonenumber)

//            access the api helper

            val helper=ApiHelper(applicationContext)
            helper.post(api,data)





        }



        }









    }
