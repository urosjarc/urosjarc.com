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
            val ucitelj = Entiteta.random<Oseba>().copy(tip = Oseba.Tip.INSTRUKTOR)
            osebaRepo.post(ucitelj)

            val kontakt_ucitelja = Entiteta.random<Kontakt>().copy(oseba_id = ucitelj.id)
            kontaktRepo.post(kontakt_ucitelja)

            val naslov = Entiteta.random<Naslov>().copy(oseba_id = ucitelj.id)
            naslovRepo.post(naslov)

            for (j in 0..5) {

                val ucenec = Entiteta.random<Oseba>().copy(tip = Oseba.Tip.UCENEC)
                osebaRepo.post(ucenec)

                val kontakt_ucenca = Entiteta.random<Kontakt>().copy(oseba_id = ucenec.id)
                kontaktRepo.post(kontakt_ucenca)

                val ucenje = Entiteta.random<Ucenje>().copy(ucitelj_id = ucitelj.id, ucenec_id = ucenec.id)
                ucenjeRepo.post(ucenje)

                for (k in 0..5) {

                    val sporocilo_ucenca = Entiteta.random<Sporocilo>().copy(posiljatelj_id = kontakt_ucenca.id, prejemnik_id = kontakt_ucitelja.id)
                    sporociloRepo.post(sporocilo_ucenca)

                    val sporocilo_ucitelja = Entiteta.random<Sporocilo>().copy(posiljatelj_id = kontakt_ucitelja.id, prejemnik_id = kontakt_ucenca.id)
                    sporociloRepo.post(sporocilo_ucitelja)

                }
            }

            /**
             * TEST & ZVEZEK
             */
            for (x in 0..5) {
                val zvezek = Entiteta.random<Zvezek>()
                zvezekRepo.post(zvezek)

                for (j in 0..5) {

                    val tematika = Entiteta.random<Tematika>().copy(zvezek_id = zvezek.id)
                    tematikaRepo.post(tematika)

                    val test = Entiteta.random<Test>().copy(oseba_id = ucitelj.id)
                    testRepo.post(test)

                    for (l in 0..5) {

                        val naloga = Entiteta.random<Naloga>().copy(tematika_id = tematika.id)
                        nalogaRepo.post(naloga)

                        val status = Entiteta.random<Status>().copy(naloga_id = naloga.id, test_id = test.id)
                        statusRepo.post(status)
                    }
                }
            }
        }
    }

    override fun <T> exe(code: () -> T): T = transaction(statement = { code() })
}
