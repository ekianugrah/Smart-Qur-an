package com.ekianugrah.myproject.respon

import com.google.gson.annotations.SerializedName
import com.ekianugrah.myproject.respon.ModelLocation

class ModelGeometry {
    @SerializedName("location")
    lateinit var modelLocation: ModelLocation
}