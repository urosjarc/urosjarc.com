import {API} from "../stores/apiStore";
import type {KontaktReq} from "$lib/api";

export async function poslji_kontakt(args: KontaktReq) {
  return await API().postKontakt(args)
}
