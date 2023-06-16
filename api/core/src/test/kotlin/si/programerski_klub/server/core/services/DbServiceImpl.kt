package si.programerski_klub.server.core.services

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.obvescanje.KontaktniObrazec
import si.programerski_klub.server.core.domain.placevanje.Narocilo
import si.programerski_klub.server.core.domain.placevanje.Narocnina
import si.programerski_klub.server.core.domain.placevanje.Ponudba
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.domain.uprava.Oseba
import si.programerski_klub.server.core.services.DbService.RezultatIdentifikacije
import si.programerski_klub.server.core.services.DbService.RezultatShranitve

class DbServiceImpl(
    val narocnine: MutableList<Narocnina> = mutableListOf(),
    val narocila: MutableList<Narocilo> = mutableListOf(),
    val kontaktni_obrazci: MutableList<KontaktniObrazec> = mutableListOf(),
    val produkti: MutableList<Produkt> = mutableListOf(),

    val shrani_narocilo_return: MutableList<RezultatShranitve<Narocilo>> = mutableListOf(),
    val shrani_narocnino_return: MutableList<RezultatShranitve<Narocnina>> = mutableListOf(),
    val shrani_kontaktni_obrazec_return: MutableList<RezultatShranitve<KontaktniObrazec>> = mutableListOf(),
) : DbService {

    override fun izbrisi_vse(): DbService.RezultatDbIzbrisa {
        TODO("Not yet implemented")
    }

    override fun narocilo(id: Id<Narocilo>): RezultatIdentifikacije<Narocilo> {
        TODO("Not yet implemented")
    }

    override fun shrani_narocilo(narocilo: Narocilo): RezultatShranitve<Narocilo> {
        this.narocila.add(narocilo)
        if (this.shrani_narocilo_return.size == 0)
            return RezultatShranitve.DATA(data = narocilo)
        return this.shrani_narocilo_return.removeAt(0)
    }

    override fun narocnina(id: Id<Narocnina>): RezultatIdentifikacije<Narocnina> {
        TODO("Not yet implemented")
    }

    override fun shrani_narocnino(narocnina: Narocnina): RezultatShranitve<Narocnina> {
        this.narocnine.add(narocnina)
        if (this.shrani_narocnino_return.size == 0)
            return RezultatShranitve.DATA(data = narocnina)
        return this.shrani_narocnino_return.removeAt(0)
    }

    override fun kontaktni_obrazec(id: Id<KontaktniObrazec>): RezultatIdentifikacije<KontaktniObrazec> {
        TODO("Not yet implemented")
    }

    override fun shrani_kontaktni_obrazec(kontaktni_obrazec: KontaktniObrazec): RezultatShranitve<KontaktniObrazec> {
        this.kontaktni_obrazci.add(kontaktni_obrazec)
        if (this.shrani_kontaktni_obrazec_return.size == 0)
            return RezultatShranitve.DATA(data = kontaktni_obrazec)
        return this.shrani_kontaktni_obrazec_return.removeAt(0)
    }

    override fun produkt(id: Id<Produkt>): RezultatIdentifikacije<Produkt> {
        for (produkt in this.produkti) {
            if (produkt.id == id) {
                return RezultatIdentifikacije.DATA(data = produkt)
            }
        }
        return RezultatIdentifikacije.ERROR()
    }

    override fun produkti(id: Id<Ponudba>): RezultatIdentifikacije<List<Produkt>> {
        TODO("Not yet implemented")
    }

    override fun shrani_produkt(produkt: Produkt): RezultatShranitve<Produkt> {
        TODO("Not yet implemented")
    }

    override fun oseba(id: Id<Oseba>): RezultatIdentifikacije<Oseba> {
        TODO("Not yet implemented")
    }

    override fun ponudbe(tip: Ponudba.Tip): List<Ponudba> {
        TODO("Not yet implemented")
    }

    override fun dodaj_ponudbo(ponudba: Ponudba): RezultatShranitve<Ponudba> {
        TODO("Not yet implemented")
    }

    override fun poisci_narocnino(id: Id<Oseba>, tip: Produkt.Tip): DbService.RezultatIskanja<Narocnina> {
        for (narocnina in this.narocnine) {
            if (narocnina.id_uporabnika == id && narocnina.produkt.tip == tip)
                return DbService.RezultatIskanja.DATA(data = narocnina)
        }
        return DbService.RezultatIskanja.PASS()
    }

}
