package com.ekianugrah.myproject.networking

import android.content.Context
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DaftarKota {
    var id: Int? = null
    var nama: String? = null

    override fun toString(): String {
        return nama.toString()
    }
}