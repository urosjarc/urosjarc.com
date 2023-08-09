import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {ime, trace} from "../../../utils";
import {Audit} from "../../../services/api/openapi/models/audit";
import {DbService} from "../../../services/db/db.service";

@Component({
  selector: 'app-ucenec-delo',
  templateUrl: './ucenec-delo.component.html',
  styleUrls: ['./ucenec-delo.component.scss']
})
export class UcenecDeloComponent {
  audits = new MatTableDataSource<Audit>()

  constructor(private dbService: DbService) {
  }

  @trace()
  async ngOnInit() {
    const root_id = this.dbService.get_root_id()
    this.audits.data = await this.dbService.audit.where(ime<Audit>("entitete_id")).equals(root_id).toArray()
  }
}
