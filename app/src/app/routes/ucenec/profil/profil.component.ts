import {Component, OnInit} from '@angular/core';
import {db} from "../../../db";
import {Status, Test} from "../../../api";
import {ime} from "../../../utils";
import {String_vDate} from "../../../extends/String";
import {MatTableDataSource} from "@angular/material/table";


interface TestVrstica {
  naslov: string | undefined,
  opravljeno: number,
  datum: Date | undefined
}

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {

  testi: MatTableDataSource<TestVrstica> = new MatTableDataSource()
  displayedColumns: any[] = ['naslov', 'opravljeno', 'datum', 'oddaljenost'];

  async ngOnInit() {
    await this.initTestVrstice()
  }

  async initTestVrstice() {
    const root_id = await db.get_root_id()
    const testi = await db.test
      .where(ime<Test>("oseba_ucenec_id"))
      .equals(root_id)
      .toArray()

    const newTesti = []
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
        datum: String_vDate(test.deadline as string)
      })

    }

    this.testi.data = newTesti

  }

}
