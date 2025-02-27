package com.example.androidmidterm.presentation.model

data class User(
    val username: String = "",
    val age: Int = 0,
    val weight : Int = 0,
    val height : Int = 0
) {
    constructor() : this("",0, 0, 0)
}