package com.example.lyceum13epass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.WriterException

class MainActivity : AppCompatActivity() {

    var im: ImageView? = null
    var bLogout: Button? = null
    var tv_email_id: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        im = findViewById(R.id.imageView)
        tv_email_id = findViewById(R.id.tv_email_id)
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")
        val nameId = intent.getStringExtra("name_id")
        bLogout = findViewById(R.id.b_logout)
        generatorQrCode("$userId")
        bLogout?.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        tv_email_id?.text = "Email :: $emailId"
    }

    private fun generatorQrCode(text: String){
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 600)
        try{
            val bMap = qrGenerator.encodeAsBitmap()
            im?.setImageBitmap(bMap)
        } catch (e: WriterException) {

        }
    }

}