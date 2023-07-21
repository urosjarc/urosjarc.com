package base

import services.DbService
import services.EmailService
import services.TelefonService
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

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
