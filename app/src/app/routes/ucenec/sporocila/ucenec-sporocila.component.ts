import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ime, trace} from "../../../utils";
import {String_vDate} from "../../../extends/String";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {SporociloInfo} from "../../../components/dialog-sporocilo/SporociloInfo";
import {MatDialog} from "@angular/material/dialog";
import {DialogSporociloComponent} from "../../../components/dialog-sporocilo/dialog-sporocilo.component";
import {DbService} from "../../../services/db/db.service";
import {Oseba} from "../../../services/api/openapi/models/oseba";
import {Kontakt} from "../../../services/api/openapi/models/kontakt";
import {Sporocilo} from "../../../services/api/openapi/models/sporocilo";

@Component({
  selector: 'app-ucenec-sporocila',
  templateUrl: './ucenec-sporocila.component.html',
  styleUrls: ['./ucenec-sporocila.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class UcenecSporocilaComponent implements OnInit, AfterViewInit {

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  sporocila: MatTableDataSource<SporociloInfo> = new MatTableDataSource<SporociloInfo>()
  displayedColumns = ["smer", "pred", "posiljatelj", "prejemnik", "datum"]

  constructor(
    private dbService: DbService,
    private dialog: MatDialog) {
  }

  @trace()
  ngAfterViewInit() {
    this.sporocila.paginator = this.paginator;
    this.sporocila.sort = this.sort;
    this.sporocila.filterPredicate = this.filterSporociloPredicate
  }

  @trace()
  ngOnInit(): void {
    this.initSporocila()
  }

  @trace()
  odpriSporociloDialog(sporociloInfo: SporociloInfo) {
    this.dialog.open(DialogSporociloComponent, {data: sporociloInfo});
  }

  @trace()
  async initSporocila() {
    const root_id = this.dbService.get_root_id()
    const oseba = await this.dbService.oseba.where(ime<Oseba>("_id")).equals(root_id).first()

    if (!oseba) return

    const kontakti = await this.dbService.kontakt.where(ime<Kontakt>("oseba_id")).equals(root_id).toArray()
    const vsa_sporocila: SporociloInfo[] = []

    for (const kontakt of kontakti) {
      const prejeta_sporocila = await this.dbService.sporocilo.where(ime<Sporocilo>("kontakt_prejemnik_id")).equals(kontakt._id as string).toArray()
      const poslana_sporocila = await this.dbService.sporocilo.where(ime<Sporocilo>("kontakt_posiljatelj_id")).equals(kontakt._id as string).toArray()
      const sporocila = [...prejeta_sporocila, ...poslana_sporocila]

      for (const sporocilo of sporocila) {
        if (!sporocilo.kontakt_posiljatelj_id) continue
        const je_posiljatelj = sporocilo.kontakt_posiljatelj_id == kontakt._id

        const posiljatelj_kontakt = await this.dbService.kontakt.where(ime<Kontakt>("_id")).equals(sporocilo.kontakt_posiljatelj_id).first()
        if (!posiljatelj_kontakt) continue

        const posiljatelj_id = posiljatelj_kontakt.oseba_id?.[0] as string
        const posiljatelj = await this.dbService.oseba.where(ime<Oseba>("_id")).equals(posiljatelj_id).first()
        if (!posiljatelj) continue

        vsa_sporocila.push({
          smer: je_posiljatelj ? "POSLANO" : "PREJETO",
          vsebina: sporocilo.vsebina || "",
          datum: String_vDate(sporocilo?.poslano?.toString() || ""),
          posiljatelj: je_posiljatelj ? posiljatelj : oseba,
          prejemnik: je_posiljatelj ? oseba : posiljatelj,
          posiljatelj_kontakt: je_posiljatelj ? posiljatelj_kontakt : kontakt,
          prejemnik_kontakt: je_posiljatelj ? kontakt : posiljatelj_kontakt,
        })

      }
    }
    this.sporocila.data = vsa_sporocila
  }

  @trace()
  applySporocilaFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.sporocila.filter = filterValue.trim()
  }

  @trace()
  filterSporociloPredicate(data: SporociloInfo, filter: string) {
    return JSON.stringify(data).includes(filter)
  }
}
