package net.iessochoa.suarpl.suarquizcodeapp.model

/*
Provider de categorías que se obtendrán desde el RecyclerView,
se deja este a modo de ejemplo para comprobar cómo se haría
localmente. Las preguntas sí se obtienen desde la BD Firestore
*/
class QzCategoryProviderPremiumEng {
    companion object{
        val qzCategoryListEng = listOf<QzCategory>(
            QzCategory(
                "https://cdn.iconscout.com/icon/free/png-256/kotlin-2038873-1720086.png",
                "KOTLIN",
                "enKOTLIN"
            ),
            QzCategory(
                "https://logospng.org/download/react/logo-react-256.png",
                "REACT",
                "enREACT"
            ),
            QzCategory(
                "https://logospng.org/download/typescript/typescript-256.png",
                "TYPESCRIPT",
                "enTYPESCRIPT"
            ),
            QzCategory(
                "https://logospng.org/download/git/git-256.png",
                "GIT",
                "enGIT"
            ),
            QzCategory(
                "https://logospng.org/download/html-5/logo-html-5-256.png",
                "HTML",
                "enHTML"
            ),
            QzCategory(
                "https://logospng.org/download/mysql/mysql-256.png",
                "MYSQL",
                "enMYSQL"
            ),
            QzCategory(
                "https://logospng.org/download/java/logo-java-256.png",
                "JAVA",
                "enJAVA"
            )
        )
    }
}