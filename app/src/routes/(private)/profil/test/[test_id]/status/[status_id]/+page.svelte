<script lang="ts">
  import Accordion, {Content, Header, Panel} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group} from "@smui/button";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table";
  import {Status} from "$lib/api";
  import {onMount} from "svelte";
  import type {Data} from "./data";
  import {data} from "./data";
  import type {AuditsData} from "./audits";
  import {audits} from "./audits";
  import {posodobi_status} from "$lib/usecases/posodobi_status";
  import {route} from "$lib/stores/routeStore";
  import {goto} from "$app/navigation";
  import {StatusTip_class} from "$lib/extends/StatusTip";
  import {Number_vCas} from "$lib/extends/Number";

  function load_audits() {
    audits({
      test_id: test_id,
      status_id: status_id,
      uspeh(data: AuditsData[]): void {
        _audits = data
      },
    })
  }

  function koncaj_nalogo(tip: Status.tip) {
    posodobi_status({
      test_id: test_id,
      status_id: status_id,
      sekund: sekund,
      tip: tip,

      uspeh(): void {
        goto(route.profil_test_id(test_id))
      },
    })
  }

  onMount(() => {
    data({
      test_id: test_id,
      status_id: status_id,
      uspeh(data: Data): void {
        _data = data
        loaded = true
      },
    })

  })

  setInterval(() => {
    sekund += 1
  }, 1000)

  const test_id = $page.params.test_id
  const status_id = $page.params.status_id

  let _data: Data
  let _audits: AuditsData[] = []
  let statusi = [Status.tip.NERESENO, Status.tip.NAPACNO, Status.tip.PRAVILNO]

  let sekund = 0
  let loaded = false
</script>

<div class="row">
  {#if loaded}
    <Accordion>
      <Panel open>
        <Header class="{_data.cls}">
          <h1 style="text-align: center">{Number_vCas(sekund)}</h1>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{_data.vsebina_src}">
        </Content>
      </Panel>
      <Panel>
        <Header class="col-royalblue">
          <h3 style="text-align: center; margin: 0">Resitev</h3>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{_data.resitev_src}">

          <Group style="display: flex; justify-content: stretch; width: 100%">
            {#each statusi as status}
              <Button on:click={() => koncaj_nalogo(status)} class="{StatusTip_class(status)}" style="flex-grow: 1; border-radius: 0">
                <b>{status}</b>
              </Button>
            {/each}
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
            {#each _audits as audit, i}
              <Row>
                <Cell>{audit.ustvarjeno_date}</Cell>
                <Cell>{audit.ustvarjeno_time}</Cell>
                <Cell>{audit.trajanje_min} min</Cell>
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
