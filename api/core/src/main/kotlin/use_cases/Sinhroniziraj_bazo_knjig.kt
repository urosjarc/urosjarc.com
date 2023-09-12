package use_cases

import services.DbService
import services.FtpService


class Sinhroniziraj_bazo_knjig(
    private val db: DbService,
    private val ftp: FtpService
) {

    fun zdaj(vse: Boolean = false) {
        for(file in this.ftp.walk()){
            println(file)
        }
    }
}
