package si.urosjarc.server.app.base

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import si.urosjarc.server.app.services.EmailSmtp
import si.urosjarc.server.app.services.PhoneTwilio
import si.urosjarc.server.core.services.EmailService
import si.urosjarc.server.core.services.PhoneService
import kotlin.math.E

object App {
    enum class Tip { PRODUCTION, DEVELOPMENT, TEST }

    fun modul(tip: Tip = Tip.TEST) = module {
        this.single<EmailService> {
            EmailSmtp(
                host=Env.SMTP_HOST,
                port=Env.SMTP_PORT,
                username = Env.SMTP_USERNAME,
                password = Env.SMTP_PASSWORD
            )
        }
        this.single<PhoneService> {
            PhoneTwilio(
                account_sid = Env.TWILIO_ACCOUNT_SID,
                auth_token = Env.TWILIO_AUTH_TOKEN,
                default_region = Env.TWILIO_DEFAULT_REGION,
                phone = Env.TWILIO_PHONE
            )
        }
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
