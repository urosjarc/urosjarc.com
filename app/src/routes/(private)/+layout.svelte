<script lang="ts">
  import {route} from "$lib/stores/routeStore";
  import Navigation from "$lib/components/Navigation.svelte";
  import Layout from "$lib/components/Layout.svelte";
  import {auth_guard} from "$lib/usecases/auth_guard";
  import {onMount} from "svelte";

  function odjava() {

  }

  onMount(auth_guard)

</script>

<div style="padding-top: 8px">
  <Layout lg_size="12" sm_size="12">
    <svelte:fragment slot="navigation">
      <Navigation radius="10px" classes="col-4" text="Profil" icon="person" link="{route.profil_oseba}"/>
      <Navigation radius="10px" classes="col-4" text="Testi" icon="article" link="{route.profil}"/>
      <Navigation radius="10px" classes="col-4" text="Odjava" icon="directions_run" on_click={odjava}/>
    </svelte:fragment>
    <svelte:fragment slot="body">
      <slot/>
    </svelte:fragment>
  </Layout>
</div>
