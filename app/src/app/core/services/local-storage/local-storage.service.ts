import {Injectable} from '@angular/core';
import {trace} from "../../../utils/trace";

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() {
  }

  get_root_id(): string {
    return localStorage.getItem("root_id") || ""
  }

  @trace()
  set_root_id(root_id: string) {
    return localStorage.setItem("root_id", root_id)
  }

  get_token(): string {
    return localStorage.getItem("token") || ""
  }

  @trace()
  set_token(token: string) {
    localStorage.setItem("token", token)
  }

  @trace()
  drop(){
    this.set_token("")
    this.set_root_id("")
  }
}
