<script lang="ts">
  import {page} from "$app/stores";
  import {api} from "../../../../../stores/apiStore";

  let testi_refs_promise = api.profil.index().then((osebaData: OsebaData) => osebaData.test_refs)

</script>

<div>
  <h1>Hello test {$page.params.id}</h1>
</div>
