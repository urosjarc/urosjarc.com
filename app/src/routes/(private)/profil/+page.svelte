<script lang="ts">
  import {token} from "../../../stores/tokenStore";
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import DataTable, {Body, Head, Row, Cell} from "@smui/data-table"
  import LinearProgress from '@smui/linear-progress';
  import {api} from "../../../stores/apiStore";
  import type {core} from "../../../types/core";
  import OsebaData = core.data.OsebaData;

  function logout() {
    token.clear()
    goto(route.prijava)
  }

  function test_call(test_id) {
    console.log(test_id)
  }

  let testi_refs_promise = api.profil.index().then((osebaData: OsebaData) => osebaData.test_refs)

</script>

<div>
  <DataTable table$aria-label="User list" style="width: 100%; text-align: center">
    <Head>
      <Row>
        <Cell>Naslov</Cell>
        <Cell numeric>Opravljeno</Cell>
        <Cell>Deadline</Cell>
      </Row>
    </Head>
    <Body>
    {#await testi_refs_promise}
      <LinearProgress
        style="width: 100%"
        indeterminate
        aria-label="Data is being loaded..."
        slot="progress"
      />
    {:then testi_refs}
      {#each testi_refs as test_ref, i}
        {@const test = test_ref.test}
        <Row on:click={() => test_call(test._id)}>
          <Cell>
            {test.naslov}
          </Cell>
          <Cell numeric>
            {test_ref.status_refs.length}
          </Cell>
          <Cell>
            {test.deadline}
          </Cell>
        </Row>
      {/each}
    {:catch error}
      {error}
    {/await}
    </Body>

  </DataTable>
</div>
