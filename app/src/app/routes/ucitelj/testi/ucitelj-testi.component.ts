import { Component } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {TableTest} from "../../../components/table-testi/TableTest";
import {DbService} from "../../../services/db/db.service";
import {ime, trace} from "../../../utils";
import {Test} from "../../../services/api/openapi/models/test";
import {Status} from "../../../services/api/openapi/models/status";
import {String_vDate} from "../../../extends/String";
import {routing} from "../../../app-routing.module";

@Component({
  selector: 'app-ucitelj-testi',
  templateUrl: './ucitelj-testi.component.html',
  styleUrls: ['./ucitelj-testi.component.scss']
})
export class UciteljTestiComponent {
  testi: MatTableDataSource<TableTest> = new MatTableDataSource()

  constructor(private db: DbService) {
  }

  @trace()
  async ngOnInit() {
    await this.initTestVrstice()
  }

  @trace()
  async initTestVrstice() {
    const root_id = this.db.get_root_id()
    const testi = await this.db.test
      .where(ime<Test>("oseba_admin_id"))
      .equals(root_id)
      .toArray()

    const newTesti: TableTest[] = []
    for (const test of testi) {
      const st_nalog = test.naloga_id?.length || -1
      const status_tip: Status['tip'] = 'PRAVILNO'
      const opravljeni_statusi = await this.db.status.where({
        [ime<Status>("test_id")]: test._id,
        [ime<Status>("tip")]: status_tip,
      }).count()

      newTesti.push({
        naslov: test.naslov || "",
        opravljeno: opravljeni_statusi / st_nalog,
        datum: String_vDate(test.deadline as string),
        link: routing.ucitelj({}).test({test_id: test._id || ""}).$
      })

    }

    this.testi.data = newTesti

  }
}
