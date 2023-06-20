package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import si.urosjarc.server.app.repos.NalogeSqlRepo
import si.urosjarc.server.core.repos.NalogeRepo
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

    override val naloge: NalogeRepo = NalogeSqlRepo()
    override fun drop() = transaction {
        naloge.drop()
    }

    override fun seed() = transaction {
        naloge.seed()
    }

    override fun commit(code: () -> Unit) = transaction(statement = { code() })
}
