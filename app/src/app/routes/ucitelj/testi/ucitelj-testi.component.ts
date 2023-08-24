import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {UciteljRepoService} from "../../../core/repos/ucitelj/ucitelj-repo.service";
import {TestModel} from "../../../../assets/models/TestModel";
import {PrikaziTesteComponent} from "../../../ui/widgets/tables/prikazi-teste/prikazi-teste.component";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-ucitelj-testi',
  templateUrl: './ucitelj-testi.component.html',
  styleUrls: ['./ucitelj-testi.component.scss'],
  imports: [
    PrikaziTesteComponent
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
