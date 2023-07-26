import {API} from "../stores/apiStore";
import type {KontaktObrazecReq} from "$lib/api";

export async function poslji_kontakt(args: KontaktObrazecReq) {
  return API().postKontakt(args);
}
