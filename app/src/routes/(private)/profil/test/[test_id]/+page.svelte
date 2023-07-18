<script lang="ts">
  import {page} from "$app/stores";
  import Button, {Label} from '@smui/button';
  import {onMount} from "svelte";
  import DataTable, {Body, Cell, Row} from "@smui/data-table";
  import type {Data} from "./data";
  import {data} from "./data";
  import {Chart} from "chart.js/auto";
  import {audits} from "./audits";
  import type {AuditsData} from "./audits";
  import Dialog, {Content} from "@smui/dialog";
  import {Separator} from "@smui/list";
  import Actions from "@smui/dialog/src/Actions";
  import Alerts from "$lib/components/Alerts.svelte";
  import {route} from "$lib/stores/routeStore";
  import {Number_zavkrozi} from "$lib/extends/Number";
  import {StatusTip_color} from "$lib/extends/StatusTip";


  function load_audits() {
    if(Object.keys(_audits).length == 0){
      audits({
        test_id: test_id,
        error(err: any): void {
          error = err
        },
        fatal(err: any): void {
          fatal = err
        },
        warn(err: any): void {
          warn = err
        },
        uspeh(data: AuditsData) {
          open = true
          _audits = data
        }
      })
    } else {
      open = true
    }
  }

  onMount(() => {
    data({
      test_id: test_id,
      error(err: any): void {
        error = err
      },
      fatal(err: any): void {
        fatal = err
      },
      warn(err: any): void {
        warn = err
      },
      uspeh(data: Data) {
        _data = data
        tema_statusi = data.tema_statusi
        status_stevilo = data.status_stevilo
      }
    })

    const options = {
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
    }
    console.debug(options)

    //@ts-ignore
    new Chart("chart", options);
  })

  const test_id = $page.params.test_id
  //@ts-ignore
  let _data: Data = {}
  //@ts-ignore
  let _audits: AuditsData = {}
  let open = false
  let tema_statusi = new Map()
  let status_stevilo = new Map()
  let error = ""
  let fatal = ""
  let warn = ""

</script>

<div class="row justify-content-center pb-3" style="margin: 0">

  <Alerts bind:fatal={fatal} bind:error={error} bind:warn={warn}/>

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
            ({Math.round(_data.manjkajoci_statusi / _data.vsi_statusi * 100)}
            %)
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Delo:</b></Cell>
          <Cell>{Math.ceil(_data.manjkajoci_statusi / (_data.rok - 1))} nal/dan</Cell>
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
        <li>Število reševanj: {_audits.stevilo_vseh} nalog</li>
        <li>Skupno: {_audits.trajanje_vseh_min} min ({Number_zavkrozi(_audits.trajanje_vseh_min / 60, 2)} h)</li>
        <li>Povprečje: ({_audits.trajanje_vseh_povprecje_min} +- {_audits.trajanje_vseh_napaka_min}) min/nal</li>
      </ul>

      <p style="text-align: center"><b>PRAVILNE NALOGE</b></p>
      <Separator/>
      <ul>
        <li>Stevilo reševanj: {_audits.stevilo_pravilnih} nalog</li>
        <li>Skupno: {_audits.trajanje_pravilnih_min} min ({Number_zavkrozi(_audits.trajanje_pravilnih_min / 60, 2)} h)</li>
        <li>Povprečje: ({_audits.trajanje_pravilnih_povprecje_min} +- {_audits.trajanje_pravilnih_napaka_min}) min/nal</li>
      </ul>

      <Separator/>
      <p style="text-align: center"><b>NAPOVED</b></p>
      <p style="text-align: center"><b>
        Na testu, ki traja 45 min,<br>boš rešil(a)
        od {Number_zavkrozi(45 / (_audits.trajanje_pravilnih_povprecje_min + _audits.trajanje_pravilnih_napaka_min), 2)}
        do <br>{Number_zavkrozi(45 / (_audits.trajanje_pravilnih_povprecje_min - _audits.trajanje_pravilnih_napaka_min), 2)} naloge!
        V povprečju { Number_zavkrozi(45 / _audits.trajanje_pravilnih_povprecje_min, 2)} nalog.
      </b></p>
    </Content>
    <Actions>
      <Button variant="raised" style="width: 100%" on:click={() => (open= false)}>
        <Label>Zapri</Label>
      </Button>
    </Actions>
  </Dialog>
</div>
