package si.programerski_klub.server.app.base

object Env {
    val PORT: Int = System.getenv("PORT").toInt()

    val MONGODB_DEV: String = System.getenv("MONGODB_DEV")
    val MONGODB_TEST: String = System.getenv("MONGODB_TEST")
    val MONGODB_PROD: String = System.getenv("MONGODB_PROD")
    val MONGODB_DB_NAME: String = System.getenv("MONGODB_DB_NAME")

    val JWT_REALM: String = System.getenv("JWT_REALM")
    val JWT_AUDIENCE: String  = System.getenv("JWT_AUDIENCE")
    val JWT_ISSUER: String  = System.getenv("JWT_ISSUER")
    val JWT_PRIVATE_KEY: String  = System.getenv("JWT_PRIVATE_KEY")

    val TWILIO_ACCOUNT_SID: String = System.getenv("TWILIO_ACCOUNT_SID")
    val TWILIO_AUTH_TOKEN: String = System.getenv("TWILIO_AUTH_TOKEN")
    val TWILIO_DEFAULT_REGION: String = System.getenv("TWILIO_DEFAULT_REGION")
}
