package com.ekianugrah.myproject.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.ekianugrah.myproject.R
import com.ekianugrah.myproject.networking.ClientAsyncTask
import com.ekianugrah.myproject.networking.DaftarKota
import kotlinx.android.synthetic.main.jadwal.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Jadwal : AppCompatActivity() {

    private var listDaftarKota: MutableList<DaftarKota>? = null
    private var mDaftarKotaAdapter: ArrayAdapter<DaftarKota>? = null
    private var greetImg: ImageView? = null
    private var greetText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jadwal)

        val ToolBarAtas2: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(ToolBarAtas2)
        ToolBarAtas2.setLogoDescription(resources.getString(R.string.app_name))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        greetImg = findViewById(R.id.gambar)
        greetText = findViewById(R.id.pengingat)

        listDaftarKota = ArrayList()
        mDaftarKotaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            listDaftarKota as ArrayList<DaftarKota>
        )
        mDaftarKotaAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kota.adapter = mDaftarKotaAdapter
        kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val kota = mDaftarKotaAdapter!!.getItem(position)
                if (kota != null) {
                    loadJadwal(kota.id)
                }
            }

        }

        loadKota()
        greeting()
    }

    @SuppressLint("SetTextI18n")
    private fun greeting() {
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetText?.setText("Selamat Pagi, sudah sholat Subuh?")
            greetImg?.setImageResource(R.drawable.img_default_half_morning)
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText?.setText("Selamat Siang, sudah sholat Dzuhur?")
            greetImg?.setImageResource(R.drawable.img_default_half_afternoon)
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText?.setText("Selamat Sore, sudah sholat Ashar?")
            greetImg?.setImageResource(R.drawable.img_default_half_without_sun)
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText?.setText("Selamat Malam, sudah sholat Maghrib?\njangan tidur dulu ya, lanjut sholat Isya")
            greetText?.setTextColor(Color.BLACK)
            greetImg?.setImageResource(R.drawable.img_default_half_night)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun loadJadwal(id: Int?) {
        try {
            val idKota = id.toString()

            val current = SimpleDateFormat("yyyy-MM-dd")
            val tanggal = current.format(Date())

            val url = "https://api.banghasan.com/sholat/format/json/jadwal/kota/$idKota/tanggal/$tanggal"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("JadwalData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val objJadwal = jsonObj.getJSONObject("jadwal")
                        val obData = objJadwal.getJSONObject("data")

                        tv_tanggal.text = obData.getString("tanggal")
                        tv_subuh.text = obData.getString("subuh")
                        tv_dzuhur.text = obData.getString("dzuhur")
                        tv_ashar.text = obData.getString("ashar")
                        tv_maghrib.text = obData.getString("maghrib")
                        tv_isya.text = obData.getString("isya")

                        Log.d("dataJadwal", obData.toString())

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
            task.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun loadKota() {
        try {
            val url = "https://api.banghasan.com/sholat/format/json/kota"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("KotaData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val jsonArray = jsonObj.getJSONArray("kota")
                        var daftarKota: DaftarKota?
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            daftarKota = DaftarKota()
                            daftarKota.id = obj.getInt("id")
                            daftarKota.nama = obj.getString("nama")
                            listDaftarKota!!.add(daftarKota)
                        }
                        mDaftarKotaAdapter!!.notifyDataSetChanged()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
            task.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}