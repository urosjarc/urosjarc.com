import {Injectable} from '@angular/core';
import {ApiService} from "../../services/api/services/api.service";
import {exe, UseCase} from "../../../utils/types";

@Injectable()
export class DobiProfilService implements UseCase {

  constructor(private api: ApiService) {
  }

  async zdaj() {
    return await exe(this.api.authProfilGet())
  }
}
