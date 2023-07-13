<script lang="ts">
  import Accordion, {Panel, Header, Content} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group, Label} from "@smui/button";
  import {dateName, time, timeFormat} from "../../../../../../../libs/utils";
  import {goto} from "$app/navigation";
  import {route} from "../../../../../../../stores/routeStore";
  import {onMount} from "svelte";
  import {profil} from "../../../../../../../stores/profilStore";
  import type {data, domain} from "../../../../../../../types/server-core.d.ts";
  import TestData = data.TestData;
  import StatusData = data.StatusData;
  import Naloga = domain.Naloga;
  import {api} from "../../../../../../../stores/apiStore";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table";
  import {dateFormat} from "../../../../../../../libs/utils.js";
  import {barva_statusa} from "../../../../../../../libs/stili";

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

  {#if loaded}
    <Accordion>
      <Panel open>
        <Header style="background-color: {barva_statusa(statusRef.status.tip)}">
          <h1 style="text-align: center">{time(seconds)}</h1>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{naloga.vsebina}">
        </Content>
      </Panel>
      <Panel>
        <Header>
          <h2 style="text-align: center">Resitev</h2>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{naloga.resitev}">

          <Group style="display: flex; justify-content: stretch; width: 100%">
            <Button on:click={() => koncaj(core.domain.Status.Tip.NERESENO.name)} class="red" style="flex-grow: 1; border-radius: 0">
              <b>{core.domain.Status.Tip.NERESENO.name}</b>
            </Button>
            <Button on:click={() => koncaj(core.domain.Status.Tip.NAPACNO.name)} class="orange" style="flex-grow: 1">
              <b>{core.domain.Status.Tip.NAPACNO.name}</b>
            </Button>
            <Button on:click={() => koncaj(core.domain.Status.Tip.PRAVILNO.name)} class="green" style="flex-grow: 1; border-radius: 0">
              <b>{core.domain.Status.Tip.PRAVILNO.name}</b>
            </Button>
          </Group>
        </Content>
      </Panel>
      <Panel>
        <Header>
          <h2 style="text-align: center">Audits</h2>
        </Header>
        <Content style="padding: 0">
          <DataTable style="width: 100%">
            <Head>
              <Row>
                <Cell numeric><b>#</b></Cell>
                <Cell><b>Datum</b></Cell>
                <Cell><b>Čas</b></Cell>
                <Cell><b>Opis</b></Cell>
              </Row>
            </Head>
            <Body>
            {#each audits as audit, i}
              <Row>
                <Cell numeric>{i + 1}</Cell>
                <Cell>{dateFormat(audit.ustvarjeno)}</Cell>
                <Cell>{timeFormat(audit.ustvarjeno)}</Cell>
                <Cell>{audit.opis}</Cell>
              </Row>
            {/each}
            </Body>
          </DataTable>
        </Content>
      </Panel>
    </Accordion>
  {/if}
</div>

<style>
  h1, h2 {
    margin: 0;
  }

</style>
