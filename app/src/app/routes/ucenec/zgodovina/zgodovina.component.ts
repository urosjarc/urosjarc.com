import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {db} from "../../../db";
import {ime} from "../../../utils";
import {Audit} from "../../../api";

@Component({
  selector: 'app-zgodovina',
  templateUrl: './zgodovina.component.html',
  styleUrls: ['./zgodovina.component.scss']
})
export class ZgodovinaComponent {
  audits = new MatTableDataSource<Audit>()

  async ngOnInit() {
    const root_id = await db.get_root_id()
    this.audits.data = await db.audit.where(ime<Audit>("entitete_id")).equals(root_id).toArray()
  }
}
