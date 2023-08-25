import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.Base64
import java.util.UUID
import java.util.Date


interface JwksExtension {
    val outputDir: Property<String>
}

class Jwks : Plugin<Project> {

    val className = Jwks::class.simpleName.toString()

    override fun apply(project: Project) {
        val extension = project.extensions.create<JwksExtension>(className)

        project.task(className) {
            doLast {
                val outputDir = File(this.project.projectDir, extension.outputDir.get())
                logger.warn("\nOutput dir: $outputDir\n")

                val keyPair = KeyPairGenerator.getInstance("RSA").let {
                    it.initialize(2048)
                    it.generateKeyPair()
                }
                val privateKey = String(Base64.getEncoder().encode(keyPair.private.encoded))
                val keyId = UUID.randomUUID().toString()

                logger.warn("JWT keyId: ${keyId}")
                logger.warn("RSA private: ${privateKey}")

                val jwk: JWK = RSAKey.Builder(keyPair.public as RSAPublicKey)
                    .privateKey(keyPair.private as RSAPrivateKey)
                    .keyUse(KeyUse.SIGNATURE)
                    .keyID(keyId)
                    .issueTime(Date())
                    .build()

                outputDir.parentFile.mkdirs()
                outputDir.writeText("""{
                    "keys": [
                        ${jwk.toPublicJWK().toJSONString()}
                    ]
                }""".trimIndent())
            }
        }
    }
}

apply<Jwks>()
