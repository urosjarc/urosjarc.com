package data.app

import base.Env
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply

fun ftp_indexing() {
    val ftp = FTPClient()
    ftp.controlEncoding = "UTF-8"
    ftp.connect(Env.FTP_SERVER, Env.FTP_PORT);
    ftp.login(Env.FTP_USERNAME, Env.FTP_PASSWORD);

    System.out.print(ftp.getReplyString());
    val reply = ftp.getReplyCode();

    if (!FTPReply.isPositiveCompletion(reply)) {
        ftp.disconnect();
        System.err.println("FTP server refused connection.");
        System.exit(1);
    }

    for (file in ftp_walk(ftp)) {
        println(file)
    }
    ftp.logout();
}

fun ftp_walk(ftpClient: FTPClient, path: String = "") = sequence {
    val cakalnica = mutableListOf(path)
    while (cakalnica.isNotEmpty()) {
        val dir = cakalnica.removeAt(0)
        println(dir)
        for (file in ftpClient.listFiles(dir)) {
            if (file.name == "." || file.name == "..") continue

            val filePath = "$dir/${file.name}".replace(" ", "\\ ");
            if (file.isDirectory) cakalnica.add(filePath)
            else yield(filePath)
        }
    }
}
