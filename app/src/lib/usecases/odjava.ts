import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";


export async function odjava() {
  localStorage.clear()
  goto(route.prijava)
}
