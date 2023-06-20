package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import si.urosjarc.server.app.repos.NalogeSqlRepo
import si.urosjarc.server.core.repos.DbDeleteRezultat
import si.urosjarc.server.core.repos.NalogeRepo
import si.urosjarc.server.core.services.DbService


class DbExposed(
    val url: String,
    val driver: String,
    val user: String,
    val password: String
) : DbService {

    override val naloge: NalogeRepo = NalogeSqlRepo()

    val log = this.logger()

    init {
        Database.connect(url = this.url, driver = this.driver, user = this.user, password = this.password)
    }


    override fun izbrisi_vse(): DbDeleteRezultat {
        SchemaUtils.drop()
    }


}
