<script lang="ts">
  import {page} from "$app/stores";
  import Button, {Label} from '@smui/button';
  import {onMount} from "svelte";
  import DataTable, {Body, Cell, Row} from "@smui/data-table";
  import {page_audits, page_data} from "./page";
  import {Chart} from "chart.js/auto";
  import Dialog, {Content} from "@smui/dialog";
  import {Separator} from "@smui/list";
  import Actions from "@smui/dialog/src/Actions";
  import {route} from "$lib/stores/routeStore";
  import {Number_zavkrozi} from "$lib/extends/Number";
  import {StatusTip_color} from "$lib/extends/StatusTip";

  async function load_audits() {
    if (Object.keys(audits).length == 0) {
      audits = await page_audits({test_id: test_id})
      open = true
    }
  }

  onMount(async () => {
    ({tema_statusi, status_stevilo, data} = await page_data({test_id: test_id}))

    //@ts-ignore
    new Chart("chart", {
      type: "pie",
      data: {
        labels: [...status_stevilo.keys()],
        datasets: [{
          backgroundColor: [...status_stevilo.keys()].map((v) => StatusTip_color(v)),
          data: [...status_stevilo.values()]
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

  const test_id = $page.params.test_id
  let data = {}
  let audits = {}
  let open = false
  let tema_statusi = new Map()
  let status_stevilo = new Map()
</script>

<div class="row justify-content-center pb-3" style="margin: 0">
  <Button style="width: 100%; border-radius: 0" variant="raised" color="primary" on:click={load_audits}>
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
          <Cell>{data.datum}</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Rok:</b></Cell>
          <Cell>{data.rok} dni ({data.dan})</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Reseno:</b></Cell>
          <Cell>
            {data.opravljeni_statusi}/{data.vsi_statusi}
            ({Math.round(data.opravljeni_statusi / data.vsi_statusi * 100)} %)
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Manjka:</b></Cell>
          <Cell>{data.manjkajoci_statusi}/{data.vsi_statusi}
            ({Math.round(data.manjkajoci_statusi / data.vsi_statusi * 100)}
            %)
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Delo:</b></Cell>
          <Cell>{Math.ceil(data.manjkajoci_statusi / (data.rok - 1))} nal/dan</Cell>
        </Row>
        </Body>
      </DataTable>
    </div>
  </div>

  {#each [...tema_statusi] as [tema, statusDatas], i}
    <h1 style="text-align: center; margin: 30px 0 0 0">{tema}</h1>
    {#each statusDatas as statusData, i}
      <Button
        class="col-1 {statusData.cls}"
        style="margin: 2px" variant="raised"
        href={route.profil_status_id(test_id, statusData.id)}>
        <Label><b>{i + 1}</b></Label>
      </Button>
    {/each}
  {/each}


  <Dialog bind:open>
    <Content>

      <p style="text-align: center"><b>VSE NALOGE</b></p>
      <Separator/>
      <ul>
        <li>Število reševanj: {audits.stevilo_vseh} nalog</li>
        <li>Skupno: {audits.trajanje_vseh_min} min ({Number_zavkrozi(audits.trajanje_vseh_min / 60, 2)} h)</li>
        <li>Povprečje: ({audits.trajanje_vseh_povprecje_min} +- {audits.trajanje_vseh_napaka_min}) min/nal</li>
      </ul>

      <p style="text-align: center"><b>PRAVILNE NALOGE</b></p>
      <Separator/>
      <ul>
        <li>Stevilo reševanj: {audits.stevilo_pravilnih} nalog</li>
        <li>Skupno: {audits.trajanje_pravilnih_min} min ({Number_zavkrozi(audits.trajanje_pravilnih_min / 60, 2)} h)</li>
        <li>Povprečje: ({audits.trajanje_pravilnih_povprecje_min} +- {audits.trajanje_pravilnih_napaka_min}) min/nal</li>
      </ul>

      <Separator/>
      <p style="text-align: center"><b>NAPOVED</b></p>
      <p style="text-align: center"><b>
        Na testu, ki traja 45 min,<br>boš rešil(a)
        od {Number_zavkrozi(45 / (audits.trajanje_pravilnih_povprecje_min + audits.trajanje_pravilnih_napaka_min), 2)}
        do <br>{Number_zavkrozi(45 / (audits.trajanje_pravilnih_povprecje_min - audits.trajanje_pravilnih_napaka_min), 2)} naloge!
        V povprečju { Number_zavkrozi(45 / audits.trajanje_pravilnih_povprecje_min, 2)} nalog.
      </b></p>
    </Content>
    <Actions>
      <Button variant="raised" style="width: 100%" on:click={() => (open= false)}>
        <Label>Zapri</Label>
      </Button>
    </Actions>
  </Dialog>
</div>
