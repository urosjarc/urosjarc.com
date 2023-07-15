<script lang="ts">
  import {page} from "$app/stores";
  import {profil} from "../../../../../stores/profilStore";
  import {route} from "../../../../../stores/routeStore";
  import Button, {Group, Label} from '@smui/button';
  import {onMount} from "svelte";
  import Chart from "chart.js/auto";
  import {average, dateDistance, dateName, mean_error, sum} from "../../../../../libs/utils";
  import DataTable, {Body, Cell, Row} from "@smui/data-table";
  import {barva_statusa} from "../../../../../libs/stili";
  import Dialog, {Title, Content, Actions} from '@smui/dialog';
  import {Separator} from "@smui/list";

  const test_id = $page.params.test_id

  const tematike = {}
  const statusi = {}
  let testRef = {}
  let test = {};

  let stevilo_statusov = 0
  let manjkajoci = 0
  let opravljeni = 0
  let nezaceto = 0
  let st_dni = 0
  let open = false
  let trajanje_pravilnih = []
  let trajanje = []
  let st_trajanje_pravilnih = 0
  let st_trajanje = 0
  let min_trajanje = 0
  let min_trajanje_pravilnih = 0
  let pravilni_mean = 0
  let pravilni_mean_err = 0
  let mean = 0
  let mean_err = 0

  // function show_dialog() {
  //   api.profil_test_audits(test_id).then(audits => {
  //     for (let audit of audits) {
  //       let min = core.trajanje_minut(audit.trajanje)
  //       trajanje.push(min)
  //       if (audit.opis == core.domain.Status.Tip.PRAVILNO.name) {
  //         trajanje_pravilnih.push(min)
  //       }
  //     }
  //     console.log(trajanje_pravilnih, trajanje)
  //     min_trajanje = sum(trajanje)
  //     min_trajanje_pravilnih = sum(trajanje_pravilnih)
  //     st_trajanje = trajanje.length
  //     st_trajanje_pravilnih = trajanje_pravilnih.length
  //     pravilni_mean = Math.round(average(trajanje_pravilnih) * 10) / 10
  //     pravilni_mean_err = Math.round(mean_error(trajanje_pravilnih) * 10) / 10
  //     mean = Math.round(average(trajanje) * 10) / 10
  //     mean_err = Math.round(mean_error(trajanje) * 10) / 10
  //     open = true
  //   })
  // }
  //
  // onMount(() => {
  //   testRef = profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id)
  //   testRef.status_refs.forEach((status_ref) => {
  //     const info = {
  //       status_id: status_ref.status._id,
  //       tema: status_ref.naloga_refs[0].tematika_refs[0].naslov,
  //       tip: status_ref.status.tip || core.domain.Status.Tip.NEZACETO.name
  //     }
  //     if (info.tema in tematike) tematike[info.tema].push(info)
  //     else tematike[info.tema] = [info]
  //
  //     if (info.tip in statusi) statusi[info.tip] += 1
  //     else statusi[info.tip] = 1
  //
  //     stevilo_statusov += 1
  //   })
  //
  //   test = testRef.test
  //   st_dni = dateDistance(test.deadline)
  //   opravljeni = statusi[core.domain.Status.Tip.PRAVILNO.name]
  //   nezaceto = statusi[core.domain.Status.Tip.NEZACETO.name]
  //   manjkajoci = stevilo_statusov - opravljeni
  //
  //   new Chart("chart", {
  //     type: "pie",
  //     data: {
  //       labels: Object.keys(statusi),
  //       datasets: [{
  //         backgroundColor: Object.keys(statusi).map((v) => barva_statusa(v)),
  //         data: Object.values(statusi)
  //       }]
  //     },
  //     options: {
  //       responsive: false,
  //       plugins: {
  //         legend: {
  //           position: '',
  //         }
  //       }
  //     }
  //   });
  // })


</script>

<div class="row justify-content-center pb-3" style="margin: 0">
  <Button style="width: 100%; border-radius: 0" variant="raised" color="primary" on:click={show_dialog}>
    <b>Pokaži časovno statistiko</b>
  </Button>
  <div class="row justify-content-evenly align-items-center p-2" style="border-style: solid; border-width: 1px; border-color: lightgray">
    <canvas class="col-4 p-0" id="chart"></canvas>
    <div class="col p-2">
      <DataTable class="senca-mala" style="width: 100%">
        <Body>
        <Row>
          <Cell numeric><b>Datum:</b></Cell>
          <Cell>{test.deadline}</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Rok:</b></Cell>
          <Cell>{st_dni} dni ({dateName(test.deadline)})</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Reseno:</b></Cell>
          <Cell>{stevilo_statusov - manjkajoci}/{stevilo_statusov} ({Math.round(opravljeni / stevilo_statusov * 100)}%)</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Manjka:</b></Cell>
          <Cell>{manjkajoci}/{stevilo_statusov} ({100 - Math.round(opravljeni / stevilo_statusov * 100)}%)</Cell>
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
        <Label><b>{i + 1}</b></Label>
      </Button>
    {/each}
  {/each}


  <Dialog bind:open>
    <Content>
      <p style="text-align: center"><b>VSE NALOGE</b></p>
      <Separator/>
      <ul>
        <li>Število reševanj: {st_trajanje} nalog</li>
        <li>Skupno: {min_trajanje} min ({Math.round(min_trajanje / 60 * 100) / 100} h)</li>
        <li>Povprečje: {mean} +- {mean_err} min/nal</li>
      </ul>
      <p style="text-align: center"><b>PRAVILNE NALOGE</b></p>
      <Separator/>
      <ul>
        <li>Stevilo reševanj: {st_trajanje_pravilnih} nalog</li>
        <li>Skupno: {min_trajanje_pravilnih} min ({Math.round(min_trajanje_pravilnih / 60 * 100) / 100} h)</li>
        <li>Povprečje: {pravilni_mean} +- {pravilni_mean_err} min/nal</li>
      </ul>
      <Separator/>
      <p style="text-align: center"><b>NAPOVED</b></p>
      <p style="text-align: center"><b>
        Na testu, ki traja 45 min,<br>boš rešil(a) od {Math.round(45 / (pravilni_mean + pravilni_mean_err) * 10) / 10} do
        <br>{Math.round(45 / (pravilni_mean - pravilni_mean_err) * 10) / 10} naloge!
        V povprečju { Math.round(45 / (pravilni_mean) * 10) / 10} nalog.
      </b></p>
    </Content>
    <Actions>
      <Button variant="raised" style="width: 100%" on:click={() => (open= false)}>
        <Label>Zapri</Label>
      </Button>
    </Actions>
  </Dialog>
</div>
