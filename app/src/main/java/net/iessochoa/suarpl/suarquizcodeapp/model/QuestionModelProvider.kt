package net.iessochoa.suarpl.suarquizcodeapp.model

class QuestionModelProvider {

    companion object{
        val questionModelProviderListKotlin = listOf<QuestionModel>(

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
            ),
            QuestionModel(
                    "¿Cuál es una sintaxis correcta para generar \"Hello World\" en Kotlin?",
            "Console.WriteLine(\"Hello World\");",
            "System.out.printline(\"Hello World\")",
            "cout << \"Hello World\";",
            "println(\"Hello World\")",
            "println(\"Hello World\")"
            ),
            QuestionModel(
                "¿Cuál es la palabra clave para declarar una función?",
                "define",
                "decl",
                "fun",
                "function",
                "fun"
            ),
            QuestionModel(
                "¿Cuál es la palabra clave para declarar una función?",
                "define",
                "decl",
                "fun",
                "function",
                "fun"
            ),
            QuestionModel(
                "¿Cómo podrías crear una variable de tipo float con el valor 2.8?",
                "double num = 2.8",
                "val num = 2.8",
                "float num = 2.8",
                "num = 2.8 float",
                "val num = 2.8"
            ),
            QuestionModel(
                "¿Cuál sería la salida del siguiente código: println(5 > 3 && 5 < 10)?",
                "5",
                "false",
                "true",
                "2",
                "true"
            ),
            QuestionModel(
                "¿Qué operador utilizamos para comparar dos valores?",
                "==",
                "=",
                "<>",
                "><",
                "=="
            ),
            QuestionModel(
                "¿Cómo podrías crear un rango entre los números 5 y 15?",
                "for (x in 5..15)",
                "for (5 to 15)",
                "for (5..15)",
                "for (x in 5 to 15)",
                "for (x in 5..15)"
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
        val  questionModelProviderListReact = listOf<QuestionModel>(
            QuestionModel(
                "¿Cuál es el comando correcto para crear un nuevo proyecto React?",
                "npx create-react-app",
                "npm create-react-app",
                "npx create-react-app myReactApp",
                "npm create-react-app myReactApp\n",
                "npx create-react-app myReactApp"
            ),
            QuestionModel(
                "¿Qué comando se utiliza para iniciar el servidor local de desarrollo?",
                "npm build",
                "npm serve",
                "npm run dev",
                "npm start",
                "npm start"
            ),
            QuestionModel(
                "¿Qué puerto localhost por defecto utiliza React para desarrollo?",
                "3500",
                "3000",
                "5000",
                "8080",
                "3000"
            ),
            QuestionModel(
                "¿Qué palabra clave crea una constante en JavaScript?",
                "const",
                "let",
                "constant",
                "val",
                "const"
            ),
            QuestionModel(
                "¿Qué herramienta utiliza React para compilar JSX?",
                "ReactDOM",
                "React Router",
                "JSX Compiler",
                "Babel",
                "Babel"
            ),
            QuestionModel(
                "¿Cuál es un uso habitual para ref?",
                "Hacer referencia a otro archivo JS",
                "Hacer referencia a una función",
                "Acceder directamente a un nodo DOM",
                "Bindear una función",
                "Acceder directamente a un nodo DOM"
            ),
            QuestionModel(
                "¿Cuál es la sintáxis correcta para importar un Componente?",
                "import { Component } from 'react'",
                "import React.Component from 'react'",
                "import [ Component ] from 'react'",
                "import Component from 'react'",
                "import { Component } from 'react'"
            ),
            QuestionModel(
                "¿Qué permite la propiedad children?",
                "Pasar datos a componentes hijos",
                "Fijar un objeto como propiedad",
                "Anidar componentes a otros componentes",
                "Añadir valores de un hijo a estado",
                "Anidar componentes a otros componentes"
            ),
            QuestionModel(
                "¿Qué operador puede renderizar condicionalmente un componente React?",
                "||",
                "&&",
                "::",
                "??",
                "&&"
            ),
            QuestionModel(
                "¿Qué se requiere para renderizar cada valor de una lista usando map()?",
                "data",
                "id",
                "key",
                "index",
                "key"
            )
        )
    }
}