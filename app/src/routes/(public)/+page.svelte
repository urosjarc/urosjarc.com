<script lang="ts">
  import {Icon} from '@smui/common';
  import Button, {Group} from '@smui/button';
  import List, {Item, Separator} from '@smui/list';
  import {hacker} from "../../libs/hacker";
  import {onMount} from "svelte";
  import {route} from "../../stores/routeStore";

  onMount(() => hacker(0));
</script>

<div>
  <div id="glava">

    <h1 class="hacker">Uroš Jarc</h1>
    <h2 class="hacker">Inštruktor programiranja, fizike, matematike</h2>

    <Button href="{route.kontakt}">
      <Icon class="material-icons">email</Icon>
      <b class="hacker">jar.fmf@gmail.com</b>
    </Button>
    <Button href="{route.kontakt}">
      <Icon class="material-icons">phone</Icon>
      <b class="hacker">051-240-885</b>
    </Button>

    <Group style="display: flex; justify-content: stretch;margin:20px 0;">
      <Button variant="raised" href="{route.kontakt}" color="primary" style="flex-grow: 1;">
        <b class="hacker">Naroči inštrukcije</b>
      </Button>
    </Group>
  </div>

  <List nonInteractive>
    <Separator/>
    <Item>
      <p class="hacker">Izvajam kvalitetne inštrukcije programiranja, fizike, matematike.</p>
    </Item>
    <Separator/>
    <Item>
      <p class="hacker">Pripravljam študente vseh fakultet na izpite iz programiranja.</p>
    </Item>
    <Separator/>
    <Item>
      <p class="hacker">Pomagam pri programiranju diplomskih in mag. nalog.</p>
    </Item>
    <Separator/>
    <Item>
      <p class="hacker">Pomagam pri prekvalifikaciji za programersko službo</p>
    </Item>
  </List>

</div>


<style>

  :global(.smui-paper), :global(.mdc-deprecated-list), :global(.mdc-deprecated-list-item) {
    padding: 0 !important;
    padding-left: 10px;
    padding-right: 10px;
    text-align: center;
  }

  :global(.mdc-deprecated-list-item) {
    padding: 12px 0 !important;
  }

  #glava {
    padding-top: 5px !important;
    text-align: center;
  }

  p {
    text-align: center;
    width: 100%;
  }

  /*Gumb brez border radiusa!*/
  :global(.mdc-button) {
    border-radius: 0 !important;
  }

</style>
