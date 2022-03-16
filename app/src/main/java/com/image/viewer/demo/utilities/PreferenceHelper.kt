package com.image.viewer.demo.utilities

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceHelper @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    private fun edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = sharedPreferences.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * puts a value for the given [key].
     */
    fun set(key: String, value: Any?) = when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")

    }

    /**
     * get a value from the given [key].
     */

    fun get(
        key: String,
        defaultValue: Any? = null
    ) {
        when (defaultValue) {
            String::class -> sharedPreferences.getString(key, defaultValue as? String ?: "") as Any
            Int::class -> sharedPreferences.getInt(key, defaultValue as? Int ?: -1)
            Boolean::class -> sharedPreferences.getBoolean(key, defaultValue as? Boolean ?: false)
            Float::class -> sharedPreferences.getFloat(key, defaultValue as? Float ?: -1f)
            Long::class -> sharedPreferences.getLong(key, defaultValue as? Long ?: -1)
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}