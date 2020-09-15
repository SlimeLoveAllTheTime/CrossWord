package com.example.demo.view

import com.example.demo.app.Styles
import tornadofx.*

class MainView : View("Выберете загаданные слова!") {

    val size = 60.0

    override val root = vbox {
        label(title) {
            addClass(Styles.heading)
        }

    }

}


