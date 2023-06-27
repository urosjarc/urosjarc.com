<script lang="ts">
  import {usecase} from "../../stores/usecaseStore";
  import {onMount} from "svelte";

  onMount(usecase.neprijavljen_v_prijavo)
</script>

<slot/>
