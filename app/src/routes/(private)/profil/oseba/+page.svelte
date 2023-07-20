<script lang="ts">
  import DataTable, {Body, Cell, Head, Row} from '@smui/data-table';
  import Accordion, {Content, Header, Panel} from "@smui-extra/accordion";
  import {onMount} from "svelte";
  import {type Audits, type Napake, page_audits, page_data, page_napake} from "./page";
  import type {Oseba} from "$lib/api";

  async function load_audits() {
    if (!show_audits) {
      audits = await page_audits()
      show_audits = true
    }
  }

  async function load_napake() {
    if (!show_napake) {
      napake = await page_napake()
      show_napake = true
    }
  }

  onMount(() => {
    ({oseba, kontakti, naslovi} = page_data())
  })

  let show_audits = false
  let show_napake = false
  let audits: Audits[] = []
  let napake: Napake[] = []
  let oseba: Oseba = {}
  let kontakti = []
  let naslovi = []
</script>

<Accordion>
  <Panel open>
    <Header class="col-royalblue">
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

        {#if show_audits}
          {#if audits.length > 0}
            {#each audits as audit}
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
  <Panel close>
    <Header on:click={load_napake} class="col-royalblue">
      <h3 style="text-align: center; margin: 0">Napake</h3>
    </Header>
    <Content style="padding: 0">

      <DataTable style="width: 100%">

        <Head>
          <Row>
            <Cell><b>Datum</b></Cell>
            <Cell><b>Pred...</b></Cell>
            <Cell><b>Napake</b></Cell>
          </Row>
        </Head>

        <Body>

        {#if show_napake}
          {#if napake.length > 0}
            {#each napake as napaka}
              <Row>
                <Cell>{napaka.datum}</Cell>
                <Cell>{napaka.dni} dni</Cell>
                <Cell>{napaka.napake}</Cell>
              </Row>
            {/each}
          {:else}
            <h3 style="text-align: center">Uporabnik še brez napak!</h3>
          {/if}
        {/if}

        </Body>
      </DataTable>

    </Content>
  </Panel>
</Accordion>
