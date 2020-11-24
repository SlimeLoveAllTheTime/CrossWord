package com.example.demo.model

class CrossWord(list: MutableList<Word>) {

    val wordsList = list

    private val size = 100

    var grid = Grid(size, size)

    private val finalGrid = Grid(size, size)

    private val someCoordinates = mutableMapOf<Word, Pair<Int, Int>>()

    private var finalSomeSolution = 0

    private var someSolution = 0



    fun solver() {

        grid.createGrayGrid()
        wordsList.sortBy { it.word.length }
        wordsList.reverse()

        var solutionFound = false

        for (word1 in wordsList) {

            if (solutionFound) break
            word1.setCoordinates(Pair(30, 30))
            grid.placeWordHorizontal(word1)
            someSolution += word1.word.length

            solutionFound = wordsBePlaced()

            for (word2 in wordsList ) {
                if (solutionFound) break
                if (!word2.isPlaced()) placeWord(word2)
            }

            if (someSolution > finalSomeSolution) {
                finalSomeSolution = someSolution
                finalGrid.swap(grid)
                setSomeCoordinates()
            }

            someSolution = 0

            if (!solutionFound) {
                grid.createGrayGrid()
                defaultCoordinates()
            }
        }

        if (!solutionFound) {
            grid = finalGrid
            for (word3 in wordsList) {
                word3.setCoordinates(someCoordinates[word3]!!)
            }
        }

        grid.resizeGrid()

    }


    private fun placeWord(word: Word) {
        var coordinates = Pair(-1, -1)

        if (!canPlace(word, coordinates)) {
            for (char in word.word) {
                for (someWord in wordsList) {
                    if (canPlace(word, coordinates)) break
                    if (someWord.isPlaced()) {
                        val charIndex = someWord.word.indexOf(char)
                        if (charIndex >= 0) coordinates = coordinates(word, wordsList.indexOf(someWord), charIndex)
                    }
                }
            }
        }

        if (canPlace(word, coordinates)) {
            word.setCoordinates(coordinates)
            if (word.vertical) grid.placeWordVertical(word)
            else if (word.horizontal) grid.placeWordHorizontal(word)
            someSolution += word.word.length
        }

    }


    private fun canPlace(word: Word, coordinates: Pair<Int, Int>): Boolean {

        val x = coordinates.first
        val y = coordinates.second
        var crossing = false
        var tooClose = false
        val placeGrid = grid.grid

        if (x != -1 && y != -1) {
            for (i in word.word.indices ) {
                if (word.vertical) {
                    crossing = (placeGrid[y + i][x] == word.word[i])
                    when {
                        (i == 0 && (placeGrid[y + word.word.length][x] != grid.grayChar
                                || placeGrid[y - 1][x] != grid.grayChar)) -> tooClose = true
                        !(crossing || placeGrid[y + i][x - 1] == grid.grayChar) -> tooClose = true
                        !(crossing || placeGrid[y + i][x + 1] == grid.grayChar) -> tooClose = true
                        !(crossing || placeGrid[y + i][x] == grid.grayChar) -> crossing = true
                    }
                } else {
                    word.horizontal = true
                    crossing = (placeGrid[y][x + i] == word.word[i])
                    when {
                        (i == 0 && (placeGrid[y][x + word.word.length] != grid.grayChar
                                || placeGrid[y][x - 1] != grid.grayChar)) -> tooClose = true
                        !(crossing || placeGrid[y - 1][x + i] == grid.grayChar) -> tooClose = true
                        !(crossing || placeGrid[y + 1][x + i] == grid.grayChar) -> tooClose = true
                        !(placeGrid[y][x + i] == grid.grayChar || crossing) -> crossing = true
                    }
                }
                if (tooClose && crossing) return false
            }
            return !tooClose && !crossing
        }
        return false
    }


    private fun coordinates(word: Word, placed: Int, placedWordLetterIndex: Int): Pair<Int, Int> {
        val x: Int
        val y: Int
        word.vertical = !wordsList[placed].vertical
        if (wordsList[placed].vertical) {
            x = wordsList[placed].x - word.word.indexOf(wordsList[placed].word[placedWordLetterIndex])
            y = wordsList[placed].y + placedWordLetterIndex
        } else {
            wordsList[placed].horizontal = true
            x = wordsList[placed].x + placedWordLetterIndex
            y = wordsList[placed].y -word.word.indexOf(wordsList[placed].word[placedWordLetterIndex])
        }
        return Pair(x, y)
    }


    private fun wordsBePlaced(): Boolean {
        wordsList.forEach{ if (!it.isPlaced()) return false }
        return true
    }


    private fun setSomeCoordinates() = wordsList.forEach { someCoordinates[it] = (Pair(it.x, it.y)) }


    private fun defaultCoordinates() = wordsList.forEach{ it.setCoordinates(Pair(-1, -1)) }

}
