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
                "KOTLIN"
            ),
            QzCategory(
                "https://logospng.org/download/react/logo-react-256.png",
                "REACT"
            ),
            QzCategory(
                "https://logospng.org/download/typescript/typescript-256.png",
                "TYPESCRYPT"
            ),
            QzCategory(
                "https://logospng.org/download/git/git-256.png",
                "GIT"
            ),
            QzCategory(
                "https://logospng.org/download/html-5/logo-html-5-256.png",
                "HTML"
            ),
            QzCategory(
                "https://logospng.org/download/mysql/mysql-256.png",
                "MYSQL"
            ),
            QzCategory(
                "https://logospng.org/download/java/logo-java-256.png",
                "JAVA"
            )
        )
    }
}