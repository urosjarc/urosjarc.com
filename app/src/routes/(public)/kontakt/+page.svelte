<script lang="ts">
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import {poslji_kontakt} from "$lib/usecases/poslji_kontakt";
  import Alert from "$lib/components/Alert.svelte";
  import type {Kontakt, Oseba} from "$lib/api";
  import SubmitButton from "$lib/components/SubmitButton.svelte";

  async function kontakt_submit() {
    loading = true
    const odgovor = await poslji_kontakt({ime_priimek, email, telefon, vsebina})
    oseba = odgovor.oseba
    telefonKontakt = odgovor.telefon
    emailKontakt = odgovor.email
    dialog = true
    loading = false
  }

  let ime_priimek = "";
  let email = "";
  let telefon = "";
  let vsebina = "";

  let loading = false
  let dialog = false
  let oseba: Oseba = {}
  let telefonKontakt: Kontakt = {}
  let emailKontakt: Kontakt = {}
</script>

<div>
  <form class="row" on:submit|preventDefault={kontakt_submit}>
    <div class="col-sm-12 col-xl-4">
      <Textfield required class="razsiri"
                 input$name="name"
                 bind:value={ime_priimek}
                 label="Ime in priimek">
        <Icon class="material-icons" slot="leadingIcon">person</Icon>
      </Textfield>
    </div>

    <div class="col-sm-6 col-xl-4">
      <Textfield required class="razsiri"
                 input$name="email"
                 bind:value={email}
                 label="Email">
        <Icon class="material-icons" slot="leadingIcon">email</Icon>
      </Textfield>
    </div>

    <div class="col-sm-6 col-xl-4">
      <Textfield required class="razsiri"
                 input$name="telefon"
                 bind:value={telefon}
                 label="Telefon">
        <Icon class="material-icons" slot="leadingIcon">phone</Icon>
      </Textfield>
    </div>

    <div class="col-sm-12">
      <Textfield required class="razsiri" style="height:270px"
                 input$name="vsebina"
                 textarea
                 bind:value={vsebina}
                 label="Sporočilo">
      </Textfield>
    </div>

    <div class="col-12">
      <SubmitButton loading={loading}>Poslji sporočilo</SubmitButton>
    </div>
  </form>

  <Alert bind:open={dialog} cls="col-royalblue">
    <svelte:fragment slot="naslov">
      Vaše sporočilo je bilo sprejeto!
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      Povratni sporočili sta se poslali osebi <b>"{oseba.ime} {oseba.priimek}"</b>,
      na email naslov <b>"{emailKontakt.data}"</b>, ter
      telefonsko številko <b>"{telefonKontakt.data}"</b>.
      Prosim preverite prejem povratnic.<br><br>
    </svelte:fragment>
  </Alert>
</div>
