import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {db} from "../../../db";
import {ime} from "../../../utils";
import {Status, Test} from "../../../api";
import {String_vDate} from "../../../extends/String";
import {TableTest} from "../../../components/table-testi/TableTest";
import {routing} from "../../../app-routing.module";

@Component({
  selector: 'app-ucenec-testi',
  templateUrl: './ucenec-testi.component.html',
  styleUrls: ['./ucenec-testi.component.scss']
})
export class UcenecTestiComponent implements OnInit {
  testi: MatTableDataSource<TableTest> = new MatTableDataSource()

  async ngOnInit() {
    await this.initTestVrstice()
  }

  async initTestVrstice() {
    const root_id = await db.get_root_id()
    const testi = await db.test
      .where(ime<Test>("oseba_ucenec_id"))
      .equals(root_id)
      .toArray()

    const newTesti: TableTest[] = []
    for (const test of testi) {
      const st_nalog = test.naloga_id?.length || -1
      const opravljeni_statusi = await db.status.where({
        [ime<Status>("test_id")]: test._id,
        [ime<Status>("oseba_id")]: root_id,
        [ime<Status>("tip")]: Status.tip.PRAVILNO,
      }).count()

      newTesti.push({
        naslov: test.naslov || "",
        opravljeno: opravljeni_statusi / st_nalog,
        datum: String_vDate(test.deadline as string),
        link: routing.ucenec({}).test({test_id: test._id || ""}).$
      })

    }

    this.testi.data = newTesti

  }

}
