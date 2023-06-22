package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import si.urosjarc.server.app.repos.*
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.repos.*
import si.urosjarc.server.core.services.DbService

class DbExposed(
    val url: String,
    val driver: String,
    val user: String,
    val password: String
) : DbService {

    val log = this.logger()
    val db = Database.connect(
        url = this.url,
        driver = this.driver,
        user = this.user,
        password = this.password
    )

    override val zvezekRepo: ZvezekRepo = ZvezekSqlRepo
    override val tematikaRepo: TematikaRepo = TematikaSqlRepo
    override val nalogaRepo: NalogaRepo = NalogaSqlRepo
    override val statusRepo: StatusRepo = StatusSqlRepo
    override val testRepo: TestRepo = TestSqlRepo
    override val osebaRepo: OsebaRepo = OsebaSqlRepo
    override val naslovRepo: NaslovRepo = NaslovSqlRepo
    override val kontaktRepo: KontaktRepo = KontaktSqlRepo
    override val auditRepo: AuditRepo = AuditSqlRepo
    override val ucenjeRepo: UcenjeRepo = UcenjeSqlRepo
    override val sporociloRepo: SporociloRepo = SporociloSqlRepo


    override fun drop() = transaction {
        zvezekRepo.drop()
        tematikaRepo.drop()
        nalogaRepo.drop()
        statusRepo.drop()
        testRepo.drop()
        osebaRepo.drop()
        naslovRepo.drop()
        kontaktRepo.drop()
        auditRepo.drop()
        ucenjeRepo.drop()
        sporociloRepo.drop()
    }

    override fun seed() = transaction {
        zvezekRepo.seed()
        tematikaRepo.seed()
        nalogaRepo.seed()
        statusRepo.seed()
        testRepo.seed()
        osebaRepo.seed()
        naslovRepo.seed()
        kontaktRepo.seed()
        auditRepo.seed()
        ucenjeRepo.seed()
        sporociloRepo.seed()

        for (i in 0..10) {

            /**
             * OSEBA
             */
            val oseba = Entiteta.random<Oseba>()
            osebaRepo.post(oseba)

            val naslov = Entiteta.random<Naslov>().copy(id_oseba = oseba.id)
            naslovRepo.post(naslov)

            for (j in 0..5) {

                val kontakt = Entiteta.random<Kontakt>().copy(id_oseba = oseba.id)
                kontaktRepo.post(kontakt)

                for (k in 0..5) {

                    val sporocilo = Entiteta.random<Sporocilo>().copy(id_posiljatelj = kontakt.id, id_prejemnik = kontakt.id)
                    sporociloRepo.post(sporocilo)

                }
            }

            /**
             * TEST & ZVEZEK
             */
            for (x in 0..5) {
                val zvezek = Entiteta.random<Zvezek>()
                zvezekRepo.post(zvezek)

                for (j in 0..5) {

                    val tematika = Entiteta.random<Tematika>().copy(id_zvezek = zvezek.id)
                    tematikaRepo.post(tematika)

                    val test = Entiteta.random<Test>().copy(id_oseba = oseba.id)
                    testRepo.post(test)

                    for (l in 0..5) {

                        val naloga = Entiteta.random<Naloga>().copy(id_tematika = tematika.id)
                        nalogaRepo.post(naloga)

                        val status = Entiteta.random<Status>().copy(id_naloga = naloga.id, id_test = test.id)
                        statusRepo.post(status)
                    }
                }
            }
        }
    }

    override fun commit(code: () -> Unit) = transaction(statement = { code() })
}
