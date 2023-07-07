<script lang="ts">
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import DataTable, {Body, Head, Row, Cell} from "@smui/data-table"
  import {profil} from "../../../stores/profilStore";

  function goto_test(id) {
    goto(route.test(id))
  }

  const testi_refs = profil.get().test_refs

</script>

<div>
  <DataTable table$aria-label="User list" style="width: 100%">
    <Head>
      <Row>
        <Cell numeric>Opravljeno</Cell>
        <Cell>Naslov</Cell>
        <Cell>Deadline</Cell>
      </Row>
    </Head>
    <Body>
    {#each testi_refs as test_ref, i}
      {@const test = test_ref.test}
      <Row on:click={() => goto_test(test._id)} style="cursor: pointer">
        <Cell numeric>
          {test_ref.status_refs.length}%
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
