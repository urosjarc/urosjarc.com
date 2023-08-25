import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {trace} from "../../../utils/trace";
import {SporociloModel} from "../../../core/domain/SporociloModel";

@Component({
  selector: 'app-ucitelj-sporocila',
  templateUrl: './ucitelj-sporocila.component.html',
  styleUrls: ['./ucitelj-sporocila.component.scss'],
  imports: [],
  standalone: true
})
export class UciteljSporocilaComponent {
  sporocila: MatTableDataSource<SporociloModel> = new MatTableDataSource<SporociloModel>()

  constructor(private osebaRepo: OsebaRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.sporocila.data = await this.osebaRepo.sporocila()
  }
}
