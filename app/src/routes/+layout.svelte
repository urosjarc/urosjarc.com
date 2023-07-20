<script lang="ts">
  import Navigation from "$lib/components/Navigation.svelte";
  import Layout from "$lib/components/Layout.svelte";
  import {route} from "$lib/stores/routeStore";
  import {alerts} from "$lib/stores/alertsStore";
  import Alerts from "$lib/components/Alerts.svelte";
  import {onMount} from "svelte";

  let fatal = ""
  let error = ""
  let warn = ""
  let info = ""

  onMount(() => {
    alerts.store_fatal.subscribe(text => {
      if (text) fatal = text
    })
    alerts.store_error.subscribe(text => {
      if (text) error = text
    })
    alerts.store_warn.subscribe(text => {
      if (text) warn = text
    })
    alerts.store_unauthorized.subscribe(text => {
      if (text) warn = text
    })
    alerts.store_info.subscribe(text => {
      if (text) info = text
    })
  })
</script>

<Layout vsebina_classes="senca-velika">
  <svelte:fragment slot="navigation">
    <Navigation classes="col-3" text="Domov" icon="home" link="{route.index}"/>
    <Navigation classes="col-3" text="Koledar" icon="event" link="{route.koledar}"/>
    <Navigation classes="col-3" text="Kontakt" icon="email" link="{route.kontakt}"/>
    <Navigation classes="col-3" text="Prijava" icon="person" link="{route.prijava}"/>
  </svelte:fragment>
  <svelte:fragment slot="body">
    <Alerts bind:info={info} bind:fatal={fatal} bind:error={error} bind:warn={warn}/>
    <slot/>
  </svelte:fragment>
</Layout>
