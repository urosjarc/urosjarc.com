import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {trace} from "../../../utils/trace";
import {TableComponent} from "../../../ui/parts/table/table.component";
import {DatePipe} from "@angular/common";
import {UciteljUcenciModel, uciteljUcenciModelMap} from "./ucitelj-ucenci.model";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";

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
    private osebaRepo: OsebaRepoService
  ) {
  }

  @trace()
  async ngOnInit() {
    this.ucenci.data = (await this.osebaRepo.ucenje()).map(uciteljUcenciModelMap)
  }
}
