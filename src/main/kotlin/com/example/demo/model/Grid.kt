package com.example.demo.model

import java.io.File

class Grid {

    /**
     * Класс Сетка - это класс, который собой представляет логическое поле (логический grid),
     * где расставляются буквы. По факту это просто таблица 100 на 100 клеток, в которой потом будут располагаться буквы.
     */


    /**
     * В начале таблица имеет по сто строк и столбцов, но после расположения слов, размер таблицы будет изменен.
     * Это сделано для того, чтобы можно было вносить большое количество слов (без ограничений на общую длину).
     * Конечно чем больше значение этих параметров, тем из большего количества слов можно составить кроссворд.
     * Ограничение на нормальную работу кроссворда все-таки поставлю: 30 слов.
     */
    var rowLength = 100
    var columnLength = 100


    /**
     * Сама сетка (таблица, доска), куда будем вносить слова.
     * Сначала идут индексы вертикали (y), а потом горизонтали (x). То есть координаты элемента в сетки имеет вид:
     * grid[y][x].
     */
    var grid = Array(rowLength) { CharArray(columnLength) }


    /**
     * Символ, который указывает на то, что ячейка сетки пустая. Такой подход помогает с работой в некоторых методах.
     * Название идет от того, что все пустые клетки view имеют серый цвет.
     */
    val grayChar = '#'


    /**
     * Получаем из файла список слов
     */
    fun listOfWords(file: File): List<String> {
        val result = mutableListOf<String>()
        file.forEachLine {
            val split = it.split(", ")
            for (element in split) {
                result.add(element.toUpperCase())
            }
        }
        return result
    }


    /**
     * Заполняем сетку "пустыми клетками"
     */
    fun createGrayGrid(): Boolean {
        for (i in 0 until rowLength) {
            for (j in 0 until columnLength) grid[i][j] = grayChar
        }
        return true
    }


    /**
     * Меняем местами значения двух клеток
     */
    fun swap(swap: Grid) {
        for (i in 0 until rowLength) {
            for (j in 0 until columnLength) grid[i][j] = swap.grid[j][i]
        }
    }


    /**
     * Располагаем слово по вертикали
     */
    fun placeWordVertical(word: Word): Boolean {
        if (word.word.length > rowLength) return false
        val x = word.x
        val y = word.y
        var count = 0
        for (char in word.word) {
            grid[y + count][x] = char
            count++
        }
        word.vertical = true
        return true
    }


    /**
     * Располагаем слово по горизонтали
     */
    fun placeWordHorizontal(word: Word): Boolean {
        if (word.word.length > columnLength) return false
        val x = word.x
        val y = word.y
        var count = 0
        for (char in word.word) {
            grid[y][x + count] = char
            count++
        }
        word.horizontal = true
        return true
    }


    /**
     * Проверяем столбец на наличие элементов
     */
    fun columnIsEmpty(column: Int): Boolean {
        var counter = 0
        for (i in 0 until rowLength) {
            if (grid[i][column] == grayChar) counter++
            if (counter == rowLength) return true
        }
        return false
    }


    /**
     * Проверяем строку на наличие элементов
     */
    fun rowIsEmpty(row: Int): Boolean {
        var counter = 0
        for (i in 0 until columnLength) {
            if (grid[row][i] == grayChar) counter++
            if (counter == columnLength) return true
        }
        return false
    }


    /**
     * Удаление столбца
     */
    fun removeColumn(column: Int) {
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


    /**
     * Удаление строки
     */
    fun removeRow(row: Int) {
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


    /**
     * Изменение размера финальной сетки, в которой были расположены все слова
     * Минимальный размер финальной сетки равен единице
     */
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