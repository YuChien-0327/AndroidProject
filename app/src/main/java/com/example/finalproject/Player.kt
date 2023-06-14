package com.example.finalproject

import java.io.Serializable

class Player(number: Int) : Serializable {
    var number: Int = number
    var win_score: Int = 0
    var loss_score: Int = 0
    var winTypesArr = arrayOf(0, 0, 0, 0)
    val lossTypesArr = arrayOf(0, 0, 0, 0, 0, 0, 0)
}