<script lang="ts">
  import {token} from "../../../stores/tokenStore";
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import {api} from "../../../stores/apiStore";
  import {onMount} from "svelte";

  let token_str = token.get()

  function logout() {
    token.clear()
    goto(route.prijava)
  }

  onMount(() => {
    api.profil.oseba().then(data => {
      console.log(data)
    }).catch(data => {
      console.error(data)
    })
  })

</script>

<div>
  <h1> To je tvoj profil</h1>
  <h2>{token_str}</h2>
  <button on:click={logout}>Logout</button>

</div>
