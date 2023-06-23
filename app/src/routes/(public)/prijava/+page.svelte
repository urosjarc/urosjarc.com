<script lang="ts">
  import Button, {Group} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import {api} from "../../../stores/apiStore";
  import {token} from "../../../stores/tokenStore";
  import {goto} from "$app/navigation";

  let response: Object = {}
  let username = "";

  async function poslji() {
    console.log("poslji")
    api.auth.prijava({
      username
    }).then(data => {
      token.set(data.token)
      goto("/profil")
    }).catch(data => {
      console.log(data)
    })
  }

  if (token.get()) api.auth.whois().then(() => goto("/profil")).catch(data => console.log(data))
</script>

<form class="row">
  <div class="col">
    <Textfield
      style="width: 100%;margin-bottom: 10px;"
      helperLine$style="width: 100%;"
      bind:value={username} label="Uporabnik">
      <Icon class="material-icons" slot="leadingIcon">person</Icon>
    </Textfield>
    <Group on:click={poslji} style="display: flex; justify-content: stretch;">
      <Button variant="raised" color="primary" style="flex-grow: 1;">
        <b>Prijava</b>
      </Button>
    </Group>
  </div>
</form>
