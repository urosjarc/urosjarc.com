<script lang="ts">
  import {page} from "$app/stores";
  import {profil} from "../../../../../stores/profilStore";
  import {route} from "../../../../../stores/routeStore";
  import Button, {Label} from '@smui/button';
  import {onMount} from "svelte";
  import Chart from "chart.js/auto";
  import {dateDistance, dateName} from "../../../../../libs/utils";
  import {core} from "../../../../../api/server-core";
  import TestData = core.data.TestData;
  import Test = core.domain.Test;
  import Status = core.domain.Status;
  import DataTable, {Body, Cell, Row} from "@smui/data-table";

  const test_id = $page.params.test_id
  const tematike = {}
  const statusi: Map<Status.Tip, number> = {}
  let testRef: TestData = {}
  let test: Test = {};
  let stevilo_statusov = 0
  let manjkajoci = 0
  let st_dni = 0

  onMount(() => {
    testRef: TestData = profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id)
    testRef.status_refs.forEach((status_ref) => {
      const info = {
        status_id: status_ref.status._id,
        tema: status_ref.naloga_refs[0].tematika_refs[0].naslov,
        tip: status_ref.status.tip || Status.Tip.OSTALO
      }
      if (info.tema in tematike) tematike[info.tema].push(info)
      else tematike[info.tema] = [info]

      if (info.tip in statusi) statusi[info.tip] += 1
      else statusi[info.tip] = 1
      stevilo_statusov += 1
    })

    test = testRef.test
    st_dni = dateDistance(test.deadline)
    manjkajoci = stevilo_statusov - statusi[Status.Tip.PRAVILNO]

    new Chart("chart", {
      type: "pie",
      data: {
        labels: Object.keys(statusi),
        datasets: [{
          backgroundColor: Object.keys(statusi).map((v) => barva_statusa(v)),
          data: Object.values(statusi)
        }]
      },
      options: {
        responsive: false,
        plugins: {
          legend: {
            position: '',
          }
        }
      }
    });
  })


  function barva_statusa(tip) {
    switch (tip) {
      case "NAPACNO":
        return "red"
      case "NERESENO":
        return "orange"
      case "PRAVILNO":
        return "green"
      default:
        return "lightgrey"
    }
  }
</script>

<div class="row justify-content-center pb-3" style="margin: 0">
  <div class="row justify-content-evenly align-items-center p-2" style="border-style: solid; border-width: 1px; border-color: lightgray">
    <canvas class="col-4 p-0" id="chart"></canvas>
    <div class="col p-2">
      <DataTable style="width: 100%">
        <Body>
        <Row>
          <Cell numeric><b>Datum:</b></Cell>
          <Cell>{test.deadline}</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Rok:</b></Cell>
          <Cell>{st_dni} dni, {dateName(test.deadline)}</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Reseno:</b></Cell>
          <Cell>{stevilo_statusov - manjkajoci}/{stevilo_statusov}, {Math.round(testRef.opravljeno * 100)}%</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Manjka:</b></Cell>
          <Cell>{manjkajoci}/{stevilo_statusov}, {100 - Math.round(testRef.opravljeno * 100)}%</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Delo:</b></Cell>
          <Cell>{Math.ceil(manjkajoci / (st_dni - 1))} nal/dan</Cell>
        </Row>
        </Body>
      </DataTable>
    </div>
  </div>

  {#each Object.entries(tematike) as [tema, infos], i}
    <h1 style="text-align: center; margin: 30px 0 0 0">{tema}</h1>
    {#each infos as info, i}
      {@const status_id = info.status_id}
      <Button class="col-1 {barva_statusa(info.tip)}" style="margin: 2px" variant="raised" href={route.profil_status_id(test_id, status_id)}>
        <Label>{i + 1}</Label>
      </Button>
    {/each}
  {/each}
</div>
