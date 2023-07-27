import {API} from "../stores/apiStore";
import {ucenec} from "$lib/stores/ucenecStore";

export async function posodobi_datum_testa(args: { test_id: string, datum: string }) {
  // @ts-ignore
  const new_test: Test = await API().putUcenecTest(args.test_id, {datum: args.datum})

  const ucenecData = ucenec.get()
  const test_ucenec_refs = ucenecData.test_ucenec_refs || []

  //Loop tests
  for (let i = 0; i < test_ucenec_refs.length; i++) {
    const old_test = test_ucenec_refs[i].test || {}
    //Match test ids
    if (args.test_id == old_test._id && new_test._id == old_test._id) {
      // @ts-ignore
      ucenecData.test_ucenec_refs[i].test = new_test
      return ucenec.set(ucenecData)
    }
  }

  throw new Error(`Test se ni posodobil!`)
}
