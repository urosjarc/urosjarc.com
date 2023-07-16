import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {API} from "../stores/apiStore";
import {route} from "../stores/routeStore";
import {goto} from "$app/navigation";
import type {Profil} from "../api";




// api.auth.prijava({username})
//   .then(prijavaRes => {
//     if ("token" in prijavaRes) {
//       token.set(prijavaRes.token)
//       usecase.posodobi_profil().then(() => {
//         goto(route.profil)
//       })
//     } else {
//       usecase.obvestilo_napake("API ne pošilja uporabniskega token ključa!")
//     }
//   }).catch(data => {
//   usecase.obvestilo_napake("API ni mogel prijaviti uporabnika!")
// })
// },
// prijavljen_v_profil() {
//   if (token.exists()) api.auth.whois().then(() => goto(route.profil)).catch(() => usecase.odjava())
// },
// neprijavljen_v_prijavo() {
//   if (token.exists()) {
//     api.auth.whois().catch(() => {
//       usecase.odjava()
//       goto(route.prijava)
//     })
//   } else goto(route.prijava)
// },
// obvestilo_napake(sporocilo: String) {
//   alert(sporocilo)
// },
// nastavi_profil() {
//   if (!profil.exists()) {
//     api.profil().then(profilRes => {
//       profil.set(profilRes)
//     }).catch(data => {
//       console.error(data)
//       usecase.obvestilo_napake("API ni vrnil uporabniskega profila!")
//     })
//   }
// },
// posodobi_profil() {
//   return api.profil().then(profilRes => {
//     profil.set(profilRes)
//   }).catch(data => {
//     console.error(data)
//     usecase.obvestilo_napake("API ni vrnil uporabniskega profila!")
//   })
// },
// posodobi_status(test_id: string, status_id: string, status_tip: domain.Status.Tip, sekund: number) {
//   api.profil_status_update(test_id, status_id, status_tip, sekund).then(status => {
//
//     let p = profil.get()
//     for (let i = 0; i < p.test_refs.length; i++) {
//       for (let j = 0; j < p.test_refs[i].status_refs.length; j++) {
//         if (status_id == p.test_refs[i].status_refs[j].status._id) {
//           p.test_refs[i].status_refs[j].status = status
//           profil.set(p)
//           goto(route.profil_test_id(test_id))
//           return true
//         }
//       }
//     }
//     console.error("Status se ni posodobil :(")
//   }).catch(err => {
//     console.error(err)
//   })
// },
// odjava() {
//   token.clear()
//   profil.clear()
// }
// }
