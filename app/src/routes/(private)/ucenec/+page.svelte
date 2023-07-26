<script lang="ts">
  import {goto} from "$app/navigation";
  import {route} from "$lib/stores/routeStore";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table"
  import {onMount} from "svelte";
  import {type Data, page_data} from "./page";

  function goto_test(id: string) {
    goto(route.ucenec_test_id(id))
  }

  onMount(async () => {
    data = await page_data()
  })

  let data: Array<Data> = []
</script>

<DataTable class="razsiri">
  <Head>
    <Row>
      <Cell><b>Naslov</b></Cell>
      <Cell numeric><b>Rešeno</b></Cell>
      <Cell><b>Ostalo</b></Cell>
      <Cell><b>Deadline</b></Cell>
    </Row>
  </Head>
  <Body>
  {#each data as data}
    <Row style="cursor: pointer" on:click={() => goto_test(data.id)}>
      <Cell class="{data.cls}">{data.naslov}</Cell>
      <Cell class="{data.cls}" numeric>{data.opravljeno}%</Cell>
      <Cell class="{data.cls}"><b>{data.oddaljenost} dni</b></Cell>
      <Cell class="{data.cls}">{data.datum}</Cell>
    </Row>
  {/each}
  </Body>

</DataTable>
