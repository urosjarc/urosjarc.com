<script lang="ts">
  import {usecase} from "../../stores/usecaseStore";
  import {onMount} from "svelte";
  import {route} from "../../stores/routeStore";
  import Navigation from "../../components/Navigation.svelte";
  import Layout from "../../components/Layout.svelte";
  import {goto} from "$app/navigation";

  onMount(usecase.neprijavljen_v_prijavo)

  function odjava() {
    usecase.odjava()
    goto(route.prijava)
  }

  let color = "#198a00"
</script>

<div style="padding-top: 8px">
  <Layout lg_size="12" sm_size="12">
    <svelte:fragment slot="navigation">
      <Navigation radius="10px" color={color} cls="col-4" text="Profil" icon="person" link="{route.profil_oseba}"/>
      <Navigation radius="10px" color={color} cls="col-4" text="Testi" icon="article" link="{route.profil}"/>
      <Navigation radius="10px" color={color} cls="col-4" text="Odjava" icon="directions_run" on_click={odjava}/>
    </svelte:fragment>
    <svelte:fragment slot="body">
      <slot/>
    </svelte:fragment>
  </Layout>
</div>
