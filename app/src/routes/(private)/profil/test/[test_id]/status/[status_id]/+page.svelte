<script lang="ts">
  import Accordion, {Content, Header, Panel} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group} from "@smui/button";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table";
  import {Status} from "$lib/api";
  import {onMount} from "svelte";
  import {page_audits, page_data} from "./page";
  import {posodobi_status} from "$lib/usecases/posodobi_status";
  import {StatusTip_class} from "$lib/extends/StatusTip";
  import {Number_vCas} from "$lib/extends/Number";

  async function load_audits() {
    audits = await page_audits({test_id: test_id, status_id: status_id})
  }

  function koncaj_nalogo(tip: Status.tip) {
    posodobi_status({
      test_id: test_id,
      status_id: status_id,
      sekund: sekund,
      tip: tip,
    })
  }

  onMount(async () => {
    data = await page_data({test_id: test_id, status_id: status_id})
    loaded = true
  })

  setInterval(() => {
    sekund += 1
  }, 1000)

  const test_id = $page.params.test_id
  const status_id = $page.params.status_id

  let data = {}
  let audits = []

  let sekund = 0
  let loaded = false
</script>

<div class="row">
  {#if loaded}
    <Accordion>
      <Panel open>
        <Header class="{data.cls}">
          <h1 style="text-align: center">{Number_vCas(sekund)}</h1>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{data.vsebina_src}">
        </Content>
      </Panel>
      <Panel>
        <Header class="col-royalblue">
          <h3 style="text-align: center; margin: 0">Resitev</h3>
        </Header>
        <Content style="padding: 0">
          <img width="100%" src="{data.resitev_src}">

          <Group class="razsiri">
            {#each Object.values(Status.tip) as status}
              <Button on:click={() => koncaj_nalogo(status)} class="{StatusTip_class(status)} razsiri-gumb">
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
            {#each audits as audit, i}
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
