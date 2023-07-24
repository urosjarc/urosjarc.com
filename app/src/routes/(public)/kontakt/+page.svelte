<script lang="ts">
  import Button from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import {poslji_kontakt} from "$lib/usecases/poslji_kontakt";
  import Alert from "$lib/components/Alert.svelte";
  import type {Kontakt, Oseba, Sporocilo} from "$lib/api";

  async function kontakt_submit() {
    const odgovor = await poslji_kontakt({ime_priimek, email, telefon, vsebina})
    oseba = odgovor.oseba
    telefonKontakt = odgovor.telefon
    emailKontakt = odgovor.email
    sporocila = odgovor.sporocila || []
    dialog = true
  }

  let ime_priimek = "";
  let email = "";
  let telefon = "";
  let vsebina = "";

  let dialog = false
  let oseba: Oseba = {}
  let telefonKontakt: Kontakt = {}
  let emailKontakt: Kontakt = {}
  let sporocila: Array<Sporocilo> = []
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
      <Button class="razsiri" variant="raised" type="submit">
        <b>Pošlji sporočilo</b>
      </Button>
    </div>
  </form>

  <Alert bind:open={dialog} cls="col-red">
    <svelte:fragment slot="naslov">
      Sprejem kontakta
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      <p>Email se je uspesno poslal.</p>
      <p>Ime: {oseba.ime}</p>
      <p>Priimek: {oseba.priimek}</p>
      <p>Telefon: {telefon.data}</p>
      <p>Email: {email.data}</p>
      {#each sporocila as sporocilo}
        <p>SMS: {sporocilo.vsebina}</p>
      {/each}
    </svelte:fragment>
  </Alert>
</div>
