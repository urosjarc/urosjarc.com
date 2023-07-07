<script lang="ts">
  import {usecase} from "../../stores/usecaseStore";
  import {onMount} from "svelte";
  import {route} from "../../stores/routeStore";
  import Navigation from "../../components/Navigation.svelte";
  import Layout from "../../components/Layout.svelte";

  onMount(usecase.neprijavljen_v_prijavo)
</script>

<Layout lg_size="12" sm_size="12">
  <svelte:fragment slot="navigation">
    <Navigation color="green" cls="col-4" text="Profil" icon="badge" link="{route.index}"/>
    <Navigation color="green" cls="col-4" text="Testi" icon="article" link="{route.koledar}"/>
    <Navigation color="green" cls="col-4" text="Odjava" icon="directions_run" link="{route.kontakt}"/>
  </svelte:fragment>
  <svelte:fragment slot="body">
    <slot/>
  </svelte:fragment>
</Layout>
