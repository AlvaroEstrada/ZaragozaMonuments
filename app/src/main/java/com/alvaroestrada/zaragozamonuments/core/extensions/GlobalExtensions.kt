package com.alvaroestrada.zaragozamonuments.core.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.core.extensions
 * Creado por Álvaro Estrada en 12/11/2021
 * Todos los derechos reservados 2021
 **********/


fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, text, length)
}

fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, text, length)
}

fun Any?.isNotNull() = this != null

fun Activity.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)