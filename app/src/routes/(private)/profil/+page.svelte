<script lang="ts">
  import {goto} from "$app/navigation";
  import {route} from "$lib/stores/routeStore";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table"
  import {onMount} from "svelte";
  import Alerts from "$lib/components/Alerts.svelte";
  import type {Data} from "./data";
  import {data} from "./data";

  function goto_test(id) {
    goto(route.profil_test_id(id))
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
      uspeh(data: Array<Data>): void {
        _datas = data
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
