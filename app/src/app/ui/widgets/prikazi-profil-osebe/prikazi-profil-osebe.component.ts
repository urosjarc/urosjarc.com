import {Component} from '@angular/core';
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {Oseba} from "../../../core/services/api/models/oseba";
import {Naslov} from "../../../core/services/api/models/naslov";
import {Kontakt} from "../../../core/services/api/models/kontakt";
import {trace} from "../../../utils/trace";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-prikazi-profil-osebe',
  templateUrl: './prikazi-profil-osebe.component.html',
  styleUrls: ['./prikazi-profil-osebe.component.scss'],
  imports: [
    MatListModule,
    MatIconModule,
    NgForOf
  ],
  standalone: true
})
export class PrikaziProfilOsebeComponent {
  oseba: Oseba | undefined
  naslovi: Naslov[] = []
  kontakti: Kontakt[] = []

  ucenec_src = 'https://img.icons8.com/external-vitaliy-gorbachev-lineal-vitaly-gorbachev/60/external-student-online-learning-vitaliy-gorbachev-lineal-vitaly-gorbachev.png'

  constructor(
    private osebaRepo: OsebaRepoService) {
  }

  @trace()
  async ngOnInit() {
    const osebaModel = await this.osebaRepo.oseba()
    if (!osebaModel) return
    this.oseba = osebaModel.oseba
    this.naslovi = osebaModel.naslovi
    this.kontakti = osebaModel.kontakti
  }
}
