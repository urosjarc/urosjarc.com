<script lang="ts">

  import Alert from "./Alert.svelte";
  import {Separator} from "@smui/list";
  import {Error_pretty} from "../extends/Error";

  export let fatal: string = ""
  export let error: string = ""
  export let warn: string = ""
  export let info: string = ""

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
      <pre><b>{Error_pretty(fatal)}</b></pre>
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
      <pre><b>{Error_pretty(error)}</b></pre>
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
      <pre><b>{Error_pretty(warn)}</b></pre>
    </svelte:fragment>
  </Alert>

  <Alert bind:open={info}>
    <svelte:fragment slot="naslov">
      INFO
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      <pre style="text-align: left"><b>{JSON.stringify(info, null, 2)}</b></pre>
    </svelte:fragment>
  </Alert>

</div>

<style>
  pre {
    text-align: center;
  }
</style>
