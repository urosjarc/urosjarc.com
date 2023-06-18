package si.programerski_klub.server.core.extend

import org.apache.logging.log4j.kotlin.KotlinLogger

fun <T> KotlinLogger.test(rezultat: T, stack: Array<StackTraceElement> = Throwable().stackTrace): T {
    val fileName = this.javaClass.simpleName + ".kt"

    val vsebina = mutableListOf<String>()
    for (s in stack) {
        if (!s.className.startsWith("si") || s.fileName == fileName || s.lineNumber < 0) continue
        vsebina.add(0, "${s.fileName}:${s.lineNumber}")
    }
    rezultat?.let { rez ->
        rez::class.qualifiedName?.run {
            vsebina.add(this.split(".").takeLast(3).joinToString("."))
        }
    }

//    this.log(level = level, vsebina.joinToString(" -> "))
    return rezultat
}
