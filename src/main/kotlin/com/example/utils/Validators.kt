package com.example.utils

fun String.isValidEmail():Boolean{
    return this.contains("@")
}