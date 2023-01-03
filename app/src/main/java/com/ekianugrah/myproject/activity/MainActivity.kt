package com.ekianugrah.myproject.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ekianugrah.myproject.R
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    var greetText: TextView? = null

    var REQ_PERMISSION = 100
    var strCurrentLatitude = 0.0
    var strCurrentLongitude = 0.0
    lateinit var strCurrentLatLong: String
    lateinit var strDate: String
    lateinit var strDateNow: String
    lateinit var simpleLocation: SimpleLocation
    lateinit var progressDialog: ProgressDialog
    lateinit var btnIntent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setInitLayout()
        setPermission()
        setLocation()
        setCurrentLocation()

        greetText = findViewById(R.id.selamat);

        greeting();
    }
    private fun setInitLayout() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Sedang menampilkan data...")

        val dateNow = Calendar.getInstance().time
        strDate = DateFormat.format("EEEE", dateNow) as String
        strDateNow = DateFormat.format("d MMMM yyyy", dateNow) as String

        tvToday.setText("$strDate,")
        tvDate.setText(strDateNow)
        mengaji.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    MagrhibMengaji::class.java
                )
            )
        }
        jadwal.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    Jadwal::class.java
                )
            )
        }
        babacaan.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    Babacaan::class.java
                )
            )
        }
        tahlilan.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    Tahlilan::class.java
                )
            )
        }
        weekly.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SehariHari::class.java
                )
            )
        }
        kisah.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    Kisah::class.java
                )
            )
        }
    }
    private fun setLocation() {
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }

        //get location
        strCurrentLatitude = simpleLocation.latitude
        strCurrentLongitude = simpleLocation.longitude

        //set location lat long
        strCurrentLatLong = "$strCurrentLatitude,$strCurrentLongitude"
    }

    private fun setCurrentLocation() {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1)
            if (addressList != null && addressList.size > 0) {
                val strCurrentLocation = addressList[0].locality
                tvLocation.text = strCurrentLocation
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQ_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                val intent = intent
                finish()
                startActivity(intent)
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun greeting() {
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar[Calendar.HOUR_OF_DAY]
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetText!!.text = "Selamat Pagi"
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText!!.text = "Selamat Siang"
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText!!.text = "Selamat Sore"
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText!!.text = "Selamat Malam"
        }
    }
}