@file:OptIn(ExperimentalJsExport::class)

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.Element
import org.w3c.dom.asList

private fun randChar(): Char =
    "!#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[C]^_abcdefghijklmnopqrstuvwxyz{|}~".random()

private fun createCharIterator(string: String, maxIterations: Int): MutableMap<Int, Int> {
    val charIter = mutableMapOf<Int, Int>()
    string.forEachIndexed { i, char ->
        if (char != ' ') {
            if (i == 0) charIter[i] = maxIterations
            else charIter[i] = maxIterations / string.length
        }
    }
    return charIter
}

private fun scramble(
    element: Element,
    timeout: Int,
    originalWord: String,
    charIter: MutableMap<Int, Int>
) {
    /**
     * Calculate new word base on chariterator.
     */
    var newWord = ""
    originalWord.indices.forEach { i ->
        newWord += if (i in charIter) randChar() else originalWord[i]
    }

    /**
     * Lower number of iterations and remove all that are lowwer than 0
     */
    charIter.onEachIndexed { index, it ->
        if (index == 0) charIter[it.key] = it.value - 1
        if (it.value < 0) charIter.remove(it.key)
    }

    /**
     * Stop if no iterations is left
     */
    if (charIter.isEmpty()) {
        element.textContent = originalWord
        return
    }

    /**
     * Repeat!
     */
    element.textContent = newWord
    window.setTimeout(handler = {
        scramble(
            element = element,
            timeout = timeout,
            originalWord = originalWord,
            charIter = charIter
        )
    }, timeout = timeout)
}


@JsExport
fun hacker() {
    val elements = document.getElementsByClassName("hacker")
    val maxWordSize = elements.asList().maxOf { it.textContent?.length ?: 0 }

    elements.asList().forEach {
        val originalWord = it.textContent.toString()
        scramble(
            element = it,
            timeout = 5,
            originalWord = originalWord,
            charIter = createCharIterator(
                string = originalWord,
                maxIterations = maxWordSize
            )
        )
    }
}
