package om.example.demo.test

import com.example.demo.model.CrossWord
import com.example.demo.model.Grid
import com.example.demo.model.Word
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class Test {


    @Test
    fun createGrayGridTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        assertTrue(grid1.createGrayGrid())
        grid1.grid.forEach { assertTrue(it.isNotEmpty()) }
        for (i in 0 until grid1.rowLength) {
            for (j in 0 until grid1.columnLength) assertEquals('#', grid1.grid[i][j])
        }
        grid1.grid[0][0] = 'a'
        assertNotEquals('#', grid1.grid[0][0])

    }


    @Test
    fun placeWordVerticalTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        grid1.createGrayGrid()
        val word1 = Word("TEST")
        word1.setCoordinates(Pair(1, 1))
        assertTrue(grid1.placeWordVertical(word1))
        assertTrue(word1.vertical)
        assertEquals('T', grid1.grid[1][1])
        assertEquals('E', grid1.grid[2][1])
        assertEquals('S', grid1.grid[3][1])
        assertEquals('T', grid1.grid[4][1])

        val grid2 = Grid()
        grid2.rowLength = 1
        grid2.columnLength = 1
        grid2.createGrayGrid()
        val word2 = Word("TEST")
        word2.setCoordinates(Pair(1, 1))
        assertEquals('#', grid2.grid[0][0])
        assertFalse(grid2.placeWordVertical(word2))
        assertFalse(word2.vertical)

    }


    @Test
    fun placeWordHorizontalTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        grid1.createGrayGrid()
        val word1 = Word("TEST")
        word1.setCoordinates(Pair(1, 1))
        assertTrue(grid1.placeWordHorizontal(word1))
        assertTrue(word1.horizontal)
        assertEquals('T', grid1.grid[1][1])
        assertEquals('E', grid1.grid[1][2])
        assertEquals('S', grid1.grid[1][3])
        assertEquals('T', grid1.grid[1][4])

        val grid2 = Grid()
        grid2.createGrayGrid()
        grid2.rowLength = 1
        grid2.columnLength = 1
        val word2 = Word("TEST")
        word2.setCoordinates(Pair(1, 1))
        assertFalse(grid2.placeWordHorizontal(word2))
        assertEquals('#', grid2.grid[0][0])
        assertFalse(word2.horizontal)

    }

    @Test
    fun columnIsEmptyTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        grid1.createGrayGrid()
        val word1 = Word("TEST")
        word1.setCoordinates(Pair(1, 1))
        assertTrue(grid1.placeWordVertical(word1))
        assertTrue(grid1.columnIsEmpty(0))
        assertFalse(grid1.columnIsEmpty(1))
        assertTrue(grid1.columnIsEmpty(2))
        assertTrue(grid1.columnIsEmpty(3))
        assertTrue(grid1.columnIsEmpty(4))

        val grid2 = Grid()
        grid2.createGrayGrid()
        grid2.rowLength = 1
        grid2.columnLength = 1
        val word2 = Word("TEST")
        word2.setCoordinates(Pair(1, 1))
        assertFalse(grid2.placeWordVertical(word2))
        assertTrue(grid2.columnIsEmpty(0))
        grid2.grid[0][0] = 'a'
        assertFalse(grid2.columnIsEmpty(0))

        val grid3 = Grid()
        grid3.rowLength = 5
        grid3.columnLength = 5
        grid3.createGrayGrid()
        val word3 = Word("TEST")
        word3.setCoordinates(Pair(1, 1))
        assertTrue(grid3.placeWordHorizontal(word3))
        assertTrue(grid3.columnIsEmpty(0))
        assertFalse(grid3.columnIsEmpty(1))
        assertFalse(grid3.columnIsEmpty(2))
        assertFalse(grid3.columnIsEmpty(3))
        assertFalse(grid3.columnIsEmpty(4))

    }


    @Test
    fun rowIsEmptyTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        grid1.createGrayGrid()
        val word1 = Word("TEST")
        word1.setCoordinates(Pair(1, 1))
        assertTrue(grid1.placeWordHorizontal(word1))
        assertTrue(grid1.rowIsEmpty(0))
        assertFalse(grid1.rowIsEmpty(1))
        assertTrue(grid1.rowIsEmpty(2))
        assertTrue(grid1.rowIsEmpty(3))
        assertTrue(grid1.rowIsEmpty(4))

        val grid2 = Grid()
        grid2.createGrayGrid()
        grid2.rowLength = 1
        grid2.columnLength = 1
        val word2 = Word("TEST")
        word2.setCoordinates(Pair(1, 1))
        assertFalse(grid2.placeWordHorizontal(word2))
        assertTrue(grid2.rowIsEmpty(0))
        grid2.grid[0][0] = 'a'
        assertFalse(grid2.rowIsEmpty(0))

        val grid3 = Grid()
        grid3.rowLength = 5
        grid3.columnLength = 5
        grid3.createGrayGrid()
        val word3 = Word("TEST")
        word3.setCoordinates(Pair(1, 1))
        assertTrue(grid3.placeWordVertical(word3))
        assertTrue(grid3.rowIsEmpty(0))
        assertFalse(grid3.rowIsEmpty(1))
        assertFalse(grid3.rowIsEmpty(2))
        assertFalse(grid3.rowIsEmpty(3))
        assertFalse(grid3.rowIsEmpty(4))

    }


    @Test
    fun removeColumnTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        grid1.createGrayGrid()
        val word1 = Word("TEST")
        word1.setCoordinates(Pair(1, 1))
        assertTrue(grid1.placeWordVertical(word1))
        assertFalse(grid1.columnIsEmpty(1))
        grid1.removeColumn(1)
        assertTrue(grid1.columnIsEmpty(1))

        val grid2 = Grid()
        grid2.createGrayGrid()
        grid2.rowLength = 1
        grid2.columnLength = 1
        val word2 = Word("TEST")
        word2.setCoordinates(Pair(1, 1))
        assertFalse(grid2.placeWordVertical(word2))
        assertTrue(grid2.columnIsEmpty(0))
        grid2.grid[0][0] = 'a'
        assertFalse(grid2.columnIsEmpty(0))
        grid2.removeColumn(0)
        assertEquals(0, grid2.columnLength)

    }


    @Test
    fun removeRowTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        grid1.createGrayGrid()
        val word1 = Word("TEST")
        word1.setCoordinates(Pair(1, 1))
        assertTrue(grid1.placeWordHorizontal(word1))
        assertFalse(grid1.rowIsEmpty(1))
        grid1.removeRow(1)
        assertTrue(grid1.rowIsEmpty(1))

        val grid2 = Grid()
        grid2.createGrayGrid()
        grid2.rowLength = 1
        grid2.columnLength = 1
        val word2 = Word("TEST")
        word2.setCoordinates(Pair(1, 1))
        assertFalse(grid2.placeWordHorizontal(word2))
        assertTrue(grid2.rowIsEmpty(0))
        grid2.grid[0][0] = 'a'
        assertFalse(grid2.rowIsEmpty(0))
        grid2.removeRow(0)
        assertEquals(0, grid2.rowLength)

    }


    @Test
    fun resizeGridTest() {

        val grid1 = Grid()
        grid1.rowLength = 5
        grid1.columnLength = 5
        grid1.createGrayGrid()
        val word1 = Word("TEST")
        word1.setCoordinates(Pair(1, 1))
        assertTrue(grid1.placeWordHorizontal(word1))
        assertEquals(5, grid1.columnLength)
        assertEquals(5, grid1.rowLength)
        grid1.resizeGrid()
        assertEquals(4, grid1.columnLength)
        assertEquals(1, grid1.rowLength)

        val grid2 = Grid()
        grid2.rowLength = 5
        grid2.columnLength = 5
        grid2.createGrayGrid()
        val word2 =  Word("TEST")
        word2.setCoordinates(Pair(0, 0))
        assertTrue(grid2.placeWordVertical(word2))
        assertTrue(grid2.placeWordHorizontal(word2))
        assertEquals(5, grid2.columnLength)
        assertEquals(5, grid2.rowLength)
        grid2.resizeGrid()
        assertEquals(4, grid2.columnLength)
        assertEquals(4, grid2.rowLength)

        val grid3 = Grid()
        grid3.rowLength = 5
        grid3.columnLength = 5
        grid3.createGrayGrid()
        assertEquals(5, grid3.columnLength)
        assertEquals(5, grid3.rowLength)
        grid3.resizeGrid()
        assertEquals(1, grid3.columnLength)
        assertEquals(1, grid3.rowLength)

    }


    @Test
    fun solverTest() {

        val wordsList1 = mutableListOf(Word("TEST"), Word("TEST"))
        val crossWord1 = CrossWord(wordsList1)
        val grid1 = Grid()
        grid1.columnLength = 4
        grid1.rowLength = 4
        grid1.createGrayGrid()
        grid1.grid[0][0] = 'T'
        grid1.grid[0][1] = 'E'
        grid1.grid[0][2] = 'S'
        grid1.grid[0][3] = 'T'
        grid1.grid[0][0] = 'T'
        grid1.grid[1][0] = 'E'
        grid1.grid[2][0] = 'S'
        grid1.grid[3][0] = 'T'
        crossWord1.solver()
        assertEquals(grid1.columnLength, crossWord1.grid.columnLength)
        assertEquals(grid1.rowLength, crossWord1.grid.rowLength)
        for (i in 0 until crossWord1.grid.rowLength) {
            for (j in 0 until crossWord1.grid.columnLength) {
                assertEquals(grid1.grid[i][j], crossWord1.grid.grid[i][j])
            }
        }

        val wordsList2 = mutableListOf(Word("КОТ"), Word("ДОМ"))
        val crossWord2 = CrossWord(wordsList2)
        val grid2 = Grid()
        grid2.columnLength = 3
        grid2.rowLength = 3
        grid2.createGrayGrid()
        grid2.grid[0][1] = 'К'
        grid2.grid[1][0] = 'Д'
        grid2.grid[1][1] = 'О'
        grid2.grid[1][2] = 'М'
        grid2.grid[2][1] = 'Т'
        crossWord2.solver()
        assertEquals(grid2.columnLength, crossWord2.grid.columnLength)
        assertEquals(grid2.rowLength, crossWord2.grid.rowLength)
        for (i in 0 until crossWord2.grid.rowLength) {
            for (j in 0 until crossWord2.grid.columnLength) {
                assertEquals(grid2.grid[i][j], crossWord2.grid.grid[i][j])
            }
        }

        val wordsList3 = mutableListOf(Word("ТИМ"), Word("КИТ"), Word("НОЖ"))
        val crossWord3 = CrossWord(wordsList3)
        val grid3 = Grid()
        grid3.columnLength = 3
        grid3.rowLength = 3
        grid3.createGrayGrid()
        grid3.grid[0][0] = 'К'
        grid3.grid[1][0] = 'И'
        grid3.grid[2][0] = 'Т'
        grid3.grid[2][0] = 'Т'
        grid3.grid[2][1] = 'И'
        grid3.grid[2][2] = 'М'
        crossWord3.solver()
        assertEquals(grid3.columnLength, crossWord3.grid.columnLength)
        assertEquals(grid3.rowLength, crossWord3.grid.rowLength)
        for (i in 0 until crossWord3.grid.rowLength) {
            for (j in 0 until crossWord3.grid.columnLength) {
                assertEquals(grid3.grid[i][j], crossWord3.grid.grid[i][j])
            }
        }

    }


    @Test
    fun canPlaceTest() {

        val words = mutableListOf(Word(""))
        val crossWord1 = CrossWord(words)
        crossWord1.grid.columnLength = 6
        crossWord1.grid.rowLength = 6
        crossWord1.grid.createGrayGrid()
        val word1 = Word("TEST")
        word1.horizontal = true
        assertTrue(crossWord1.canPlace(word1, Pair(1, 1)))
        assertFalse(crossWord1.canPlace(word1, Pair(2, 2)))
        assertTrue(crossWord1.canPlace(word1, Pair(1, 2)))
        assertFalse(crossWord1.canPlace(word1, Pair(2, 1)))
        word1.horizontal = false
        word1.vertical = true
        assertTrue(crossWord1.canPlace(word1, Pair(2, 1)))

        val crossWord2 = CrossWord(words)
        crossWord2.grid.columnLength = 3
        crossWord2.grid.rowLength = 3
        crossWord2.grid.createGrayGrid()
        val word2 = Word("T")
        word2.vertical = true
        assertTrue(crossWord2.canPlace(word2, Pair(1, 1)))

        val crossWord3 = CrossWord(words)
        crossWord3.grid.columnLength = 15
        crossWord3.grid.rowLength = 15
        crossWord3.grid.createGrayGrid()
        val word3 = Word("КЛАВИАТУРА")
        word3.vertical = true
        assertTrue(crossWord3.canPlace(word3, Pair(1, 1)))
        assertTrue(crossWord3.canPlace(word3, Pair(2, 1)))
        assertTrue(crossWord3.canPlace(word3, Pair(12, 1)))
        word3.vertical = false
        assertTrue(crossWord3.canPlace(word3, Pair(1, 1)))
        assertTrue(crossWord3.canPlace(word3, Pair(2, 1)))
        assertFalse(crossWord3.canPlace(word3, Pair(12, 1)))

    }


    @Test
    fun defaultCoordinatesTest() {
        val word1 = Word("A")
        val word2 = Word("B")
        val word3 = Word("C")
        word1.setCoordinates(Pair(1, 2))
        word2.setCoordinates(Pair(2, 3))
        word3.setCoordinates(Pair(3, 4))
        val words = mutableListOf(word1, word2, word3)
        val crossWord = CrossWord(words)
        crossWord.wordsList.forEach {
            assertTrue(it.x != -1)
            assertTrue(it.y != -1)
        }
        crossWord.defaultCoordinates()
        crossWord.wordsList.forEach {
            assertEquals(-1, it.x)
            assertEquals(-1, it.y)
        }

    }


    @Test
    fun setSomeCoordinatesTest() {

        val word1 = Word("A")
        val word2 = Word("B")
        val word3 = Word("C")
        word1.setCoordinates(Pair(1, 2))
        word2.setCoordinates(Pair(2, 3))
        word3.setCoordinates(Pair(3, 4))
        val words = mutableListOf(word1, word2, word3)
        val crossWord = CrossWord(words)
        crossWord.setSomeCoordinates()
        crossWord.wordsList.forEach {
            assertEquals(Pair(it.x, it.y), crossWord.someCoordinates[it])
        }

    }


    @Test
    fun wordsBePlacedTest() {

        val word1 = Word("A")
        val word2 = Word("B")
        val word3 = Word("C")
        word1.setCoordinates(Pair(1, 2))
        word2.setCoordinates(Pair(2, 3))
        word3.setCoordinates(Pair(3, 4))
        val words = mutableListOf(word1, word2, word3)
        val crossWord = CrossWord(words)
        assertTrue(crossWord.wordsBePlaced())

    }


    @Test
    fun coordinatesTest() {

        val word1 = Word("TEST")
        val word2 = Word("TEMP")
        val words1 = mutableListOf(word1, word2)
        val crossWord1 = CrossWord(words1)
        assertEquals(-1, word2.x)
        assertEquals(-1, word2.y)
        word1.setCoordinates(Pair(1, 1))
        val coordinate1 = crossWord1.coordinates(word2, 0, 0)
        word2.x = coordinate1.second
        word2.y = coordinate1.first
        assertNotEquals(-1, word2.x)
        assertNotEquals(-1, word2.y)

    }


}