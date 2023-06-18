package si.programerski_klub.server.app.base

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.app.services.EmailSmtp
import si.programerski_klub.server.app.services.PhoneTwilio
import si.programerski_klub.server.core.services.DbService
import si.programerski_klub.server.core.services.EmailService
import si.programerski_klub.server.core.services.PhoneService
import si.programerski_klub.server.core.use_cases.*
import si.programerski_klub.server.core.use_cases_api.Sprejmi_kontakti_obrazec
import si.programerski_klub.server.core.use_cases_api.Sprejmi_narocilo

object App {
    enum class Tip { PRODUCTION, DEVELOPMENT, TEST }

    fun modul(tip: Tip = Tip.TEST) = module {
        this.single<DbService> {
            DbMongo(
                connection = when (tip) {
                    Tip.PRODUCTION -> Env.MONGODB_PROD
                    Tip.DEVELOPMENT -> Env.MONGODB_DEV
                    Tip.TEST -> Env.MONGODB_TEST
                },
                database = Env.MONGODB_DB_NAME,
                allow_db_drop = true
            )
        }
        this.single<EmailService> { EmailSmtp() }
        this.single<PhoneService> {
            PhoneTwilio(
                account_sid = Env.TWILIO_ACCOUNT_SID,
                auth_token = Env.TWILIO_AUTH_TOKEN,
                default_region = Env.TWILIO_DEFAULT_REGION
            )
        }

        this.factoryOf(::Formatiraj_kontakt)
        this.factoryOf(::Preveri_zalogo)
        this.factoryOf(::Sprejmi_kontakt)
        this.factoryOf(::Sprejmi_narocnino)
        this.factoryOf(::Sprejmi_osebo)
        this.factoryOf(::Validiraj_kontakt)

        this.factoryOf(::Sprejmi_kontakti_obrazec)
        this.factoryOf(::Sprejmi_narocilo)
    }

    fun pripravi_DI(tip: Tip = Tip.TEST) {
        val app_module = this.modul(tip = tip)
        startKoin {
            this.modules(app_module)
        }
    }

    fun resetiraj_DI() {
        stopKoin()
    }
}
