<script lang="ts">

  import Alert from "./Alert.svelte";
  import {Separator} from "@smui/list";
  import {Error_serializiraj} from "../extends/Error";

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
      <pre><b>{Error_serializiraj(fatal)}</b></pre>
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
      <pre><b>{Error_serializiraj(error)}</b></pre>
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
      <pre style="text-align: center"><b>{Error_serializiraj(warn)}</b></pre>
    </svelte:fragment>
  </Alert>

</div>

<style>
  pre {
    text-align: center;
  }
</style>
