package com.thatnawfal.binarsibc5challange.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AuthPreference(context: Context){

    private val preference : SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "Challange4Binar"
        private const val MODE = Context.MODE_PRIVATE
    }

    var userId: Int?
        get() = preference.getInt(
            PreferenceKey.PREF_USERID.first,
            PreferenceKey.PREF_USERID.second
        )
        set(value) = preference.edit {
            this.putInt(PreferenceKey.PREF_USERID.first, value?:0)
        }

}

object PreferenceKey {
    val PREF_USERID = Pair("PREF_USERID", 0)
}