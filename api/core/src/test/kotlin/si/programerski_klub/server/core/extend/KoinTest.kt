package si.programerski_klub.server.core.extend

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import si.programerski_klub.server.core.domain.placevanje.Narocilo
import si.programerski_klub.server.core.domain.placevanje.Narocnina
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.services.*
import si.programerski_klub.server.core.use_cases.*
import si.programerski_klub.server.core.use_cases_api.Sprejmi_kontakti_obrazec
import si.programerski_klub.server.core.use_cases_api.Sprejmi_narocilo

fun KoinTest.pripravi_DI(
    produkti: MutableList<Produkt> = mutableListOf(),
    shrani_narocilo_return: MutableList<DbService.RezultatShranitve<Narocilo>> = mutableListOf(),
    shrani_narocnino_return: MutableList<DbService.RezultatShranitve<Narocnina>> = mutableListOf(),
    narocnine: MutableList<Narocnina> = mutableListOf()
) {
    val module = module {
        this.single<DbService> {
            DbServiceImpl(
                produkti = produkti,
                shrani_narocilo_return = shrani_narocilo_return,
                shrani_narocnino_return = shrani_narocnino_return,
                narocnine = narocnine
            )
        }
        this.single<EmailService> { EmailServiceImpl() }
        this.single<PhoneService> { PhoneServiceImpl() }

        this.factoryOf(::Validiraj_kontakt)
        this.factoryOf(::Formatiraj_kontakt)
        this.factoryOf(::Sprejmi_kontakt)
        this.factoryOf(::Sprejmi_narocilo)
        this.factoryOf(::Sprejmi_narocnino)
        this.factoryOf(::Preveri_zalogo)
        this.factoryOf(::Sprejmi_osebo)
        this.factoryOf(::Sprejmi_kontakti_obrazec)

//        this.verify()
    }

    startKoin {
        this.modules(module)
    }
}

fun KoinTest.resetiraj_DI() {
    stopKoin()
}
