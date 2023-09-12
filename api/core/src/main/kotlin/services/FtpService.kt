package services

import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import kotlin.system.exitProcess


class FtpService(
    private val server: String, private val port: Int, private val username: String, private val password: String
) {

    val client = FTPClient()

    init {

        client.controlEncoding = "UTF-8"
        client.connect(server, port)
        client.login(username, password)

        client.enterLocalPassiveMode()

        val reply = client.replyCode
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            throw Exception("FTP server refused connection.")
        }
    }

    fun walk(path: String = "") = sequence<String> {
        val cakalnica = mutableListOf(path)
        while (cakalnica.isNotEmpty()) {
            val dir = cakalnica.removeAt(0)
            val files = client.listFiles(dir)
            for (file in files) {
                if (file.name == "." || file.name == ".." || file.name.contains("resitve")) continue
                val filePath = "$dir/${file.name}".replace(" ", "\\ ");
                cakalnica.add(filePath)
            }
            if (files.isEmpty()) yield(dir)
            if (cakalnica.size % 100 == 0) println("Walk status: ${cakalnica.size}")
        }
        exitProcess(0)
    }

    fun logout() {
        this.client.logout();
    }
}
