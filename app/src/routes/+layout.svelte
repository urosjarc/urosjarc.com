<script>
  import {fade} from 'svelte/transition'
  import {cubicIn, cubicOut} from 'svelte/easing'
  import Fab from '@smui/fab';
  import {Icon} from '@smui/common';

  export let data;
  const duration = 500;
  const delay = duration + 250;
  const transitionIn = {easing: cubicOut, duration, delay};
  const transitionOut = {easing: cubicIn, duration};
</script>

<div id="app" class="container">

  <div class="row justify-content-center" style="margin: 0">
    <div class="col-12 col-sm-11 col-lg-6" style="padding: 0">

      <div id="navigacija" class="row">
        <div class="col-3">
          <Fab href="/">
            <Icon class="material-icons">home</Icon>
          </Fab>
          <p><b>Domov</b></p>
        </div>
        <div class="col-3">
          <Fab href="/koledar">
            <Icon class="material-icons">event</Icon>
          </Fab>
          <p><b>Koledar</b></p>
        </div>
        <div class="col-3">
          <Fab href="/kontakt">
            <Icon class="material-icons">email</Icon>
          </Fab>
          <p><b>Kontakt</b></p>
        </div>
        <div class="col-3">
          <Fab href="/ucenci">
            <Icon class="material-icons">person</Icon>
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
</div>


<style>
  #app, #vsebina, #navigacija div {
    padding: 0;
  }

  #navigacija {
    text-align: center;
    margin: 0;
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
