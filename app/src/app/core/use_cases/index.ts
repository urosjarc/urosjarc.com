import {OdjaviOseboService} from "./odjavi-osebo/odjavi-osebo.service";
import {
  PosljiPublicKontaktniObrazecService
} from "./poslji-public-kontaktni-obrazec/poslji-public-kontaktni-obrazec.service";
import {PrijaviOseboService} from "./prijavi-osebo/prijavi-osebo.service";
import {SinhronizirajOsebnePodatkeService} from "./sinhroniziraj-osebne-podatke/sinhroniziraj-osebne-podatke.service";
import {DobiNastavitveProfilaService} from "./dobi-nastavitve-profila/dobi-nastavitve-profila.service";
import {IzberiTipOsebeService} from "./izberi-tip-osebe/izberi-tip-osebe.service";
import {UseCase} from "../../utils/types";
import {DobiProfilService} from "./dobi-profil/dobi-profil.service";

export const core_useCases: UseCase[] = [
  DobiNastavitveProfilaService,
  DobiProfilService,
  IzberiTipOsebeService,
  OdjaviOseboService,
  PosljiPublicKontaktniObrazecService,
  PrijaviOseboService,
  SinhronizirajOsebnePodatkeService
]
