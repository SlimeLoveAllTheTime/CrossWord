package com.example.demo.model

class Grid(row: Int, column: Int) {

    var rowLength = row

    var columnLength = column

    var grid = Array(rowLength) { CharArray(columnLength) }

    val grayChar = '#'


    fun createGrayGrid() {
        for (i in 0 until rowLength) {
            for (j in 0 until columnLength) grid[i][j] = grayChar
        }
    }


    fun swap(swap: Grid) {
        for (i in 0 until rowLength) {
            for (j in 0 until columnLength) grid[i][j] = swap.grid[j][i]
        }
    }


    fun placeWordVertical(word: Word) {
        val y = word.y
        val x = word.x
        var count = 0
        for (char in word.word) {
            grid[y + count][x] = char
            count++
        }
        word.vertical = true
    }


    fun placeWordHorizontal(word: Word) {
        val y = word.y
        val x = word.x
        var count = 0
        for (char in word.word) {
            grid[y][x + count] = char
            count++
        }
        word.horizontal = true
    }


    private fun columnIsEmpty(column: Int): Boolean {
        var counter = 0
        for (i in 0 until rowLength) {
            if (grid[i][column] == grayChar) counter++
            if (counter == rowLength) return true
        }
        return false
    }


    private fun rowIsEmpty(row: Int): Boolean {
        var counter = 0
        for (i in 0 until columnLength) {
            if (grid[row][i] == grayChar) counter++
            if (counter == columnLength) return true
        }
        return false
    }


    private fun removeColumn(column: Int) {
        var indexControl = 0
        val reGrid = Array(rowLength) { CharArray(columnLength - 1) }
        for (i in 0 until rowLength) {
            for (j in 0 until columnLength) {
                if (j == column) {
                    indexControl = 1
                    continue
                }
                reGrid[i][j - indexControl] = grid[i][j]
            }
            indexControl = 0
        }
        columnLength--
        grid = reGrid
    }


    private fun removeRow(row: Int) {
        var indexControl = 0
        val reGrid = Array(rowLength - 1) { CharArray(columnLength) }
        for (i in 0 until rowLength) {
            if (i == row) {
                indexControl = 1
                continue
            }
            for (j in 0 until columnLength) {
                reGrid[i - indexControl][j] = grid[i][j]
            }
        }
        rowLength--
        grid = reGrid
    }


    fun resizeGrid() {
        var column = 0
        var row = 0
        while (columnLength > column && columnLength > 1) {
            if (columnIsEmpty(column)) removeColumn(column)
            else column++
        }
        while (rowLength > row && rowLength > 1) {
            if (rowIsEmpty(row)) removeRow(row)
            else row++
        }
    }

}