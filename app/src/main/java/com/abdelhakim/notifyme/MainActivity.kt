package com.abdelhakim.notifyme


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdelhakim.notifyme.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    companion object {
        var time = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notifyMe.setOnClickListener {
            time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            var (hours, min) = time.split(":").map { it.toInt() }
            min += 29
            if (min > 59) {
                min -= 60
                if (hours+1 < 24) hours += 1  else hours = 0
            }
            time = "$hours:$min"
            RemindersManager.startReminder(this,time)
        }
    }


}