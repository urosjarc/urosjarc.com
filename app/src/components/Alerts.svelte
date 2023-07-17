<script lang="ts">

  import Alert from "./Alert.svelte";

  export let fatal: string = ""
  export let error: string = ""
  export let warn: string = ""

</script>

<div>

  <Alert bind:open={fatal} cls="col-red">
    <svelte:fragment slot="naslov">
      SISTEMSKA NAPAKA<br>{fatal}
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      Zgodila se je sistemska napaka.
      Sistem je že poslal poročilo o incidentu.
      Takoj ko bom lahko, bom napako odpravil in Vas o tem tudi
      obvestil.
    </svelte:fragment>
  </Alert>

  <Alert bind:open={error} cls="col-red">
    <svelte:fragment slot="naslov">
      UPORABNIŠKA NAPAKA<br>{error}
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      Zgodila se je neznana uporabniška napaka.
      Sistem je že poslal poročilo o incidentu.
      Če je napaka sistemska, Vas bom obvestil ko bom napako odpravil.
    </svelte:fragment>
  </Alert>

  <Alert bind:open={warn} cls="col-orange">
    <svelte:fragment slot="naslov">
      OPOZORILO
    </svelte:fragment>
    <svelte:fragment slot="vsebina">
      {warn}
    </svelte:fragment>
  </Alert>

</div>
