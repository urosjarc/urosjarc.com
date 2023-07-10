<script lang="ts">
  import {page} from "$app/stores";
  import {profil} from "../../../../../stores/profilStore";
  import {route} from "../../../../../stores/routeStore";
  import Button, {Label} from '@smui/button';
  import {onMount} from "svelte";
  import Chart from "chart.js/auto";

  const test_id = $page.params.test_id
  const tematike = {}
  const statusi = {}
  let test = {};
  let stevilo_statusov = 0

  onMount(() => {
    const testRef = profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id)
    test = testRef.test
    testRef.status_refs.forEach((status_ref) => {
      const info = {
        naloga_id: status_ref.naloga_refs[0].naloga._id,
        tema: status_ref.naloga_refs[0].tematika_refs[0].naslov,
        tip: status_ref.status.tip || "OSTALO"
      }
      if (info.tema in tematike) tematike[info.tema].push(info)
      else tematike[info.tema] = [info]

      if (info.tip in statusi) statusi[info.tip] += 1
      else statusi[info.tip] = 1
      stevilo_statusov += 1
    })

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
            position: 'right',
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
        return "gray"
    }
  }
</script>

<div class="row justify-content-center" style="padding: 0 12px 30px 12px">
  <div style="text-align: center">
    <h1>{test.naslov}</h1>
    <h2>{test.podnaslov}</h2>
    <h2>({stevilo_statusov} nalog)</h2>
  </div>
  <canvas class="col-7" id="chart"></canvas>

  {#each Object.entries(tematike) as [tema, infos], i}
    <h1 style="text-align: center; margin: 30px 0 0 0">{tema}</h1>
    {#each infos as info, i}
      {@const naloga_id = info.naloga_id}
      <Button class="col-1 {barva_statusa(info.tip)}" style="margin: 2px" variant="raised" href={route.profil_naloga_id(test_id, naloga_id)}>
        <Label>{i + 1}</Label>
      </Button>
    {/each}
  {/each}
</div>
