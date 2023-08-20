import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {TestModel} from "../../../../assets/models/TestModel";
import {TableTestiComponent} from "../../../ui/widgets/tables/table-testi/table-testi.component";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-ucitelj-testi',
  templateUrl: './ucitelj-testi.component.html',
  styleUrls: ['./ucitelj-testi.component.scss'],
  imports: [
    TableTestiComponent
  ],
  standalone: true
})
export class UciteljTestiComponent implements OnInit {
  testi: MatTableDataSource<TestModel> = new MatTableDataSource()
  displayedColumns: any[] = ['naslov', 'datum', 'oddaljenost'];

  constructor(private uciteljRepo: UciteljRepoService) {
  }

  @trace()
  async ngOnInit() {
    this.testi.data = await this.uciteljRepo.testi()
  }
}
