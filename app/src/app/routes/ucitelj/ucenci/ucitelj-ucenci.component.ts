import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {TableUcenciComponent} from "../../../ui/widgets/tables/table-ucenci/table-ucenci.component";
import {OsebaModel} from "../../../../assets/models/OsebaModel";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-ucitelj-ucenci',
  templateUrl: './ucitelj-ucenci.component.html',
  styleUrls: ['./ucitelj-ucenci.component.scss'],
  imports: [
    TableUcenciComponent
  ],
  standalone: true
})
export class UciteljUcenciComponent implements OnInit {
  ucenci: MatTableDataSource<OsebaModel> = new MatTableDataSource<OsebaModel>()

  constructor(private uciteljRepo: UciteljRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.ucenci.data = await this.uciteljRepo.ucenci()
  }
}
