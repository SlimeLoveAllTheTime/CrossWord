package com.example.demo.view

import com.example.demo.app.Styles
import com.sun.prism.paint.Color
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.BackgroundImage
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.lang.IllegalStateException
import kotlin.system.exitProcess

lateinit var words: File

class MainView : View() {

    override val root = Pane()

    private val startBut = Button("Начать")

    private val createCrossWord = Button("Создать Кросссворд")

    private val chooseBut = Button("Выбрать")

    private val fileChooser = FileChooser()

    private var cells = Group()

    private var width1 = 1580.0

    private var height1 = 1150.0

    private var exitBut = Button("Выйти")

    init {

        with(root) {


            primaryStage.icons.add(Image("file:src\\main\\resources\\icon.png"))

            val image = ImageView(Image("file:src\\main\\resources\\backGround.jpg"))
            image.prefHeight(0.0)
            image.prefWidth(0.0)
            root.children.add(image)

            label(title) {
                addClass(Styles.heading)
            }

            borderpane {
                setPrefSize(width1, height1)
            }

            exitBut.relocate(790.0, 500.0)
            exitBut.setPrefSize(100.0, 40.0)
            root.children.add(exitBut)
            exitBut.action { exitProcess(0) }

            startBut.setPrefSize(100.0, 50.0)
            startBut.relocate(790.0, 300.0)

            chooseBut.setPrefSize(100.0, 50.0)
            chooseBut.relocate(790.0, 400.0)

            val chooseText = Text("Выберите файл")
            chooseText.font = Font.font("times new roman", FontWeight.BOLD, FontPosture.REGULAR, 17.0)
            chooseText.relocate(790.0 - 7, 300.0)

            createCrossWord.setPrefSize(150.0, 60.0)
            createCrossWord.relocate(765.0, 400.0)

            val rect = Rectangle()
            rect.height = 30.0
            rect.width = 1680.0
            rect.fill = javafx.scene.paint.Color.CORNFLOWERBLUE
            rect.relocate(0.0, 295.0)

            startBut.action {
                root.children.forEach { if (it != image && it != exitBut) it.hide() }
                root.children.addAll(rect, chooseBut, chooseText)
            }

            root.children.addAll(startBut)

            fileChooser.initialDirectory = File("src\\main\\resources")
            fileChooser.initialFileName = "src\\main\\resources\\DefaultWordsList.txt"

            chooseBut.action {
                chooseBut.hide()
                words = try {
                    fileChooser.showOpenDialog(null)
                } catch (e: IllegalStateException) {
                    File(fileChooser.initialFileName)
                }
                chooseText.text = "Выбранный файл: ${words.absolutePath}"
                val locate = 790.0 - 4 * "Выбранный файл: ${words.absolutePath}".length
                chooseText.relocate(locate, 300.0)
                root.children.add(createCrossWord)
            }

            val wordsRectangle = Rectangle()
            wordsRectangle.height = 700.0
            wordsRectangle.width = 600.0
            wordsRectangle.fill = javafx.scene.paint.Color.CORNFLOWERBLUE
            wordsRectangle.relocate(30.0, 30.0)

            val subWordsPanel = TextArea()
            subWordsPanel.font = Font.font("times new roman", FontWeight.BOLD, FontPosture.REGULAR, 17.0)
            subWordsPanel.relocate(35.0, 35.0)

            createCrossWord.action {
                root.children.forEach { if (it != image) it.hide() }
                root.children.addAll(wordsRectangle)
                createBoard()
            }

        }
    }

    private fun createBoard() {
        var rectangle: Rectangle
        for (i in 0..9) {
            for (j in 0..9) {
                rectangle = createCell(i, j)
                cells.children.add(rectangle)
            }
        }
        root.children.addAll(cells)
    }

    private fun createCell(i: Int, j: Int): Rectangle {
        val size = 83.0
        val rectangle = Rectangle()
        val start = (height1 + 310) / 2
        rectangle.height = size - 5
        rectangle.width = size - 5
        rectangle.fill = javafx.scene.paint.Color.CORNFLOWERBLUE
        rectangle.relocate(i * size + start, j * size + 13)
        return rectangle
    }

    private fun listOfWords(file: File): List<String> {
        val result = mutableListOf<String>()
        file.forEachLine {
            val split = it.split(", ")
            for (element in split) {
                result.add(element.toUpperCase())
            }
        }
        return result
    }


}



