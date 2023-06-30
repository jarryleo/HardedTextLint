package cn.leo.lint

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test = Test()
        val text = getString(R.string.hardcoded_text)
        val t = "1硬编码"
        val t1 = "硬编码1 "
        val t2 = "22 "
        findViewById<TextView>(R.id.textView)?.text = getString(R.string.hardcoded_text_3,1)
        Toast.makeText(this, "硬编码", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "硬编1", Toast.LENGTH_SHORT).show()
    }
}