<script lang="ts">
  import Accordion, {Panel, Header, Content} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group, Label} from "@smui/button";
  import {time} from "../../../../../../../libs/utils";
  import {goto} from "$app/navigation";
  import {route} from "../../../../../../../stores/routeStore";
  import {onMount} from "svelte";
  import {profil} from "../../../../../../../stores/profilStore";
  import type {data, domain} from "../../../../../../../types/core.d.ts";
  import TestData = data.TestData;
  import StatusData = data.StatusData;
  import Naloga = domain.Naloga;
  import {api} from "../../../../../../../stores/apiStore";

  const test_id = $page.params.test_id
  const status_id = $page.params.status_id
  let loaded = false
  let seconds = 0
  let testRef: TestData = {}
  let statusRef: StatusData = {}
  let naloga: Naloga = {}
  let audits: Array<domain.Audit> = []

  function koncaj(status_tip) {
    api.profil_status_update(test_id, status_id, status_tip).then(data => {
      profil.posodobi_status_tip(status_id, status_tip)
      goto(route.profil_test_id(test_id))
    }).catch(err => {
      console.error(err)
    })
  }

  onMount(() => {
    testRef = profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id)
    statusRef = testRef.status_refs.find((status_ref) => status_ref.status._id == status_id)
    naloga = statusRef.naloga_refs[0].naloga

    api.profil_status_audits(test_id, status_id).then(data => {
      audits = data
      loaded = true
    }).catch(err => {
      console.error(err)
    })
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
          {#if loaded}
            <Button on:click={() => koncaj(core.domain.Status.Tip.NERESENO.name)} variant="raised" class="red" style="flex-grow: 1">
              <b>{core.domain.Status.Tip.NERESENO.name}</b>
            </Button>
            <Button on:click={() => koncaj(core.domain.Status.Tip.NAPACNO.name)} variant="raised" class="orange" style="flex-grow: 1">
              <b>{core.domain.Status.Tip.NAPACNO.name}</b>
            </Button>
            <Button on:click={() => koncaj(core.domain.Status.Tip.PRAVILNO.name)} variant="raised" class="green" style="flex-grow: 1">
              <b>{core.domain.Status.Tip.PRAVILNO.name}</b>
            </Button>
          {/if}
        </Group>
      </Content>
    </Panel>
    <Panel>
      <Header color="primary">
        <h2 style="text-align: center">Audits</h2>
      </Header>
      <Content>
        {#each audits as audit}
          <b>{JSON.stringify(audit)}</b>
        {/each}
      </Content>
    </Panel>
  </Accordion>
</div>

<style>
  h1, h2 {
    margin: 0;
  }
</style>
