package si.urosjarc.server.admin

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import si.urosjarc.server.app.base.App
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.services.DbService

class Seeder : KoinComponent {

    val db: DbService by this.inject()

    fun osebe(n: Int): Set<Oseba> {
        val osebe = mutableSetOf<Oseba>()
        for (i in 0..n) {
            val oseba = Entiteta.random<Oseba>()
            osebe.add(oseba)
        }
        return osebe
    }
}

fun main() {
    App.pripravi_DI(tip = App.Tip.TEST)
    val seeder = Seeder()

    seeder.db.drop()
    seeder.osebe(n = 10)
}
