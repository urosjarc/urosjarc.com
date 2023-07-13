<script lang="ts">

  import DataTable, {Body, Cell, Head, Row} from '@smui/data-table';
  import {onMount} from "svelte";
  import {profil} from "../../../../stores/profilStore";
  import type {data, domain} from "../../../../types/server-core.d.ts";
  import Oseba = domain.Oseba;
  import Naslov = domain.Naslov;
  import KontaktData = data.KontaktData;
  import {dateDistance, dateFormat, toDate} from "../../../../libs/utils";
  import {api} from "../../../../stores/apiStore";
  import Accordion, {Content, Header, Panel} from "@smui-extra/accordion";

  let oseba: Oseba = {}
  let naslovi: Array<Naslov> = []
  let kontakt_refs: Array<KontaktData> = []
  let audits = []
  let audits_loaded = false

  onMount(() => {
    let osebaData = profil.get()
    oseba = osebaData.oseba
    naslovi = osebaData.naslov_refs
    kontakt_refs = osebaData.kontakt_refs
  })

  function load_audits() {
    if(!audits_loaded){
      api.profil_audits().then(data => {
        let datum_audits = {}
        for (let audit of data) {
          let datum = dateFormat(audit.ustvarjeno)
          if (datum in datum_audits) datum_audits[datum].value += 1
          else datum_audits[datum] = { value: 1, dni: dateDistance(audit.ustvarjeno), datum: datum}
        }
        audits = Object.values(datum_audits).sort((e0, e1) => e0.dni - e1.dni).slice(0, 30)
      }).catch(err => {
        console.error(err)
      })
    }
    audits_loaded = true
  }


</script>

<div>
  <Accordion>
    <Panel open>
      <Header on:click={load_audits} class="blue">
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
      <Header on:click={load_audits} class="blue">
        <h3 style="text-align: center; margin: 0">Dejavnost</h3>
      </Header>
      <Content style="padding: 0">

        <DataTable style="width: 100%">
          <Body>
          {#each audits as audit}
            <Row>
              <Cell style="width: 43%" numeric>{audit.datum}</Cell>
              <Cell>{audit.dni} dni</Cell>
              <Cell>{audit.value} akcij</Cell>
            </Row>
          {/each}
          </Body>
        </DataTable>

      </Content>
    </Panel>
  </Accordion>
</div>
