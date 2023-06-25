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

  function ucenje() {
    api.profil.ucenje().then(data => {
      console.log(data)
    }).catch(data => {
      console.error(data)
    })
  }


  function sporocila() {
    api.profil.sporocila().then(data => {
      console.log(data)
    }).catch(data => {
      console.error(data)
    })
  }

  function statusi() {
    api.profil.statusi().then(data => {
      console.log(data)
    }).catch(data => {
      console.error(data)
    })
  }

</script>

<div>
  <Button on:click={logout}>Logout</Button>
  <Button on:click={ucenje}>Ucenje</Button>
  <Button on:click={sporocila}>Sporocila</Button>
  <Button on:click={statusi}>Statusi</Button>
</div>
