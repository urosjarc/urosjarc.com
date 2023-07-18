<script lang="ts">
  import {page} from "$app/stores";
  import {route} from "../../../../../stores/routeStore";
  import Button, {Label} from '@smui/button';
  import {onMount} from "svelte";
  import DataTable, {Body, Cell, Row} from "@smui/data-table";
  import type {Data} from "./data";
  import {data} from "./data";
  import Alerts from "../../../../../components/Alerts.svelte";


  function show_dialog() {
  //   API().getProfilTestAudits(test_id).then(audits => {
  //     for (let audit of audits) {
  //       let min = 1
  //       trajanje.push(min)
  //       if (audit.opis == Status.tip.PRAVILNO) {
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
      uspeh(data: Data): void {
        _data = data
        tema_statusi = data.tema_statusi
        status_stevilo = data.status_stevilo
      },
      test_id: test_id
    })
  })

    //   new Chart("chart", {
    //     type: "pie",
    //     data: {
    //       labels: Object.keys(statusi),
    //       datasets: [{
    //         backgroundColor: Object.keys(statusi).map((v) => "red"),
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

    const test_id = $page.params.test_id
    let _data: Data = {}
    let tema_statusi = {}
    let status_stevilo = {}
    let error = ""
    let fatal = ""
    let warn = ""

</script>

<div class="row justify-content-center pb-3" style="margin: 0">

  <Alerts bind:fatal={fatal} bind:error={error} bind:warn={warn}/>

  <Button style="width: 100%; border-radius: 0" variant="raised" color="primary" on:click={show_dialog}>
    <b>Pokaži časovno statistiko</b>
  </Button>
  <div class="row justify-content-evenly align-items-center p-2"
       style="border-style: solid; border-width: 1px; border-color: lightgray">
    <canvas class="col-4 p-0" id="chart"></canvas>
    <div class="col p-2">
      <DataTable class="senca-mala" style="width: 100%">
        <Body>
        <Row>
          <Cell numeric><b>Datum:</b></Cell>
          <Cell>{_data.datum}</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Rok:</b></Cell>
          <Cell>{_data.rok} dni ({_data.dan})</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Reseno:</b></Cell>
          <Cell>
            {_data.opravljeni_statusi}/{_data.vsi_statusi}
            ({Math.round(_data.opravljeni_statusi / _data.vsi_statusi * 100)} %)
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Manjka:</b></Cell>
          <Cell>{_data.manjkajoci_statusi}/{_data.vsi_statusi}
            ({100 - Math.round(_data.manjkajoci_statusi / _data.vsi_statusi * 100)}
            %)
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Delo:</b></Cell>
          <Cell>{Math.ceil(_data.manjkajoci_statusi / (_data.dni - 1))} nal/dan</Cell>
        </Row>
        </Body>
      </DataTable>
    </div>
  </div>

  {#each Object.entries(tema_statusi) as [tema, statusDatas], i}
    <h1 style="text-align: center; margin: 30px 0 0 0">{tema}</h1>
    {#each statusDatas as statusData, i}
      {@const status_id = statusData.status._id}
      <Button class="col-1 red" style="margin: 2px" variant="raised" href={route.profil_status_id(test_id, status_id)}>
        <Label><b>{i + 1}</b></Label>
      </Button>
    {/each}
  {/each}


  <!--  <Dialog bind:open>-->
  <!--    <Content>-->
  <!--      <p style="text-align: center"><b>VSE NALOGE</b></p>-->
  <!--      <Separator/>-->
  <!--      <ul>-->
  <!--        <li>Število reševanj: {st_trajanje} nalog</li>-->
  <!--        <li>Skupno: {min_trajanje} min ({Math.round(min_trajanje / 60 * 100) / 100} h)</li>-->
  <!--        <li>Povprečje: {mean} +- {mean_err} min/nal</li>-->
  <!--      </ul>-->
  <!--      <p style="text-align: center"><b>PRAVILNE NALOGE</b></p>-->
  <!--      <Separator/>-->
  <!--      <ul>-->
  <!--        <li>Stevilo reševanj: {st_trajanje_pravilnih} nalog</li>-->
  <!--        <li>Skupno: {min_trajanje_pravilnih} min ({Math.round(min_trajanje_pravilnih / 60 * 100) / 100} h)</li>-->
  <!--        <li>Povprečje: {pravilni_mean} +- {pravilni_mean_err} min/nal</li>-->
  <!--      </ul>-->
  <!--      <Separator/>-->
  <!--      <p style="text-align: center"><b>NAPOVED</b></p>-->
  <!--      <p style="text-align: center"><b>-->
  <!--        Na testu, ki traja 45 min,<br>boš rešil(a) od {Math.round(45 / (pravilni_mean + pravilni_mean_err) * 10) / 10}-->
  <!--        do-->
  <!--        <br>{Math.round(45 / (pravilni_mean - pravilni_mean_err) * 10) / 10} naloge!-->
  <!--        V povprečju { Math.round(45 / (pravilni_mean) * 10) / 10} nalog.-->
  <!--      </b></p>-->
  <!--    </Content>-->
  <!--    <Actions>-->
  <!--      <Button variant="raised" style="width: 100%" on:click={() => (open= false)}>-->
  <!--        <Label>Zapri</Label>-->
  <!--      </Button>-->
  <!--    </Actions>-->
  <!--  </Dialog>-->
</div>
