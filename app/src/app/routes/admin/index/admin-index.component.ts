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
  selector: 'app-admin-index',
  templateUrl: './admin-index.component.html',
  styleUrls: ['./admin-index.component.scss']
})
export class AdminIndexComponent {
}
