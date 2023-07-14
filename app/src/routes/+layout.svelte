<script lang="ts">
  import Navigation from "../components/Navigation.svelte";
  import Layout from "../components/Layout.svelte";
  import {route} from "../stores/routeStore";
</script>

<Layout vsebina_classes="senca-velika">
  <svelte:fragment slot="navigation">
    <Navigation classes="col-3" text="Domov" icon="home" link="{route.index}"/>
    <Navigation classes="col-3" text="Koledar" icon="event" link="{route.koledar}"/>
    <Navigation classes="col-3" text="Kontakt" icon="email" link="{route.kontakt}"/>
    <Navigation classes="col-3" text="Prijava" icon="person" link="{route.prijava}"/>
  </svelte:fragment>
  <svelte:fragment slot="body">
    <slot/>
  </svelte:fragment>
</Layout>
