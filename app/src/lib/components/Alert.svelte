<script lang="ts">
  import Dialog, {Content, Title} from "@smui/dialog";
  import Button from "@smui/button";
  import {Separator} from "@smui/list";

  function zapri() {
    open = false
  }

  export let open = false
  export let cls = ""
</script>

<Dialog bind:open>
  <Title style="text-align: center">
    <b>
      <slot name="naslov"/>
    </b>
  </Title>

  <Separator/>

  <Content style="text-align: center; padding: 12px !important;">
    <slot name="vsebina"/>
    <Button class="razsiri {cls}" variant="raised" on:click={zapri}>
      <b>Zapri</b>
    </Button>
  </Content>
</Dialog>
