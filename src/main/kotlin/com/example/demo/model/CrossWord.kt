package com.example.demo.model

class CrossWord(var wordsList: MutableList<Word>) {

    /**
     * Класс Кроссворд - главный класс логики, который создает нужную сетку, располагает слова и "решает" его.
     * В качестве параметра класса передаем список слов.
     */


    /**
         * Сетка (доска) и вспомогательная сетка. Вспомогательная сетка (finalGrid) нужна для получения финального вида сетки.
         * Также она используется для того, чтобы в ситуации, когда не удается расположить все слова, то было хоть какое-то
         * решение, где расположено максимальное количество слов.
     */
    var grid = Grid()
    private val finalGrid = Grid()


    /**
     * Список с координатами слов
     */
    val someCoordinates = mutableMapOf<Word, Pair<Int, Int>>()


    /**
     * Переменные, которые помогают найти лучшее решение, если нельзя расположить все слова в сетку
     */
    private var finalSomeSolution = 0
    private var someSolution = 0


    /**
     * Метод, который выдает финальное решение, то есть располагает все слова в сетке, изменяет размеры финальной
     * сетки, выдает лучшее решение, если полное решение невозможно.
     */
    fun solver() {
        grid.createGrayGrid()
        //сортируем слова по длине, выгоднее всего сначала располагать длинные слова
        wordsList.sortBy { it.word.length }
        wordsList.reverse()
        for (word1 in wordsList) {
            if (wordsBePlaced()) break
            word1.setCoordinates(Pair(50, 50))
            grid.placeWordHorizontal(word1)
            someSolution += word1.word.length
            //выставляем слова в сетку
            for (word2 in wordsList ) {
                if (wordsBePlaced()) break
                if (!word2.isPlaced()) placeWord(word2)
            }
            //лучшее решение (выставлено больше всего символов), если нет конечного решения, где выставлены все слова
            if (someSolution > finalSomeSolution) {
                finalSomeSolution = someSolution
                finalGrid.swap(grid)
                setSomeCoordinates()
            }
            someSolution = 0
            //если первое выбранное слово не подходит в качестве начального (то есть решение не было найдено),
            //то выбираем следующее слово в качестве начального
            if (!wordsBePlaced()) {
                grid.createGrayGrid()
                defaultCoordinates()
            }
        }
        //если все-таки лучшего решения нет, то выбираем решение с максимальным количеством выставленных символов
        if (!wordsBePlaced()) {
            grid = finalGrid
            for (word3 in wordsList) {
                word3.setCoordinates(someCoordinates[word3]!!)
            }
        }
        //изменяем размер сетки на минимально возможный
        grid.resizeGrid()
    }


    /**
     * Метод, который размещает слова в сетку
     */
    private fun placeWord(word: Word) {
        var coordinates = Pair(-1, -1)
        // Перебор слова и поиск слов с общим символом
        if (!canPlace(word, coordinates)) {
            for (char in word.word) {
                for (someWord in wordsList) {
                    if (canPlace(word, coordinates)) break
                    //если подходящее слово найдено, то рассчитываем нужные координаты
                    if (someWord.isPlaced()) {
                        val charIndex = someWord.word.indexOf(char)
                        if (charIndex > -1) coordinates = coordinates(word, wordsList.indexOf(someWord), charIndex)
                    }
                }
            }
        }
        //если найденное слово можно расположить в сетки, располагаем
        if (canPlace(word, coordinates)) {
            word.setCoordinates(coordinates)
            if (word.vertical) grid.placeWordVertical(word)
            else if (word.horizontal) grid.placeWordHorizontal(word)
            someSolution += word.word.length
        }
    }


    /**
     * Проверка можно ли расположить указанное слово в сетку (начиная с указанного места (координат))
     */
    fun canPlace(word: Word, coordinates: Pair<Int, Int>): Boolean {

        val x = coordinates.first
        val y = coordinates.second
        var crossing = false
        var close = false
        val placeGrid = grid.grid

        if (x != -1 && y != -1) {
            for (i in word.word.indices ) {
                if (word.vertical) {
                    crossing = (placeGrid[y + i][x] == word.word[i])
                    when {
                        (i == 0 && (placeGrid[y + word.word.length][x] != grid.grayChar
                                || placeGrid[y - 1][x] != grid.grayChar)) -> close = true
                        !(crossing || placeGrid[y + i][x - 1] == grid.grayChar) -> close = true
                        !(crossing || placeGrid[y + i][x + 1] == grid.grayChar) -> close = true
                        !(crossing || placeGrid[y + i][x] == grid.grayChar) -> crossing = true
                    }
                } else {
                    word.horizontal = true
                    crossing = (placeGrid[y][x + i] == word.word[i])
                    when {
                        (i == 0 && (placeGrid[y][x + word.word.length] != grid.grayChar
                                || placeGrid[y][x - 1] != grid.grayChar)) -> close = true
                        !(crossing || placeGrid[y - 1][x + i] == grid.grayChar) -> close = true
                        !(crossing || placeGrid[y + 1][x + i] == grid.grayChar) -> close = true
                        !(placeGrid[y][x + i] == grid.grayChar || crossing) -> crossing = true
                    }
                }
                if (close && crossing) return false
            }
            return !close && !crossing
        }
        return false
    }


    /**
     * Вычисление координат расположения слова, которые было найдено с пересечением предыдущего
     */
    fun coordinates(word: Word, placed: Int, charIndex: Int): Pair<Int, Int> {
        val x: Int
        val y: Int
        word.vertical = !wordsList[placed].vertical
        if (wordsList[placed].vertical) {
            x = wordsList[placed].x - word.word.indexOf(wordsList[placed].word[charIndex])
            y = wordsList[placed].y + charIndex
        } else {
            wordsList[placed].horizontal = true
            x = wordsList[placed].x + charIndex
            y = wordsList[placed].y - word.word.indexOf(wordsList[placed].word[charIndex])
        }
        return Pair(x, y)
    }


    /**
     * Проверка все ли слова были расположены в сетке
     */
    fun wordsBePlaced(): Boolean {
        wordsList.forEach { if (!it.isPlaced()) return false }
        return true
    }


    /**
     * Записываем в список someCoordinates координаты всех слов
     */
    fun setSomeCoordinates() = wordsList.forEach { someCoordinates[it] = (Pair(it.x, it.y)) }


    /**
     * Задаем всем словам дефолтные координаты
     */
    fun defaultCoordinates() = wordsList.forEach{ it.setCoordinates(Pair(-1, -1)) }

}
