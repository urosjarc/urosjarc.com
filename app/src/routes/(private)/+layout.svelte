<script lang="ts">
  import {route} from "../../stores/routeStore";
  import Navigation from "../../components/Navigation.svelte";
  import Layout from "../../components/Layout.svelte";

  function odjava(){
    console.log("odjava")
  }
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
