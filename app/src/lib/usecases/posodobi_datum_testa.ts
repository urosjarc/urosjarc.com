import {API} from "../stores/apiStore";
import {profil} from "$lib/stores/profilStore";

export async function posodobi_datum_testa(args: { test_id: string, datum: string }) {
  // @ts-ignore
  const new_test: Test = await API().putProfilTest(args.test_id, {datum: args.datum})

  const osebaData = profil.get()
  const test_refs = osebaData.test_refs || []

  //Loop tests
  for (let i = 0; i < test_refs.length; i++) {
    const old_test = test_refs[i].test || {}
    //Match test ids
    if (args.test_id == old_test._id && new_test._id == old_test._id) {
      // @ts-ignore
      osebaData.test_refs[i].test = new_test
      return profil.set(osebaData)
    }
  }

  throw new Error(`Test se ni posodobil!`)
}
