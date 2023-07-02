<script lang="ts">
  import {token} from "../../../stores/tokenStore";
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import {api} from "../../../stores/apiStore";
  import Button from "@smui/button";

  function logout() {
    token.clear()
    goto(route.prijava)
  }

  function oseba() {
    api.profil.oseba().then(data => {
      console.log(data)
    }).catch(data => {
      console.error(data)
    })
  }

</script>

<div>
  <Button on:click={logout}>Logout</Button>
  <Button on:click={oseba}>Oseba</Button>
</div>
