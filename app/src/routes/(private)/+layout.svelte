<script lang="ts">
  import {usecase} from "../../stores/usecaseStore";
  import Layout from "../../components/Layout.svelte";
  import Navigation from "../../components/Navigation.svelte";
  import {route} from "../../stores/routeStore";
  import {onMount} from "svelte";

  onMount(usecase.neprijavljen_v_prijavo)
</script>

<slot/>
