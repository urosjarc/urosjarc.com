import {API} from "../stores/apiStore";
import type {Status} from "$lib/api";
import {ucenec} from "$lib/stores/ucenecStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";

export async function posodobi_status(args: { test_id: string, naloga_id: string, sekund: number, tip: Status.tip }) {
  const new_status: Status = await API().putUcenecTestNaloga(args.test_id, args.naloga_id, {
    tip: args.tip,
    sekund: args.sekund
  })

  const ucenecData = ucenec.get()
  const test_refs = ucenecData.test_ucenec_refs || []

  //Loop tests
  for (let i = 0; i < test_refs.length; i++) {
    const status_refs = test_refs[i].status_refs || []
    //Loop statuses
    for (let j = 0; j < status_refs.length; j++) {
      const old_status = status_refs[j] || {}
      //Match status ids
      if (args.naloga_id == old_status._id && new_status._id == old_status._id) {
        //Update statuses
        // @ts-ignore
        ucenecData.test_refs[i].status_refs[j] = new_status
        ucenec.set(ucenecData)
        return goto(route.ucenec_test_id(args.test_id))
      }
    }
  }

  throw new Error(`Status naloge se ni posodobil!`)
}
