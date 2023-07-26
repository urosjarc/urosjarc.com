<script lang="ts">
  import {page} from "$app/stores";
  import Button, {Group, Label} from '@smui/button';
  import {onMount} from "svelte";
  import DataTable, {Body, Cell, Row} from "@smui/data-table";
  import {page_audits, page_data} from "./page";
  import {Chart} from "chart.js/auto";
  import Dialog, {Content, Title} from "@smui/dialog";
  import {Separator} from "@smui/list";
  import Actions from "@smui/dialog/src/Actions";
  import {route} from "$lib/stores/routeStore";
  import {StatusTip_color} from "$lib/extends/StatusTip";
  import Textfield from "@smui/textfield";
  import Icon from "@smui/textfield/icon";
  import {String_vDate} from "$lib/extends/String";
  import {Date_datumStr, Date_ime_dneva, Date_oddaljenost_v_dneh} from "$lib/extends/Date";
  import {posodobi_datum_testa} from "$lib/usecases/posodobi_datum_testa";
  import {Date_dodaj} from "$lib/extends/Date.js";

  async function potrdi_dialog_datuma() {
    await posodobi_datum_testa({test_id: test_id, datum: datum})
    await update()
    dialog_datuma = false
  }

  function sprememba_datuma() {
    const date = String_vDate(datum)
    datum_verbose = [
      `<b>Datum</b>: ${Date_datumStr(date)}`,
      `<b>Čez</b>: ${Date_oddaljenost_v_dneh(date)} dni (${Date_ime_dneva(date)})`,
    ].join("<br>")
  }

  async function load_audits() {
    if (Object.keys(audits).length == 0) {
      audits = await page_audits({test_id: test_id})
      dialog_statistike = true
    } else {
      dialog_statistike = true
    }
  }

  async function update() {
    const pageData = await page_data({test_id: test_id})

    tema_naloge = pageData.tema_naloge
    status_stevilo = pageData.status_stevilo
    data = pageData.data
    datum = pageData.data.datum
    sprememba_datuma()
  }

  onMount(async () => {
    await update()

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
  let tema_naloge = new Map()
  let status_stevilo = new Map()
  let datum = ""
  let datum_verbose = ""
  let dialog_statistike = false
  let dialog_datuma = false

  function povecaj_datum(number: number) {
    let date = String_vDate(datum)
    date = Date_dodaj(date, number)
    datum = Date_datumStr(date, true)
    sprememba_datuma()
  }
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
          <Cell>
            <Button variant="raised" on:click={() => dialog_datuma = true}>{data.datum}</Button>
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Rok:</b></Cell>
          <Cell>{data.rok} dni ({data.dan})</Cell>
        </Row>
        <Row>
          <Cell numeric><b>Reseno:</b></Cell>
          <Cell>
            {data.opravljene_naloge}/{data.vse_naloge}
            ({Math.round(data.opravljene_naloge / data.vse_naloge * 100)} %)
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Manjka:</b></Cell>
          <Cell>{data.manjkajoce_naloge}/{data.vse_naloge}
            ({Math.round(data.manjkajoce_naloge / data.vse_naloge * 100)}
            %)
          </Cell>
        </Row>
        <Row>
          <Cell numeric><b>Delo:</b></Cell>
          <Cell>{Math.ceil(data.manjkajoce_naloge / (data.rok > 1 ? (data.rok - 1) : data.rok))} nal/dan</Cell>
        </Row>
        </Body>
      </DataTable>
    </div>
  </div>

  {#each [...tema_naloge] as [tema, nalogaInfos], i}
    <h1 style="text-align: center; margin: 30px 0 0 0">{tema}</h1>
    {#each nalogaInfos as nalogaInfo, i}
      <Button
        class="col-1 {nalogaInfo.cls}"
        style="margin: 2px" variant="raised"
        href={route.ucenec_status_id(test_id, nalogaInfo.id)}>
        <Label><b>{i + 1}</b></Label>
      </Button>
    {/each}
  {/each}


  <Dialog bind:open={dialog_statistike}>
    <Content>

      <p style="text-align: center"><b>VSE NALOGE</b></p>
      <Separator/>
      <ul>
        <li>Število reševanj: {audits.stevilo_vseh} nalog</li>
        <li>Skupno: {audits.trajanje_vseh_min} min ({audits.trajanje_vseh_ur} h)</li>
        <li>Povprečje: ({audits.trajanje_vseh_povprecje_min} +- {audits.trajanje_vseh_napaka_min}) min/nal</li>
      </ul>

      <p style="text-align: center"><b>PRAVILNE NALOGE</b></p>
      <Separator/>
      <ul>
        <li>Stevilo reševanj: {audits.stevilo_pravilnih} nalog</li>
        <li>Skupno: {audits.trajanje_pravilnih_min} min ({audits.trajanje_pravilnih_ur} h)</li>
        <li>Povprečje: ({audits.trajanje_pravilnih_povprecje_min} +- {audits.trajanje_pravilnih_napaka_min}) min/nal
        </li>
      </ul>

      <Separator/>
      <p style="text-align: center"><b>NAPOVED</b></p>
      <p style="text-align: center"><b>
        Na testu, ki traja 45 min,<br>boš rešil(a)
        od {audits.min_resevanje_testa} do <br>{audits.max_resevanje_testa} naloge!
        V povprečju {audits.resevanje_testa} nalog.
      </b></p>
    </Content>
    <Actions>

      <Button variant="raised" class="razsiri" on:click={() => (dialog_statistike = false)}>
        <Label>Zapri</Label>
      </Button>
    </Actions>
  </Dialog>

  <Dialog bind:open={dialog_datuma}>
    <Title style="min-width: 400px">
      {@html datum_verbose}
    </Title>
    <Content>
      <Textfield variant="outlined" required class="razsiri mt-3" bind:value={datum} on:input={sprememba_datuma}
                 label="Datum YYYY-MM-DD">
        <Icon class="material-icons" slot="leadingIcon">event</Icon>
      </Textfield>
      <Group>
        <Button on:click={() => povecaj_datum(-1)}>-1dan</Button>
        <Button on:click={() => povecaj_datum(+1)}>+1dan</Button>
      </Group>
      <br/>
    </Content>
    <Actions>
      <Group class="razsiri">
        <Button variant="raised" class="razsiri col-red" on:click={() => dialog_datuma = false}>
          <b>Prekliči</b>
        </Button>
        <Button variant="raised" class="razsiri col-forestgreen" on:click={potrdi_dialog_datuma}>
          <b>Potrdi</b>
        </Button>
      </Group>
    </Actions>
  </Dialog>
</div>
