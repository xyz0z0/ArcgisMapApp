package xyz.xyz0z0.arcgismapapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var btnSimpleMap: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btnSimpleMap = findViewById(R.id.btnSimpleMap)
        btnSimpleMap.setOnClickListener {
            val intent = Intent(this, SimpleMapActivity::class.java)
            startActivity(intent)
        }
    }


}