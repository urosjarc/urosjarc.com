import {API} from "../stores/apiStore";
import type {ExeCallback} from "../execute";
import {execute} from "../execute";
import type {Status} from "$lib/api";
import {profil} from "$lib/stores/profilStore";


interface PosodobiStatusCallback extends ExeCallback {
  test_id: string,
  status_id: string,
  sekund: number,
  tip: Status.tip,

  uspeh(): void;
}

export async function posodobi_status(callback: PosodobiStatusCallback) {
  await execute(posodobi_status, callback, async () => {
    const new_status: Status = await API().putProfilTestStatus(callback.test_id, callback.status_id, {
      tip: callback.tip,
      sekund: callback.sekund
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
        if (callback.status_id == old_status._id && new_status._id == old_status._id) {
          //Update statuses
          // @ts-ignore
          osebaData.test_refs[i].status_refs[j].status = new_status
          profil.set(osebaData)
          return callback.uspeh()
        }
      }
    }

    throw new Error(`Status naloge se ni posodobil!`)
  })
}
