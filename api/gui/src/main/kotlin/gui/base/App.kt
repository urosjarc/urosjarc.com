package gui.base

import core.services.JsonService
import gui.services.LogService
import gui.services.OcrService
import gui.services.ResouceService
import gui.use_cases.*
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
        this.single<OcrService> {
            OcrService()
        }
        this.single<LogService> {
            LogService()
        }
        this.single<JsonService> {
            JsonService(prettyPrint = true)
        }

        this.factoryOf(::Najdi_vse_slike)
        this.factoryOf(::Anotiraj_omego_stran)
        this.factoryOf(::Razrezi_stran)
        this.factoryOf(::Razrezi_odsek)
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
