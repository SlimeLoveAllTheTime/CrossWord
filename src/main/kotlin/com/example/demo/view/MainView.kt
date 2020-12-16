package com.example.demo.view

import com.example.demo.model.*
import com.example.demo.model.Grid
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.shape.Rectangle
import javafx.scene.text.*
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import kotlin.system.exitProcess

class MainView : View() {

    override val root = Pane()


    /**
     * Кнопки, которые буду использоваться повторно
     **/
    private val createCrossWord = Button("Создать Кросссворд")
    private val chooseBut = Button("Выбрать")
    private val startBut = Button("Начать")
    private var exitBut = Button("Выйти")

    //Выбор файла из директории
    private val fileChooser = FileChooser()

    //слова из списка
    private lateinit var words: File

    //Рисунок заднего плана
    private val image = ImageView(Image("file:src\\main\\resources\\backGround.jpg"))

    init {

        with(root) {

            /**
             * Создаем начальный экран
             */
            primaryStage.icons.add(Image("file:src\\main\\resources\\icon.png"))

            image.prefHeight(0.0)
            image.prefWidth(0.0)
            root.children.add(image)

            label(title) {
                addClass(Styles.heading)
            }

            borderpane {
                setPrefSize(1580.0, 1150.0)
            }

            exitBut.relocate(750.0, 500.0)
            exitBut.setPrefSize(100.0, 40.0)
            root.children.add(exitBut)
            exitBut.action { exitProcess(0) }

            startBut.setPrefSize(100.0, 50.0)
            startBut.relocate(750.0, 300.0)

            /**
             * Создаем дополнительные компоненты для следующего экрана
             */
            chooseBut.setPrefSize(100.0, 50.0)
            chooseBut.relocate(750.0, 400.0)

            val chooseText = Text("Выберите файл")
            chooseText.font = Font.font("times new roman", FontWeight.BOLD, FontPosture.REGULAR, 17.0)
            chooseText.relocate(750.0 - 7, 300.0)

            createCrossWord.setPrefSize(150.0, 60.0)
            createCrossWord.relocate(725.0, 400.0)

            val rect = Rectangle()
            rect.height = 30.0
            rect.width = 1680.0
            rect.fill = javafx.scene.paint.Color.CORNFLOWERBLUE
            rect.relocate(0.0, 295.0)

            /**
             * Скрываем компоненты первого экрана и добавляем компоненты нового экрана
             */
            startBut.action {
                root.children.forEach { if (it != image && it != exitBut) it.hide() }
                root.children.addAll(rect, chooseBut, chooseText)
            }

            root.children.addAll(startBut)
            //используем FileChooser для выбора файла со словами. Поддерживаемый формат файлов .txt
            fileChooser.initialDirectory = File("src\\main\\resources")
            fileChooser.initialFileName = "src\\main\\resources\\DefaultWordsList.txt"
            fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("TXT", "*.txt"))

            chooseBut.action {
                chooseBut.hide()
                words = try {
                    //открывается окно выбора файлов с дефолтной директорией
                    File(fileChooser.showOpenDialog(null).absolutePath)
                } catch (e: IllegalStateException) {
                    //если окно было закрыто, то выбранный файл - дефолтный файл
                    File(fileChooser.initialFileName)
                }
                //добавляем новые компоненты
                chooseText.text = "Выбранный файл: ${words.absolutePath}"
                val locate = 790.0 - 4 * "Выбранный файл: ${words.absolutePath}".length
                chooseText.relocate(locate, 300.0)
                root.children.add(createCrossWord)
            }

            val subWordsPanel = TextArea()
            subWordsPanel.font = Font.font("times new roman", FontWeight.BOLD, FontPosture.REGULAR, 17.0)
            subWordsPanel.relocate(35.0, 35.0)
            //создаем кроссворд
            createCrossWord.action {
                createCrossWordView()
            }
        }
    }

    /**
     * Этот метод создает всё представление финального окна
     */
    private fun createCrossWordView() {

        root.children.forEach { if (it != image) it.hide() }
        //создаем сетку
        val gridView = GridPane()
        //получаем из файла список слов
        val wordsList = Grid().listOfWords(words).map { Word(it) }.toMutableList()
        val crossWord = CrossWord(wordsList)
        //решаем кроссворд и заполняем сетку
        crossWord.solver()
        //логичеаская сетка, полученная из солвера
        val gridLogic = crossWord.grid
        val column = gridLogic.columnLength
        val row = gridLogic.rowLength
        //клетки являются кнопками, назначаем максимальный и минимальный размеры в зависимости от количества клеток
        val cellSize = if (row > 25) 25.5 else 30.0
        for (i in 0 until row) {
            for (j in 0 until column) {
                //создаем клетки с помощью кнопок
                val cell: Button
                val value = gridLogic.grid[i][j]
                //заполняем символом текст кнопки
                cell = if (value != '#') Button("$value")
                else Button()
                cell.font = if (row > 25) Font.font(10.0) else Font.font(12.0)
                cell.setPrefSize(cellSize, cellSize)
                cell.background = if (value == '#') {
                    Background(BackgroundFill(javafx.scene.paint.Color.DARKGRAY, CornerRadii(0.0), Insets(0.0)))
                }
                else Background(BackgroundFill(javafx.scene.paint.Color.WHITE, CornerRadii(0.0), Insets(0.0)))
                gridView.add(cell, j, i)
            }
        }

        val sideStart = (1580.0 - cellSize * column) / 2
        val topStart = (800.0 - cellSize * row) / 2
        gridView.relocate(sideStart, topStart)
        gridView.isGridLinesVisible = true

        val placedWord = mutableListOf<String>()
        val unplacedWord = mutableListOf<String>()
        crossWord.wordsList.forEach {
            if (it.isPlaced()) placedWord.add(it.word)
            else unplacedWord.add(it.word)
        }

        //вспомогательное поле, которое выводит список всех слов
        val controlGrid1 = GridPane()
        controlGrid1.add(Text("Кроссворд состоит из слов:"), 0, 0)
        for (i in 0 until placedWord.size) {
            val value = placedWord[i]
            if (i == placedWord.size - 1) {
                val text = Text(value)
                controlGrid1.add(text, 0, i + 1)
                break
            }
            val text = Text(value)
            controlGrid1.add(text, 0, i + 1)
        }
        controlGrid1.relocate(25.0, 50.0)
        val controlRect1 = Rectangle(250.0, 1580.0)
        controlRect1.relocate(0.0, 0.0)
        controlRect1.fill = javafx.scene.paint.Color.WHITE

        //вспомогательное поле, которое выводит список не использованных слов (если такие есть)
        val controlGrid2 = GridPane()
        controlGrid2.add(Text("Кроссворд Солвер не расположил слова:"), 0, 0)
        if (unplacedWord.isEmpty()) controlGrid2.add(Text("Кроссворд содержит все слова!"), 0, 1)
        else {
            for (i in 0 until unplacedWord.size) {
                val value = unplacedWord[i]
                if (i == unplacedWord.size - 1) {
                    val text = Text(value)
                    controlGrid2.add(text, 0, i + 1)
                    break
                }
                val text = Text(value)
                controlGrid2.add(text, 0, i + 1)
            }
        }
        controlGrid2.relocate(1360.0, 50.0)
        val controlRect2 = Rectangle(250.0, 1580.0)
        controlRect2.relocate(1352.0, 0.0)
        controlRect2.fill = javafx.scene.paint.Color.WHITE

        //кнопки для создания нового кроссворда
        val restartButton = Button("Новый кроссворд")
        restartButton.action {
            words = try {
                File(fileChooser.showOpenDialog(null).absolutePath)
            } catch (e: IllegalStateException) {
                File(fileChooser.initialFileName)
            }
            val createButton = Button("Создать кроссворд")
            createButton.action { createCrossWordView() }
            createButton.relocate(50.0, 800.0)
            restartButton.hide()
            root.children.add(createButton)
        }
        restartButton.relocate(50.0, 800.0)

        val exitButton = Button("Выйти")
        exitButton.action { exitProcess(0) }
        exitButton.relocate(1450.0, 800.0)

        //добавляем все компоненты
        root.children.addAll(gridView, controlRect1, controlRect2, controlGrid1, controlGrid2, restartButton, exitButton)

    }

}
