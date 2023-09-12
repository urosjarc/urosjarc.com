package base

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import services.*
import use_cases.Pripravi_kontaktni_obrazec
import use_cases.Sinhroniziraj_bazo_knjig
import use_cases.Ustvari_templejt
import use_cases.Ustvari_testne_podatke
import use_cases_api.Sprejmi_kontaktni_obrazec

object App {
    enum class Tip { PRODUCTION, DEVELOPMENT, TEST }

    fun modul(tip: Tip = Tip.TEST) = module {
        this.single<EmailService> {
            EmailService(
                host = Env.SMTP_HOST,
                port = Env.SMTP_PORT,
                username = Env.SMTP_USERNAME,
                password = Env.SMTP_PASSWORD
            )
        }
        this.single<FtpService> {
            FtpService(
                server = Env.FTP_SERVER,
                port = Env.FTP_PORT,
                username = Env.FTP_USERNAME,
                password = Env.FTP_PASSWORD
            )
        }
        this.single<TelefonService> {
            TelefonService(
                account_sid = Env.TWILIO_ACCOUNT_SID,
                auth_token = Env.TWILIO_AUTH_TOKEN,
                default_region = Env.TWILIO_DEFAULT_REGION
            )
        }
        this.single<DbService> {
            DbService(
                db_url = Env.DB_URL,
                db_name = Env.DB_NAME
            )
        }
        this.single<JsonService> {
            JsonService()
        }

        this.factoryOf(::Pripravi_kontaktni_obrazec)
        this.factoryOf(::Ustvari_templejt)
        this.factoryOf(::Sprejmi_kontaktni_obrazec)
        this.factoryOf(::Ustvari_testne_podatke)
        this.factoryOf(::Sinhroniziraj_bazo_knjig)
    }

    fun pripravi_DI(tip: Tip = Tip.TEST) {
        val app_module = modul(tip = tip)
        startKoin {
            this.modules(app_module)
        }
    }

    fun resetiraj_DI() {
        stopKoin()
    }
}
