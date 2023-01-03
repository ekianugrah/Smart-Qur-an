package com.ekianugrah.myproject.respon

import com.google.gson.annotations.SerializedName
import com.ekianugrah.myproject.respon.ModelResults

class ModelResultNearby {
    @SerializedName("results")
    lateinit var modelResults: List<ModelResults>
}