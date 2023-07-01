package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import si.urosjarc.server.app.repos.*
import si.urosjarc.server.core.base.DbUstvariRezultat
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


    override fun sprazni() = transaction {
        zvezekRepo.sprazni()
        tematikaRepo.sprazni()
        nalogaRepo.sprazni()
        statusRepo.sprazni()
        testRepo.sprazni()
        osebaRepo.sprazni()
        naslovRepo.sprazni()
        kontaktRepo.sprazni()
        auditRepo.sprazni()
        ucenjeRepo.sprazni()
        sporociloRepo.sprazni()
    }

    override fun nafilaj() = transaction {
        zvezekRepo.nafilaj()
        tematikaRepo.nafilaj()
        nalogaRepo.nafilaj()
        statusRepo.nafilaj()
        testRepo.nafilaj()
        osebaRepo.nafilaj()
        naslovRepo.nafilaj()
        kontaktRepo.nafilaj()
        auditRepo.nafilaj()
        ucenjeRepo.nafilaj()
        sporociloRepo.nafilaj()

        for (i in 0..10) {

            /**
             * OSEBA
             */
            var ucitelj = Entiteta.nakljucni<Oseba>().copy(tip = Oseba.Tip.INSTRUKTOR)
            when(val r = osebaRepo.ustvari(ucitelj)){
                is DbUstvariRezultat.DATA -> ucitelj = r.data
                is DbUstvariRezultat.FATAL_DB_NAPAKA -> TODO()
            }

            val kontakt_ucitelja = Entiteta.nakljucni<Kontakt>().copy(oseba_id = ucitelj.id)
            kontaktRepo.ustvari(kontakt_ucitelja)

            val naslov = Entiteta.nakljucni<Naslov>().copy(oseba_id = ucitelj.id)
            naslovRepo.ustvari(naslov)

            for (j in 0..5) {

                val ucenec = Entiteta.nakljucni<Oseba>().copy(tip = Oseba.Tip.UCENEC)
                osebaRepo.ustvari(ucenec)

                val kontakt_ucenca = Entiteta.nakljucni<Kontakt>().copy(oseba_id = ucenec.id)
                kontaktRepo.ustvari(kontakt_ucenca)

                val ucenje = Entiteta.nakljucni<Ucenje>().copy(ucitelj_id = ucitelj.id, ucenec_id = ucenec.id)
                ucenjeRepo.ustvari(ucenje)

                for (k in 0..5) {

                    val sporocilo_ucenca = Entiteta.nakljucni<Sporocilo>().copy(posiljatelj_id = kontakt_ucenca.id, prejemnik_id = kontakt_ucitelja.id)
                    sporociloRepo.ustvari(sporocilo_ucenca)

                    val sporocilo_ucitelja = Entiteta.nakljucni<Sporocilo>().copy(posiljatelj_id = kontakt_ucitelja.id, prejemnik_id = kontakt_ucenca.id)
                    sporociloRepo.ustvari(sporocilo_ucitelja)

                }
            }

            /**
             * TEST & ZVEZEK
             */
            for (x in 0..5) {
                val zvezek = Entiteta.nakljucni<Zvezek>()
                zvezekRepo.ustvari(zvezek)

                val test = Entiteta.nakljucni<Test>().copy(oseba_id = ucitelj.id)
                testRepo.ustvari(test)

                for (j in 0..5) {

                    val tematika = Entiteta.nakljucni<Tematika>().copy(zvezek_id = zvezek.id)
                    tematikaRepo.ustvari(tematika)

                    val naloga = Entiteta.nakljucni<Naloga>().copy(tematika_id = tematika.id)
                    nalogaRepo.ustvari(naloga)

                    val status = Entiteta.nakljucni<Status>().copy(naloga_id = naloga.id, test_id = test.id)
                    statusRepo.ustvari(status)
                }
            }
        }
    }

    override fun <T> izvedi(code: () -> T): T = transaction(statement = { code() })
}
