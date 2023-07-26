<script lang="ts">
  import Button from "@smui/button";
  import CircularProgress from "@smui/circular-progress";
  export let loading = false
</script>

<Button class="razsiri" variant="raised" type="submit" disabled={loading}>
  {#if loading}
    <CircularProgress style="width: 25px; height: 25px" indeterminate/>
  {:else}
    <b><slot/></b>
  {/if}
</Button>
