package com.example.quizz

class HelperQuizz() {

    var quizzMap: MutableMap<String, MutableList<Pair<String, Boolean>>>

    init {
        quizzMap = hashMapOf()
        quizzMap["What is you name ?"] = mutableListOf("Julien" to true, "Salut" to false, "c'est pas un prénom ça" to false)
        quizzMap["What is you quest ?"] = mutableListOf("To seek the Holy Grail" to false, "Au revoir" to false, "Avoir une bonne note" to true)
        quizzMap["What is you favorite color ?"] = mutableListOf("Rouge" to false, "Bleu" to false, "Blouge" to true)
        quizzMap["Est ce que cette application mérite 20/20 coef 9999 ?"] = mutableListOf("Oui" to true, "Yes" to true, "Ja" to true)
    }

    fun iterateThat(newlist:MutableMap<String, MutableList<Pair<String, Boolean>>>? = null) = sequence {
        newlist?.forEach {
            quizzMap[it.key] = it.value
        }
        for (item in quizzMap) {
            yield(item)
        }
    }
}