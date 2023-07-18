<script lang="ts">
  import Accordion, {Content, Header, Panel} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group} from "@smui/button";
  import {time, timeFormat} from "$lib/utils";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table";
  import {Status} from "$lib/api";
  import {onMount} from "svelte";
  import {Data, data} from "./data";

  function load_audits(){
    audits({

    })
  }

  onMount(() => {
    data({
      test_id: test_id,
      status_id: status_id,
      error(err: any): void {
        error = err
      },
      fatal(err: any): void {
        fatal = err
      },
      warn(err: any): void {
        warn = err
      },
      uspeh(data: Data): void {
        _data = data
      },
    })

  })

  setInterval(() => {
    seconds += 1
  }, 1000)

  let seconds = 0
  let loaded = false
  const test_id = $page.params.test_id
  const status_id = $page.params.status_id
  let _data: Data = {}
  let error = ""
  let fatal = ""
  let warn = ""
</script>

<div class="row">

  {#if loaded}
    <Accordion>
      <Panel open>
        <Header class="{_data.cls}">
          <h1 style="text-align: center">{time(seconds)}</h1>
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
            <Button on:click={() => koncaj(Status.tip.NERESENO)} class="col-red" style="flex-grow: 1; border-radius: 0">
              <b>{Status.tip.NERESENO}</b>
            </Button>
            <Button on:click={() => koncaj(Status.tip.NAPACNO)} class="col-orange" style="flex-grow: 1">
              <b>{Status.tip.NAPACNO}</b>
            </Button>
            <Button on:click={() => koncaj(Status.tip.PRAVILNO)} class="col-forestgreen" style="flex-grow: 1; border-radius: 0">
              <b>{Status.tip.PRAVILNO}</b>
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
