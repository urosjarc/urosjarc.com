<script lang="ts">
  import {token} from "../../../stores/tokenStore";
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import {api} from "../../../stores/apiStore";

  let token_str = token.get()

  function logout() {
    token.clear()
    goto(route.prijava)
  }

  api.profil.oseba().then(data => {
    console.log(data)
  }).catch(data => {
    console.error(data)
  })

  let whois = {}
  api.auth.whois().then((data) => {
    whois = data
  })

</script>

<div>
  <h1> To je tvoj profil</h1>
  <h2>{token_str}</h2>
  <button on:click={logout}>Logout</button>

  {#each Object.entries(whois) as [key, value]}
    <h1>{key} {value}</h1>
  {/each}
</div>
