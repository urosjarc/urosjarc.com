<script lang="ts">

  import DataTable, {Head, Body, Row, Cell} from '@smui/data-table';
  import {onMount} from "svelte";
  import {profil} from "../../../../stores/profilStore";
  import type {data, domain} from "../../../../types/server-core.d.ts";
  import Oseba = domain.Oseba;
  import Naslov = domain.Naslov;
  import KontaktData = data.KontaktData;


  let oseba: Oseba = {}
  let naslovi: Array<Naslov> = []
  let kontakt_refs: Array<KontaktData> = []

  onMount(() => {
    let osebaData = profil.get()
    oseba = osebaData.oseba
    naslovi = osebaData.naslov_refs
    kontakt_refs = osebaData.kontakt_refs
  })

</script>

<div>
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
</div>
