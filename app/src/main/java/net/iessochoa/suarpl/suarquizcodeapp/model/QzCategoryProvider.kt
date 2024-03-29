package net.iessochoa.suarpl.suarquizcodeapp.model

/*
Provider de categorías que se obtendrán desde el RecyclerView,
se deja este a modo de ejemplo para comprobar cómo se haría
localmente. Las preguntas sí se obtienen desde la BD Firestore
*/
class QzCategoryProvider {
    companion object{
        val qzCategoryList = listOf<QzCategory>(
            QzCategory(
                "https://cdn.iconscout.com/icon/free/png-256/kotlin-2038873-1720086.png",
                "KOTLIN",
                "KOTLIN"
            ),
            QzCategory(
                "https://logospng.org/download/react/logo-react-256.png",
                "REACT",
                "REACT"
            ),
            QzCategory(
                "https://logospng.org/download/typescript/typescript-256.png",
                "TYPESCRYPT",
                "TYPESCRYPT"
            ),
            QzCategory(
                "https://logospng.org/download/html-5/logo-html-5-256.png",
                "HTML",
                "HTML"
            ),
            QzCategory(
                "https://logospng.org/download/java/logo-java-256.png",
                "JAVA",
                "JAVA"
            )
        )
    }
}