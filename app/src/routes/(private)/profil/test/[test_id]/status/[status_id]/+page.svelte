<script lang="ts">
  import Accordion, {Panel, Header, Content} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group, Label} from "@smui/button";
  import {dateDistance, time} from "../../../../../../../libs/utils";
  import {goto} from "$app/navigation";
  import {route} from "../../../../../../../stores/routeStore";
  import {onMount} from "svelte";
  import {profil} from "../../../../../../../stores/profilStore";
  import Chart from "chart.js/auto";
  import {core} from "../../../../../../../api/server-core";
  import TestData = core.data.TestData;
  import StatusData = core.data.StatusData;
  import NalogaData = core.data.NalogaData;
  import Naloga = core.domain.Naloga;

  const test_id = $page.params.test_id
  const status_id = $page.params.status_id
  let seconds = 0
  let testRef: TestData = {}
  let statusRef: StatusData = {}
  let naloga: Naloga = {}

  function koncaj(status_tip) {
    console.log({
      status_tip, test_id, status_id, seconds
    })
    goto(route.profil_test_id($page.params.test_id))
  }

  onMount(() => {
    testRef = profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id)
    statusRef = testRef.status_refs.find((status_ref) => status_ref.status._id == status_id)
    console.log(testRef)
    console.log(statusRef)
    naloga = statusRef.naloga_refs[0].naloga
  })

  setInterval(() => {
    seconds += 1
  }, 1000)
</script>

<div class="row">

  <Accordion>
    <Panel open>
      <Header color="primary">
        <h1 style="text-align: center">{time(seconds)}</h1>
      </Header>
      <Content>
        <img width="100%" src="{naloga.vsebina}">
      </Content>
    </Panel>
    <Panel>
      <Header>
        <h2 style="text-align: center">Resitev</h2>
      </Header>
      <Content>
        <img width="100%" src="{naloga.resitev}">

        <Group style="display: flex; justify-content: stretch; width: 100%;">
          <Button on:click={() => koncaj("NERESENO")} variant="raised" class="red" style="flex-grow: 1">
            <b>NERESENO</b>
          </Button>
          <Button on:click={() => koncaj("NAPACNO")} variant="raised" class="orange" style="flex-grow: 1">
            <b>NAPACNO</b>
          </Button>
          <Button on:click={() => koncaj("PRAVILNO")} variant="raised" class="green" style="flex-grow: 1">
            <b>PRAVILNO</b>
          </Button>
        </Group>
      </Content>
    </Panel>
    <Panel>
      <Header color="primary">
        <h2 style="text-align: center">Audits</h2>
      </Header>
      <Content>
        <b>TODO: Zgodovina</b>
      </Content>
    </Panel>
  </Accordion>
</div>

<style>
  h1, h2 {
    margin: 0;
  }
</style>
