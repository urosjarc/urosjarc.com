<script lang="ts">
  import {api} from "../../stores/apiStore.js";
  import {goto} from "$app/navigation";

  api.auth.whois().then(data => console.log(data)).catch(() => goto("/prijava"))
</script>

<slot/>
