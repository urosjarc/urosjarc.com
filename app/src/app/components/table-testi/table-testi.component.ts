import {Component, Input} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {TableTest} from "./TableTest";

@Component({
  selector: 'app-table-testi',
  templateUrl: './table-testi.component.html',
  styleUrls: ['./table-testi.component.scss']
})
export class TableTestiComponent {
  @Input() testi: MatTableDataSource<TableTest> = new MatTableDataSource()
  @Input() displayedColumns: any[] = ['naslov', 'opravljeno', 'datum', 'oddaljenost'];
}
