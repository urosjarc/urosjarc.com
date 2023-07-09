<script lang="ts">
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import DataTable, {Body, Head, Row, Cell} from "@smui/data-table"
  import {profil} from "../../../stores/profilStore";
  import {onMount} from "svelte";

  let testi_refs = []

  function goto_test(id) {
    goto(route.profil_test_id(id))
  }

  onMount(() => {
    testi_refs = profil.get().test_refs
  })

</script>

<div>
  <DataTable style="width: 100%">
    <Head>
      <Row>
        <Cell numeric><p class="naslov-stolpca">Opravljeno</p></Cell>
        <Cell><p class="naslov-stolpca">Naslov</p></Cell>
        <Cell><p class="naslov-stolpca">Deadline</p></Cell>
      </Row>
    </Head>
    <Body>
    {#each testi_refs as test_ref}
      {@const test = test_ref.test}
      <Row on:click={() => goto_test(test._id)} style="cursor: pointer">
        <Cell numeric>
          {test_ref.opravljeno || 0}%
        </Cell>
        <Cell>
          {test.naslov}
        </Cell>
        <Cell>
          {test.deadline}
        </Cell>
      </Row>
    {/each}
    </Body>

  </DataTable>
</div>

<style>
  .naslov-stolpca {
    font-weight: bolder;
  }
</style>
