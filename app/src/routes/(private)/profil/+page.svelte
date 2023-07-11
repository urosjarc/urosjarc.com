<script lang="ts">
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import DataTable, {Body, Head, Row, Cell} from "@smui/data-table"
  import {profil} from "../../../stores/profilStore";
  import {onMount} from "svelte";
  import {dateDistance, dateFormat, dateName} from "../../../libs/utils.js";

  let testi_refs = []

  function goto_test(id) {
    goto(route.profil_test_id(id))
  }
  function barva_deadlina(deadline) {
    if(deadline > 7) return ""
    else if(deadline > 3) return "orange blink"
    else return "red blink"
  }
  onMount(() => {
    testi_refs = profil.get().test_refs
  })

</script>

<div>
  <DataTable style="width: 100%">
    <Head>
      <Row>
        <Cell><p class="naslov-stolpca">Naslov</p></Cell>
        <Cell numeric><p class="naslov-stolpca">Rešeno</p></Cell>
        <Cell><p class="naslov-stolpca">Ostalo</p></Cell>
        <Cell><p class="naslov-stolpca">Deadline</p></Cell>
      </Row>
    </Head>
    <Body>
    {#each testi_refs as test_ref}
      {@const test = test_ref.test}
      {@const deadline = dateDistance(test.deadline)}
      <Row class="{barva_deadlina(deadline)}" on:click={() => goto_test(test._id)} style="cursor: pointer">
        <Cell>{test.naslov}</Cell>
        <Cell numeric>{Math.round(test_ref.opravljeno*100)}%</Cell>
        <Cell><b>{deadline} dni</b></Cell>
        <Cell>{dateFormat(test.deadline)} ({dateName(test.deadline)})</Cell>
      </Row>
    {/each}
    </Body>

  </DataTable>
</div>

<style>
  :global(.mdc-data-table__cell) {
    color: inherit !important;
  }
  .naslov-stolpca {
    font-weight: bolder;
  }
</style>
