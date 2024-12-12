package com.aqsoft.qqhome.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {

    @Volatile
    private var instance: SharedPreferences? = null

    fun getInstance(context: Context): SharedPreferences {
        return instance ?: synchronized(this) {
            instance ?: context.applicationContext.getSharedPreferences("QQHome", Context.MODE_PRIVATE).also {
                instance = it
            }
        }
    }

    fun putString(context: Context, key: String, value: String) {
        getInstance(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String = ""): String? {
        return getInstance(context).getString(key, defaultValue)
    }

    fun remove(context: Context, key: String) {
        getInstance(context).edit().remove(key).apply()
    }

    fun clear(context: Context) {
        getInstance(context).edit().clear().apply()
    }
}
