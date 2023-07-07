<script lang="ts">
  import {page} from "$app/stores";
  import {api} from "../../../../../stores/apiStore";
  import {profil} from "../../../../../stores/profilStore";
  import Button, {Label} from "@smui/button";
  import {route} from "../../../../../stores/routeStore";

  const test_id = $page.params.test_id
  const tematike = {}

  profil.get().test_refs.find((test_ref) => test_ref.test._id == test_id).status_refs.forEach((status_ref) => {
    const info = {
      naloga_id: status_ref.naloga_refs[0].naloga._id,
      tema: status_ref.naloga_refs[0].tematika_refs[0].naslov,
    }
    if (info.tema in tematike) tematike[info.tema].push(info)
    else{
      tematike[info.tema] = [info]
    }
  })

</script>

<div class="row">
  {#each Object.entries(tematike) as [tema, infos], i}
    <h1>{tema}</h1>
    {#each infos as info, i}
      {@const naloga_id = info.naloga_id}
      <div class="col-sm-2">
        {info.naloga_id}
        <Button style="margin: 12px" class="my-colored-button" variant="raised" href={route.profil_naloga_id(test_id, naloga_id)}>
          <Label>{i + 1}.</Label>
        </Button>
      </div>
    {/each}
  {/each}
</div>
