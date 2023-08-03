import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {db} from "../../../db";
import {ime} from "../../../utils";
import {Audit} from "../../../api/models/audit";

@Component({
  selector: 'app-ucenec-delo',
  templateUrl: './ucenec-delo.component.html',
  styleUrls: ['./ucenec-delo.component.scss']
})
export class UcenecDeloComponent {
  audits = new MatTableDataSource<Audit>()

  async ngOnInit() {
    const root_id = db.get_root_id()
    this.audits.data = await db.audit.where(ime<Audit>("entitete_id")).equals(root_id).toArray()
  }
}
