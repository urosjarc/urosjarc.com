package data.services

import data.domain.Log

class LogService {
    private val opazovalci = mutableListOf<(value: Log) -> Unit>()
    private val logs = mutableListOf<Log>()
    fun opazuj(cb: ((value: Log) -> Unit)) {
        this.opazovalci.add(cb)
    }

    private fun sporoci(log: Log) {
        this.opazovalci.forEach { it(log) }
    }

    fun addLog(log: Log) {
        this.logs.add(log)
        this.sporoci(log)
    }

    fun debug(msg: String) {
        addLog(Log(data = msg, tip = Log.Tip.DEBUG))
    }

    fun info(msg: String) {
        addLog(Log(data = msg, tip = Log.Tip.INFO))
    }

    fun warn(msg: String) {
        addLog(Log(data = msg, tip = Log.Tip.WARN))
    }

    fun error(msg: String): Error {
        addLog(Log(data = msg, tip = Log.Tip.ERROR))
        return Error(msg)
    }
}
