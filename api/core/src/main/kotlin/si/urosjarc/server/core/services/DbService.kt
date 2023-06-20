package si.urosjarc.server.core.services

import si.urosjarc.server.core.repos.*

interface DbService {
    val naloge: NalogeRepo
    fun seed()
    fun drop()
    fun commit(code: () -> Unit)

}
