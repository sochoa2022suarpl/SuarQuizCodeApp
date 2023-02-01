package net.iessochoa.suarpl.suarquizcodeapp.model

class QuestionModelProvider {

    companion object{
        val questionModelProviderList = listOf<QuestionModel>(

            QuestionModel(
                "¿Cómo se insertan COMENTARIOS en el código de Kotlin?",
                "//este es un comentario",
                "- - Esto es un comentario",
                "/* Esto es un comentario",
                "# Esto es un comentario",
                "//este es un comentario"
            ),
            QuestionModel(
                "¿Cómo se puede crear una variable con el valor numérico 5?",
                "val num = 5",
                "num = 5",
                "int num = 5",
                "num = 5 int",
                "val num = 5"
            ),
            QuestionModel(
                "¿Qué operador puede ser usado para comparar dos valores?",
                "==",
                "=",
                "<>",
                "><",
                "=="
            )
        )
        val  questionModelProviderListJava = listOf<QuestionModel>(
            QuestionModel(
                "¿prueba JAVA?",
                "//este es un comentario",
                "- - Esto es un comentario",
                "/* Esto es un comentario",
                "# Esto es un comentario",
                "//este es un comentario"
            ),
            QuestionModel(
                "¿prueba JAVA?",
                "val num = 5",
                "num = 5",
                "int num = 5",
                "num = 5 int",
                "val num = 5"
            ),
            QuestionModel(
                "¿prueba JAVA?",
                "==",
                "=",
                "<>",
                "><",
                "=="
            )


        )
    }
}