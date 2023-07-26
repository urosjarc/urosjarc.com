<script>
  import {Icon} from '@smui/common';
  import Button from '@smui/button';
  import {Separator} from '@smui/list';
  import {onMount} from "svelte";
  import {route} from "$lib/stores/routeStore";
  import {hacker} from "$lib/hacker";

  onMount(hacker);
</script>

<div>
  <div style="padding-top: 5px; text-align: center">

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

    <Button style="margin-top: 15px" class="razsiri-gumb" variant="raised" href="{route.kontakt}">
      <b class="hacker razsiri-gumb">Naroči inštrukcije</b>
    </Button>

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
