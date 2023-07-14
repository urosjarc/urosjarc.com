<script lang="ts">
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import DataTable, {Body, Cell, Head, Row} from "@smui/data-table"
  import {profil} from "../../../stores/profilStore";
  import {onMount} from "svelte";
  import {dateDistance, dateFormat, dateName} from "../../../libs/utils.js";
  import {barva_testa} from "../../../libs/stili";

  let testi_refs = []

  function goto_test(id) {
    goto(route.profil_test_id(id))
  }

  onMount(() => {
    testi_refs = profil.get().test_refs
  })

</script>

<div>
  <DataTable class="razsiri">
    <Head>
      <Row>
        <Cell><b>Naslov</b></Cell>
        <Cell numeric><p>Rešeno</p></Cell>
        <Cell><b>Ostalo</b></Cell>
        <Cell><b>Deadline</b></Cell>
      </Row>
    </Head>
    <Body>
    {#each testi_refs as test_ref}
      {@const test = test_ref.test}
      {@const deadline = dateDistance(test.deadline)}
      {@const opravljeno = core.opravljeno(test_ref)}
      <Row on:click={() => goto_test(test._id)} style="cursor: pointer">
        <Cell class="{barva_testa(deadline)}">{test.naslov}</Cell>
        <Cell class="{barva_testa(deadline)}" numeric>{Math.round(opravljeno * 100)}%</Cell>
        <Cell class="{barva_testa(deadline)}"><b>{deadline} dni</b></Cell>
        <Cell class="{barva_testa(deadline)}">{dateFormat(test.deadline)} ({dateName(test.deadline)})</Cell>
      </Row>
    {/each}
    </Body>

  </DataTable>
</div>
