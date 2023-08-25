import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {trace} from "../../../utils/trace";
import {OsebaModel} from "../../../core/domain/OsebaModel";
import {UcenjeModel} from "../../../core/domain/UcenjeModel";

@Component({
  selector: 'app-ucitelj-ucenci',
  templateUrl: './ucitelj-ucenci.component.html',
  styleUrls: ['./ucitelj-ucenci.component.scss'],
  imports: [],
  standalone: true
})
export class UciteljUcenciComponent implements OnInit {
  ucenci: MatTableDataSource<UcenjeModel> = new MatTableDataSource<UcenjeModel>()

  constructor(private uciteljRepo: UciteljRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.ucenci.data = await this.uciteljRepo.ucenci()
  }
}
