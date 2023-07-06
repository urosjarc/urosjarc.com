package si.urosjarc.server.app.base

object Env {
    val PORT: Int = System.getenv("PORT").toInt()

    val DB_URL: String = System.getenv("DB_URL")
    val DB_NAME: String = System.getenv("DB_NAME")

    val TWILIO_ACCOUNT_SID: String = System.getenv("TWILIO_ACCOUNT_SID")
    val TWILIO_AUTH_TOKEN: String = System.getenv("TWILIO_AUTH_TOKEN")
    val TWILIO_DEFAULT_REGION: String = System.getenv("TWILIO_DEFAULT_REGION")
    val TWILIO_PHONE: String = System.getenv("TWILIO_PHONE")

    val SMTP_PORT: Int = System.getenv("SMTP_PORT").toInt()
    val SMTP_PASSWORD: String = System.getenv("SMTP_PASSWORD")
    val SMTP_USERNAME: String = System.getenv("SMTP_USERNAME")
    val SMTP_HOST: String = System.getenv("SMTP_HOST")

    val JWT_REALM: String = System.getenv("JWT_REALM")
    val JWT_AUDIENCE: String  = System.getenv("JWT_AUDIENCE")
    val JWT_ISSUER: String  = System.getenv("JWT_ISSUER")
    val JWT_PRIVATE_KEY: String  = System.getenv("JWT_PRIVATE_KEY")
}
