package com.example.demo.model

class Word(val word: String) {

    var x = -1

    var y = -1

    var vertical = false

    var horizontal = false

    fun isPlaced(): Boolean = x != -1 && y != -1

    fun setCoordinates(coordinates: Pair<Int, Int>) {
        x = coordinates.first
        y = coordinates.second
    }

}