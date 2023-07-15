import type {OsebaData, Profil} from "../api";
import {API} from "../stores/apiStore";
import {goto} from "$app/navigation";
import {route} from "../stores/routeStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";

export interface Prijavljen_v_profilParams {
  /**
   * Preveri token.
   */
  tokenObstaja(): void;

  tokenNeObstaja(): void;

  /**
   * Preveri identiteto.
   */
  getAuthWhois(authWhois: Profil): void;

  getAuthWhoisErr(authWhois: Profil): void;

  /**
   * Pododobi profil.
   */
  getProfil(osebaData: OsebaData): void;

  getProfilErr(osebaData: OsebaData): void;

  /**
   * Zakljuci.
   */
  uspeh(): void;
}

export function prijavljen_v_profil(CB: Prijavljen_v_profilParams) {
  if (token.exists()) {
    CB.tokenObstaja()
    API().getAuthWhois().then(authWhois => {
      if (authWhois.id) {
        CB.getAuthWhois(authWhois)
        API().getProfil().then(osebaData => {
          if (osebaData.oseba) {
            CB.getProfil(osebaData)
            profil.set(osebaData)
            goto(route.profil)
            CB.uspeh()
          } else CB.getProfilErr(osebaData)
        }).catch(CB.getProfilErr)
      } else CB.getAuthWhoisErr(authWhois)
    }).catch(CB.getAuthWhoisErr)
  } else CB.tokenNeObstaja()
}
