package com.ekianugrah.myproject.main

import com.google.gson.annotations.SerializedName

class ModelMain {
    private var name: String? = null
    private var thn_kelahiran: String? = null
    private var usia: String? = null
    private var description: String? = null
    private var tmp: String? = null
    private var image_url: String? = null
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getThn_kelahiran(): String? {
        return thn_kelahiran
    }

    fun setThn_kelahiran(thn_kelahiran: String?) {
        this.thn_kelahiran = thn_kelahiran
    }

    fun getUsia(): String? {
        return usia
    }

    fun setUsia(usia: String?) {
        this.usia = usia
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getTmp(): String? {
        return tmp
    }

    fun setTmp(tmp: String?) {
        this.tmp = tmp
    }

    fun getImage_url(): String? {
        return image_url
    }

    fun setImage_url(image_url: String?) {
        this.image_url = image_url
    }
}