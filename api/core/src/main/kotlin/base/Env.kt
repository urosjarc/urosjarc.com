package base

object Env {
    val PORT: Int = System.getenv("PORT").toInt()

    val VISION_API_KEY: String = System.getenv("VISION_API_KEY")

    val ENCRYPTION_SALT: String = System.getenv("ENCRYPTION_SALT")
    val ENCRYPTION_PASSWORD: String = System.getenv("ENCRYPTION_PASSWORD")

    val DB_URL: String = System.getenv("DB_URL")
    val DB_NAME: String = System.getenv("DB_NAME")

    val JWT_REALM: String = System.getenv("JWT_REALM")
    val JWT_AUDIENCE: String = System.getenv("JWT_AUDIENCE")
    val JWT_ISSUER: String = System.getenv("JWT_ISSUER")
    val JWT_PRIVATE_KEY: String = System.getenv("JWT_PRIVATE_KEY")
    val JWT_KEYID: String = System.getenv("JWT_KEYID")

    val TWILIO_ACCOUNT_SID: String = System.getenv("TWILIO_ACCOUNT_SID")
    val TWILIO_AUTH_TOKEN: String = System.getenv("TWILIO_AUTH_TOKEN")
    val TWILIO_DEFAULT_REGION: String = System.getenv("TWILIO_DEFAULT_REGION")

    val SMTP_PORT: Int = System.getenv("SMTP_PORT").toInt()
    val SMTP_PASSWORD: String = System.getenv("SMTP_PASSWORD")
    val SMTP_USERNAME: String = System.getenv("SMTP_USERNAME")
    val SMTP_HOST: String = System.getenv("SMTP_HOST")

}
