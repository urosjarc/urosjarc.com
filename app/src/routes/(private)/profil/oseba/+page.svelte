<script lang="ts">

  import DataTable, {Body, Cell, Head, Row} from '@smui/data-table';
  import Accordion, {Content, Header, Panel} from "@smui-extra/accordion";
  import {API} from "../../../../stores/apiStore";

  let audits = []
  let audits_loaded = false


  function load_audits() {
    if (!audits_loaded) {
      api.profil_audits().then(data => {
        console.log(core.pogledi.ProfilOsebaAudits.Companion.decode(data))
      })
    }
    audits_loaded = true
  }

  const api = API()
</script>

<div>
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
          {#each kontakt_refs as kontakt_ref, i}
            {@const kontakt = kontakt_ref.kontakt}
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
          {#if audits_loaded}
            {#if audits.length > 0}
              {#each audits as audit}
                <Row>
                  <Cell>{audit.datum}</Cell>
                  <Cell>{audit.dni} dni</Cell>
                  <Cell>{audit.value} akcij</Cell>
                  <Cell>{Math.round(audit.trajanje)} min.</Cell>
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
