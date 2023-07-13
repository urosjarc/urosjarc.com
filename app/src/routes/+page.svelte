<script>
  import {Icon} from '@smui/common';
  import Button, {Group} from '@smui/button';
  import {Separator} from '@smui/list';
  import {hacker} from "../libs/hacker";
  import {onMount} from "svelte";
  import {route} from "../stores/routeStore";

  onMount(() => hacker(0));
</script>

<div>
  <div style="padding-top: 5px !important; text-align: center;">

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

    <Group style="display: flex; justify-content: stretch;margin:20px 0 0 0;">
      <Button variant="raised" href="{route.kontakt}" color="primary" style="flex-grow: 1; border-radius: 0">
        <b class="hacker">Naroči inštrukcije</b>
      </Button>
    </Group>
  </div>

  <Separator/>
  <p class="hacker">Izvajam kvalitetne inštrukcije programiranja, fizike, matematike.</p>
  <Separator/>
  <p class="hacker">Pripravljam študente vseh fakultet na izpite iz programiranja.</p>
  <Separator/>
  <p class="hacker">Pomagam pri programiranju diplomskih in mag. nalog.</p>
  <Separator/>
  <p class="hacker">Pomagam pri prekvalifikaciji za programersko službo</p>
  <Separator/>

</div>


<style>
  .hacker {
    text-align: center;
    width: 100%;
  }

</style>
