import {API} from "../stores/apiStore";
import type {Status} from "$lib/api";
import {profil} from "$lib/stores/profilStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";

export async function posodobi_status(args: { test_id: string, status_id: string, sekund: number, tip: Status.tip }) {
  const new_status: Status = await API().putProfilTestStatus(args.test_id, args.status_id, {
    tip: args.tip,
    sekund: args.sekund
  })

  const osebaData = profil.get()
  const test_refs = osebaData.test_refs || []

  //Loop tests
  for (let i = 0; i < test_refs.length; i++) {
    const status_refs = test_refs[i].status_refs || []
    //Loop statuses
    for (let j = 0; j < status_refs.length; j++) {
      const old_status = status_refs[j].status || {}
      //Match status ids
      if (args.status_id == old_status._id && new_status._id == old_status._id) {
        //Update statuses
        // @ts-ignore
        osebaData.test_refs[i].status_refs[j].status = new_status
        profil.set(osebaData)
        return goto(route.ucenec_test_id(args.test_id))
      }
    }
  }

  throw new Error(`Status naloge se ni posodobil!`)
}
