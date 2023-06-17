<script lang="ts">
  import Button, {Group, Label} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import {api} from "../../../stores/apiStore";
  import {formData} from "../../../libs/utils";

  let response: Object = {}
  let email = "";


  async function poslji(e: SubmitEvent) {
    const form = formData(e)
    api.kontakt({
      ime: form.ime,
      priimek: form.priimek,
      vsebina: form.vsebina,
      kontakti: [
        {data: form.telefon, tip: "TELEFON"},
        {data: form.email, tip: "EMAIL"}
      ]
    }).then(data => {
      response = data
    }).catch(data => {
      response = data
    })
  }
</script>

<form class="row" on:submit|preventDefault={poslji}>
  <div class="col">

    <Textfield
      style="width: 100%;margin-bottom: 10px;"
      helperLine$style="width: 100%;"
      name="geslo" bind:value={email} label="Geslo">
      <Icon class="material-icons" slot="leadingIcon">lock</Icon>
    </Textfield>
    <Group style="display: flex; justify-content: stretch;">
      <Button variant="raised" color="primary" style="flex-grow: 1;">
        <b>Vstopi</b>
      </Button>
    </Group>
  </div>
</form>
