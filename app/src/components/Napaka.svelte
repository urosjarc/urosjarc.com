<script lang="ts">
  import Dialog, {Content, Title} from "@smui/dialog";
  import Button, {Label} from "@smui/button";

  function zapri() {
    open = false
    napaka = ""
  }

  export let open = false
  export let napaka: string = ""
</script>

<Dialog bind:open>
  <Title style="text-align: center">NAPAKA</Title>
  <Content style="text-align: center">{napaka}</Content>
  <Button class="razsiri" on:click={zapri}>
    <Label>Zapri</Label>
  </Button>
</Dialog>
