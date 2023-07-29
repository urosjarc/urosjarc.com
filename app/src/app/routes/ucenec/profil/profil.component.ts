import {Component, OnInit} from '@angular/core';
import {db} from "../../../db";
import {Test} from "../../../api";
import {ime} from "../../../utils";

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {
  async ngOnInit(): Promise<void> {
    const root_id = await db.get_root_id()
    const testi = await db.test
      .where(ime<Test>("oseba_ucenec_id"))
      .equals(root_id)
      .toArray()

    console.log(testi)
  }

}
