package net.iessochoa.suarpl.suarquizcodeapp.model

/*
Provider/repositorio provisional de la lista de
categorías para aportar funcionalidad al wirefreame,
en un futuro se obtendrán de una BD
*/
class QzCategoryProvider {
    companion object{
        val qzCategoryList = listOf<QzCategory>(
            QzCategory(
                "Hola",
                "JAVA"
            ),
            QzCategory(
                "Hola",
                "KOTLIN"
            ),
            QzCategory(
                "Hola",
                "REACT"
            ),
            QzCategory(
                "Hola",
                "TYPESCRYPT"
            ),
            QzCategory(
                "Hola",
                "GIT"
            ),
            QzCategory(
                "Hola",
                "HTML"
            ),
            QzCategory(
                "Hola",
                "CSS"
            ),
        )
    }
}