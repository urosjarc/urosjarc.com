package core.base

import data.services.ResouceService
import data.use_cases.Najdi_vse_zip_slike
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import java.io.File

object App {

    fun modul() = module {

        this.single<ResouceService> {
            ResouceService(
                rootDir = File("src/main/resources"),
                zvezkiDir = File("src/main/resources/zvezki")
            )
        }
        this.single<Json> {
            Json { prettyPrint = true }
        }

        this.factoryOf(::Najdi_vse_zip_slike)
    }

    fun pripravi_DI() {
        val app_module = modul()
        startKoin {
            this.modules(app_module)
        }
    }

    fun resetiraj_DI() {
        stopKoin()
    }
}
