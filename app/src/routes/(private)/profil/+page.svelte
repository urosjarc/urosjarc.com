<script lang="ts">
  import {token} from "../../../stores/tokenStore";
  import {goto} from "$app/navigation";

  let token_str = token.get()

  function logout() {
    token.clear()
    goto("/prijava")
  }
</script>

<div>
  <h1> To je tvoj profil</h1>
  <h2>{token_str}</h2>
  <button on:click={logout}>Logout</button>
</div>
