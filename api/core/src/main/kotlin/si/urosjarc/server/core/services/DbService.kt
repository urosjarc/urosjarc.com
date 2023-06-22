package si.urosjarc.server.core.services

import si.urosjarc.server.core.repos.*

interface DbService {
    val zvezekRepo: ZvezekRepo
    val tematikaRepo: TematikaRepo
    val nalogaRepo: NalogaRepo
    val statusRepo: StatusRepo
    val testRepo: TestRepo
    val osebaRepo: OsebaRepo
    val naslovRepo: NaslovRepo
    val kontaktRepo: KontaktRepo
    val auditRepo: AuditRepo
    val ucenjeRepo: UcenjeRepo
    val sporociloRepo: SporociloRepo

    fun seed()
    fun drop()
    fun commit(code: () -> Unit)

}
