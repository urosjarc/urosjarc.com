<script>
  import {fade} from 'svelte/transition'
  import {cubicIn, cubicOut} from 'svelte/easing'
  import Fab from '@smui/fab';
  import {Icon} from '@smui/common';
  import OnMount from "../libs/OnMount.svelte";

  export let data = {url: null};
  const duration = 500;
  const delay = duration + 250;
  const transitionIn = {easing: cubicOut, duration, delay};
  const transitionOut = {easing: cubicIn, duration};
  const transitionInStart = {easing: cubicIn, duration: duration};

</script>

<div id="app" class="container">

  <OnMount>
    <div class="row justify-content-center" style="margin: 0"  in:fade={transitionInStart}>
      <div class="col-12 col-sm-11 col-lg-6" style="padding: 0">

        <div id="navigacija" class="row">
          <div class="col-3">
            <Fab href="/">
              <Icon class="material-icons">home</Icon>
              <p class="link">Domov</p>
            </Fab>
            <p><b>Domov</b></p>
          </div>
          <div class="col-3">
            <Fab href="/koledar">
              <Icon class="material-icons">event</Icon>
              <p class="link">Koledar</p>
            </Fab>
            <p><b>Koledar</b></p>
          </div>
          <div class="col-3">
            <Fab href="/kontakt">
              <Icon class="material-icons">email</Icon>
              <p class="link">Kontakt</p>
            </Fab>
            <p><b>Kontakt</b></p>
          </div>
          <div class="col-3">
            <Fab href="/ucenci">
              <Icon class="material-icons">person</Icon>
              <p class="link">Učenci</p>
            </Fab>
            <p><b>Učenci</b></p>
          </div>
        </div>

        {#key data.url}
          <div id="vsebina" in:fade={transitionIn} out:fade={transitionOut}>
            <slot/>
          </div>
        {/key}

      </div>
    </div>
  </OnMount>
</div>


<style>
  #app, #vsebina, #navigacija div {
    padding: 0;
  }

  #navigacija {
    text-align: center;
    margin: 0;
  }

  .link {
    font-size: 0 !important;
  }


  #vsebina {
    opacity: 0.85;
    background-color: white;
    border-radius: 5px;
    -webkit-box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
    -moz-box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
    box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
  }
</style>
