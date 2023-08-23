import {Injectable} from '@angular/core';
import {DbService} from "../../services/db/db.service";
import {trace} from "../../../utils/trace";
import {Router} from "@angular/router";
import {appUrls} from "../../../app.urls";
import {UseCase} from "../../../utils/types";

@Injectable()
export class OdjaviOseboService implements UseCase {

  constructor(
    private router: Router,
    private db: DbService) {
  }

  @trace()
  async zdaj() {
    this.db.drop()
    await this.router.navigate([appUrls.public({}).prijava({}).$])
  }
}
