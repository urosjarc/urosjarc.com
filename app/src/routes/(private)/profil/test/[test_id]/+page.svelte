<script lang="ts">
  import {page} from "$app/stores";
  import {profil} from "../../../../../stores/profilStore";
  import {route} from "../../../../../stores/routeStore";
  import Button, {Label} from '@smui/button';
  import {onMount} from "svelte";
  import {goto} from "$app/navigation";

  let route_naloge: String;
  const test_id = $page.params.test_id
  const tematike = {}
  let open = false;
  let response = 'Nothing yet.';

  onMount(() => {
    profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id).status_refs.forEach((status_ref) => {
      const info = {
        naloga_id: status_ref.naloga_refs[0].naloga._id,
        tema: status_ref.naloga_refs[0].tematika_refs[0].naslov,
      }
      if (info.tema in tematike) tematike[info.tema].push(info)
      else {
        tematike[info.tema] = [info]
      }
    })
  })

  function odpri_dialog(url) {
    route_naloge = url
    open = true
  }

  function zapri_dialog(url) {
    url = ""
    open = false
  }

  function odpri_nalogo() {
    goto(route_naloge)
  }

</script>

<div class="row justify-content-center" style="padding: 0 12px 30px 12px">
  {#each Object.entries(tematike) as [tema, infos], i}
    <h1 style="text-align: center; margin: 30px 0 0 0">{tema}</h1>
    {#each infos as info, i}
      {@const naloga_id = info.naloga_id}
      <Button class="col-1" style="margin: 2px" variant="raised" href={route.profil_naloga_id(test_id, naloga_id)}>
        <Label>{i + 1}</Label>
      </Button>
    {/each}
  {/each}
</div>
