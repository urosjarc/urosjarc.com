import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {trace} from "../../../utils/trace";
import {TableComponent} from "../../../ui/parts/table/table.component";
import {UciteljUcenciModel} from "./ucitelj-ucenci.model";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-ucitelj-ucenci',
  templateUrl: './ucitelj-ucenci.component.html',
  styleUrls: ['./ucitelj-ucenci.component.scss'],
  imports: [
    TableComponent
  ],
  standalone: true
})
export class UciteljUcenciComponent implements OnInit {
  ucenci: MatTableDataSource<UciteljUcenciModel> = new MatTableDataSource<UciteljUcenciModel>()
  ucenci_columns: (keyof UciteljUcenciModel)[] = ['Učenec', 'Letnik', 'Začetek'];

  constructor(
    private datePipe: DatePipe,
    private uciteljRepo: UciteljRepoService) {
  }

  @trace()
  async ngOnInit() {
    const ucenje = await this.uciteljRepo.ucenje()
    const uciteljUcenci: UciteljUcenciModel[] = []
    for (const ucen of ucenje) {

      uciteljUcenci.push({
        Učenec: `${ucen.oseba.ime} ${ucen.oseba.priimek}`,
        Letnik: ucen.oseba.letnik,
        Začetek: this.datePipe.transform(ucen.ustvarjeno) || ""
      })

    }

    this.ucenci.data = uciteljUcenci
  }
}
