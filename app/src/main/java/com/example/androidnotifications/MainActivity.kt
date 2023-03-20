package com.example.androidnotifications

import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.androidnotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 32) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    ACCESS_NOTIFICATION_POLICY
                ) == PackageManager.PERMISSION_GRANTED
            )
                return;
            val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission(),
                { Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show() })
            launcher.launch(POST_NOTIFICATIONS);
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)


        binding.sendNoticeButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(this, "normal")
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setSmallIcon(R.drawable.sync_alt_fill0_wght400_grad0_opsz48)
                .setLargeIcon(
                    BitmapFactory.decodeResource(resources, R.drawable.vives_logo)
                )
                .setContentIntent(pi)
                .build()
            manager.notify(1, notification)
        }
    }
}