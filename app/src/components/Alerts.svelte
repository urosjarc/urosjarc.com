<script lang="ts">

  import Alert from "./Alert.svelte";
  import {Separator} from "@smui/list";

  export let fatal: string = ""
  export let error: string = ""
  export let warn: string = ""

</script>

<div>

  <Alert bind:open={fatal} cls="col-red">
    <svelte:fragment slot="naslov">
      SISTEMSKA NAPAKA
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      <p>
        Zgodila se je sistemska napaka.<br>
        Sistem je že poslal poročilo o incidentu.<br>
        Takoj ko bom lahko, bom napako odpravil in Vas o tem tudi
        obvestil.
      </p>
      <Separator/>
      <p><b>{JSON.stringify(fatal, null, 4)}</b></p>
    </svelte:fragment>
  </Alert>

  <Alert bind:open={error} cls="col-orange">
    <svelte:fragment slot="naslov">
      UPORABNIŠKA NAPAKA
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      <p>
        Zgodila se je uporabniška napaka.<br>
        Sistem je že poslal poročilo o incidentu.<br>
        Če je napaka sistemska, Vas bom obvestil takoj, ko bom napako odpravil.
      </p>
      <Separator/>
      <p><b>{JSON.stringify(error, null, 4)}</b></p>
    </svelte:fragment>
  </Alert>

  <Alert bind:open={warn}>
    <svelte:fragment slot="naslov">
      OPOZORILO
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      <p>
        Zgodila se je pogosta uporabniška napaka.<br>
        Sistem je zabeležil incident.
      </p>
      <Separator/>
      <p><b>{JSON.stringify(warn, null, 4)}</b></p>
    </svelte:fragment>
  </Alert>

</div>
