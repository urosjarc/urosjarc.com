import {Api, Default} from "../api";
import {token} from "./tokenStore";


export function API(): Default {
  return new Api({
    BASE: 'http://0.0.0.0:8080',
    VERSION: '1.0.0',
    TOKEN: token.get(),
  }).default
}
