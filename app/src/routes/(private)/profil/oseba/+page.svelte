<script lang="ts">
  import DataTable, {Body, Cell, Head, Row} from '@smui/data-table';
  import Accordion, {Content, Header, Panel} from "@smui-extra/accordion";
  import type {Data} from "./data";
  import {data} from "./data";
  import {onMount} from "svelte";
  import type {AuditsData} from "./audits";
  import {audits} from "./audits";
  import Alerts from "$lib/components/Alerts.svelte";

  function load_audits() {
    audits({
      error(err: any): void {
        error = err
      },
      fatal(err: any): void {
        fatal = err
      },
      warn(err: any): void {
        warn = err
      },
      uspeh(data: AuditsData[]): void {
        _audits = data
        audits_show = true
      }
    })
  }

  onMount(() => {
    data({
      error(err: any): void {
        error = err
      },
      fatal(err: any): void {
        fatal = err
      },
      warn(err: any): void {
        warn = err
      },
      uspeh(data: Data) {
        oseba = data.oseba
        kontakti = data.kontakti
        naslovi = data.naslovi
      }
    })
  })

  let audits_show = false
  let _audits = []
  let oseba = {}
  let kontakti = []
  let naslovi = []
  let fatal = ""
  let error = ""
  let warn = ""
</script>

<div>
  <Alerts bind:fatal={fatal} bind:error={error} bind:warn={warn}/>

  <Accordion>
    <Panel open>
      <Header on:click={load_audits} class="col-royalblue">
        <h3 style="text-align: center; margin: 0">Profil</h3>
      </Header>
      <Content style="padding: 0">

        <DataTable style="width: 100%">
          <Body>
          <Row>
            <Cell numeric style="width: 43%"><b>Oseba:</b></Cell>
            <Cell>{oseba.tip}</Cell>
          </Row>
          <Row>
            <Cell numeric><b>Naziv:</b></Cell>
            <Cell>{oseba.ime} {oseba.priimek}</Cell>
          </Row>
          {#each naslovi as naslov, i}
            <Row>
              <Cell numeric><b>{i + 1}. Naslov:</b></Cell>
              <Cell>{naslov.ulica}, {naslov.zip} {naslov.mesto}<br>{naslov.dodatno}</Cell>
            </Row>
          {/each}
          {#each kontakti as kontakt, i}
            <Row>
              <Cell numeric><b>{i + 1}. {kontakt.tip}</b></Cell>
              <Cell>{kontakt.data}</Cell>
            </Row>
          {/each}
          </Body>
        </DataTable>

      </Content>
    </Panel>
    <Panel close>
      <Header on:click={load_audits} class="col-royalblue">
        <h3 style="text-align: center; margin: 0">Dejavnost</h3>
      </Header>
      <Content style="padding: 0">

        <DataTable style="width: 100%">

          <Head>
            <Row>
              <Cell><b>Datum</b></Cell>
              <Cell><b>Pred...</b></Cell>
              <Cell><b>Aktivnost</b></Cell>
              <Cell><b>Trajanje</b></Cell>
            </Row>
          </Head>

          <Body>

          {#if audits_show}
            {#if _audits.length > 0}
              {#each _audits as audit}
                <Row>
                  <Cell>{audit.datum}</Cell>
                  <Cell>{audit.dni} dni</Cell>
                  <Cell>{audit.akcije} akcij</Cell>
                  <Cell>{Math.round(audit.trajanje_min)} min.</Cell>
                </Row>
              {/each}
            {:else}
              <h3 style="text-align: center">Uporabnik še brez dejavnosti!</h3>
            {/if}
          {/if}

          </Body>
        </DataTable>

      </Content>
    </Panel>
  </Accordion>
</div>
