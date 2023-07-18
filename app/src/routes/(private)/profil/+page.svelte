<script lang="ts">
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table"
  import {onMount} from "svelte";
  import Alerts from "../../../components/Alerts.svelte";
  import {data} from "./data";
  import type {Data} from "./data";

  function goto_test(id) {
    goto(route.profil_test_id(id))
  }

  onMount(() => {
    data({
      error(p0) {
        error = p0
      },
      fatal(p0) {
        fatal = p0
      },
      warn(p0) {
        warn = p0
      },
      uspeh(datas: Array<Data>): void {
        _datas = datas
      }
    })
  })

  let _datas: Array<Data> = []
  let fatal = ""
  let error = ""
  let warn = ""
</script>

<div>
  <Alerts bind:fatal={fatal} bind:error={error} bind:warn={warn}/>

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
    {#each _datas as data}
      <Row style="cursor: pointer" on:click={() => goto_test(data.id)}>
        <Cell class="{data.cls}">{data.naslov}</Cell>
        <Cell class="{data.cls}" numeric>{data.opravljeno}%</Cell>
        <Cell class="{data.cls}"><b>{data.oddaljenost} dni</b></Cell>
        <Cell class="{data.cls}">{data.datum}</Cell>
      </Row>
    {/each}
    </Body>

  </DataTable>
</div>
