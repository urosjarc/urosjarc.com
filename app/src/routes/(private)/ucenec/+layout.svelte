<script lang="ts">
  import {route} from "$lib/stores/routeStore";
  import Navigation from "$lib/components/Navigation.svelte";
  import Layout from "$lib/components/Layout.svelte";
  import {odjava} from "$lib/usecases/odjava";
</script>

<div style="padding-top: 8px">
  <Layout lg_size="12" sm_size="12">
    <svelte:fragment slot="navigation">
      <Navigation radius="10px" classes="col-4" text="Profil" icon="person" link="{route.ucenec_oseba}"/>
      <Navigation radius="10px" classes="col-4" text="Testi" icon="article" link="{route.ucenec}"/>
      <Navigation radius="10px" classes="col-4" text="Odjava" icon="directions_run" on_click={odjava}/>
    </svelte:fragment>
    <svelte:fragment slot="body">
      <slot/>
    </svelte:fragment>
  </Layout>
</div>
