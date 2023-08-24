import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {IzberiUcenceComponent} from "../../../ui/widgets/tables/izberi-ucence/izberi-ucence.component";
import {OsebaModel} from "../../../../assets/models/OsebaModel";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-ucitelj-ucenci',
  templateUrl: './ucitelj-ucenci.component.html',
  styleUrls: ['./ucitelj-ucenci.component.scss'],
  imports: [
    IzberiUcenceComponent
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
