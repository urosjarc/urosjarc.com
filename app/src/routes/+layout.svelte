<script>
  import {fade} from 'svelte/transition'
  import {cubicIn, cubicOut} from 'svelte/easing'
  import Fab from '@smui/fab';
  import {Icon} from '@smui/common';

  export let data;
  const duration = 500;
  const delay = duration + 250;
  const transitionIn = { easing: cubicOut, duration, delay};
  const transitionOut = { easing: cubicIn, duration};
</script>

<div id="app">

  <table width="100%">
    <tr>
      <td width="25%"></td>
      <td id="center">

        <table width="100%">
          <tr>
            <td>
              <Fab href="/">
                <Icon class="material-icons">home</Icon>
              </Fab>
              <p><b>Domov</b></p>
            </td>
            <td>
              <Fab href="/koledar">
                <Icon class="material-icons">event</Icon>
              </Fab>
              <p><b>Koledar</b></p>
            </td>
            <td>
              <Fab href="/kontakt">
                <Icon class="material-icons">email</Icon>
              </Fab>
              <p><b>Kontakt</b></p>
            </td>
            <td>
              <Fab href="/ucenci">
                <Icon class="material-icons">person</Icon>
              </Fab>
              <p><b>Učenci</b></p>
            </td>
          </tr>
        </table>
        {#key data.url}
          <div
            in:fade={transitionIn}
            out:fade={transitionOut}
          >
            <div id="vsebina">
              <slot/>
            </div>
          </div>
        {/key}

      </td>
      <td width="25%"></td>
    </tr>
  </table>
</div>


<style>
  #app {
    width: 100%;
  }

  #center {
    text-align: center;
    min-width: 330px;
  }

  #vsebina {
    background-color: white;
    border-radius: 5px;
    -webkit-box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
    -moz-box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
    box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
  }
</style>
