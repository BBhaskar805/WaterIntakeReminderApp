package com.example.waterintake

import android.content.Context


object WaterIntakeData {

    fun writeLS(context: Context, value: Boolean) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putBoolean("LOGIN_STATUS", value).apply()
    }

    fun readLS(context: Context): Boolean {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getBoolean("LOGIN_STATUS", false)
    }

    fun writeUserName(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USERNAME", value).apply()
    }

    fun readUserName(context: Context): String {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("USERNAME", "")!!
    }

    fun writeMail(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USERMAIL", value).apply()
    }

    fun readMail(context: Context): String {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("USERMAIL", "")!!
    }

    fun writePhoto(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USERPHOTO", value).apply()
    }

    fun readPhoto(context: Context): String {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("USERPHOTO", "")!!
    }

    fun writeDailyGoal(context: Context, value: Float) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putFloat("DAILY_GOAL", value).apply()
    }

    fun readDailyGoal(context: Context): Float {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getFloat("DAILY_GOAL", 0f)
    }


}