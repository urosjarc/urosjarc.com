import {AdminRepoService} from "./admin/admin-repo.service";
import {OsebaRepoService} from "./oseba/oseba-repo.service";
import {UcenecRepoService} from "./ucenec/ucenec-repo.service";
import {UciteljRepoService} from "./ucitelj/ucitelj-repo.service";

export const core_repos = [
  AdminRepoService,
  OsebaRepoService,
  UcenecRepoService,
  UciteljRepoService
]
