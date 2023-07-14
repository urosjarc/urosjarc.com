<script lang="ts">
  import Accordion, {Content, Header, Panel} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group} from "@smui/button";
  import {time, timeFormat} from "../../../../../../../libs/utils";
  import {onMount} from "svelte";
  import {profil} from "../../../../../../../stores/profilStore";
  import type {data, domain} from "../../../../../../../types/server-core.d.ts";
  import {api} from "../../../../../../../stores/apiStore";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table";
  import {dateFormat} from "../../../../../../../libs/utils.js";
  import {barva_statusa} from "../../../../../../../libs/stili";
  import {usecase} from "../../../../../../../stores/usecaseStore";
  import TestData = data.TestData;
  import StatusData = data.StatusData;
  import Naloga = domain.Naloga;

  const test_id = $page.params.test_id
  const status_id = $page.params.status_id
  let loaded = false
  let audits_loaded = false
  let seconds = 0
  let testRef: TestData = {}
  let statusRef: StatusData = {}
  let naloga: Naloga = {}
  let audits: Array<domain.Audit> = []

  function koncaj(status_tip) {
    usecase.posodobi_status(test_id, status_id, status_tip, seconds)
  }

  function load_audits() {
    if (!audits_loaded) {
      api.profil_status_audits(test_id, status_id).then(data => {
        audits = data
      }).catch(err => {
        console.error(err)
      })
    }
    audits_loaded = true
  }

  onMount(() => {
    testRef = profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id)
    statusRef = testRef.status_refs.find((status_ref) => status_ref.status._id == status_id)
    naloga = statusRef.naloga_refs[0].naloga
    loaded = true

  })

  setInterval(() => {
    seconds += 1
  }, 1000)
</script>

<div class="row">

  {#if loaded}
    <Accordion>
      <Panel open>
        <Header class="{barva_statusa(statusRef.status.tip)}">
          <h1 style="text-align: center">{time(seconds)}</h1>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{naloga.vsebina}">
        </Content>
      </Panel>
      <Panel>
        <Header class="col-royalblue">
          <h3 style="text-align: center; margin: 0">Resitev</h3>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{naloga.resitev}">

          <Group style="display: flex; justify-content: stretch; width: 100%">
            <Button on:click={() => koncaj(core.domain.Status.Tip.NERESENO.name)} class="col-red" style="flex-grow: 1; border-radius: 0">
              <b>{core.domain.Status.Tip.NERESENO.name}</b>
            </Button>
            <Button on:click={() => koncaj(core.domain.Status.Tip.NAPACNO.name)} class="col-orange" style="flex-grow: 1">
              <b>{core.domain.Status.Tip.NAPACNO.name}</b>
            </Button>
            <Button on:click={() => koncaj(core.domain.Status.Tip.PRAVILNO.name)} class="col-forestgreen" style="flex-grow: 1; border-radius: 0">
              <b>{core.domain.Status.Tip.PRAVILNO.name}</b>
            </Button>
          </Group>
        </Content>
      </Panel>
      <Panel>
        <Header class="col-royalblue" on:click={load_audits}>
          <h3 style="text-align: center; margin: 0">Dejavnost</h3>
        </Header>
        <Content style="padding: 0">
          <DataTable style="width: 100%">
            <Head>
              <Row>
                <Cell><b>Datum</b></Cell>
                <Cell><b>Ura</b></Cell>
                <Cell><b>Trajanje</b></Cell>
                <Cell><b>Opis</b></Cell>
              </Row>
            </Head>
            <Body>
            {#each audits as audit, i}
              <Row>
                <Cell>{dateFormat(audit.ustvarjeno)}</Cell>
                <Cell>{timeFormat(audit.ustvarjeno)}</Cell>
                <Cell>{core.trajanje_minut(audit.trajanje)} min</Cell>
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
