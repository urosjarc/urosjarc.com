package si.urosjarc.server.core.services

import si.urosjarc.server.core.repos.*

interface DbService {
    val zvezek: ZvezekRepo
    val tematika: TematikaRepo
    val naloga: NalogaRepo
    val status: StatusRepo
    val test: TestRepo
    val oseba: OsebaRepo
    val naslov: NaslovRepo
    val zaznamek: ZaznamekRepo
    val kontakt: KontaktRepo
    val sporocilo: SporociloRepo
    val audit: AuditRepo

    fun seed()
    fun drop()
    fun commit(code: () -> Unit)

}
