package si.programerski_klub.server.core.services

import org.apache.logging.log4j.kotlin.logger

class PhoneServiceImpl : PhoneService {
    val log = this.logger()

    override fun obstaja(telefon: PhoneService.FormatiranTelefon): Boolean {
        this.log.trace("Obstaja telefon: $telefon")
        return telefon.toString().isNotEmpty()
    }

    override fun formatiraj(telefon: String): PhoneService.FormatirajRezultat {
        this.log.trace("Formatiraj telefon: $telefon")
        if (this.obstaja(telefon = PhoneService.FormatiranTelefon(value = telefon)))
            return PhoneService.FormatirajRezultat.DATA(telefon = PhoneService.FormatiranTelefon(value = "formated_$telefon"))
        return PhoneService.FormatirajRezultat.WARN_TELEFON_NI_PRAVILNE_OBLIKE
    }
}
