package services

import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply

class FtpService(
    private val server: String, private val port: Int, private val username: String, private val password: String
) {

    val client = FTPClient()

    init {
        client.controlEncoding = "UTF-8"
        client.connect(server, port);
        client.login(username, password);
        val reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            throw Exception("FTP server refused connection.")
        }

    }

    fun walk(path: String = "") = sequence {
        val cakalnica = mutableListOf(path)
        while (cakalnica.isNotEmpty()) {
            val dir = cakalnica.removeAt(0)
            for (file in client.listFiles(dir)) {
                if (file.name == "." || file.name == "..") continue
                val filePath = "$dir/${file.name}".replace(" ", "\\ ");
                if (file.isDirectory) cakalnica.add(filePath)
                else yield(filePath)
            }
        }
    }

    fun logout() {
        this.client.logout();
    }
}
