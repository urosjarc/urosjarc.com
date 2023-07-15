<script lang="ts">
  import Button, {Group} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import LinearProgress from '@smui/linear-progress';
  import {API} from "../../../stores/apiStore";
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import {token} from "../../../stores/tokenStore";
  import {profil} from "../../../stores/profilStore";
  import {prijavljen_v_profil} from "../../../usecases/prijavljen_v_profil";
  import type {OsebaData, Profil} from "../../../api";


  function prijava() {
    loading = true
    API().postAuthPrijava({username}).then(res => {
      if (res.token) {
        token.set(res.token)
        API().getProfil().then(osebaData => {
          profil.set(osebaData)
          goto(route.profil)
        }).catch(console.error)
      } else {
        console.error("Token ni bil poslan od serverja!")
      }
    }).catch(console.error)
  }

  let loading = false
  let username = ""
  let geslo = ""

  prijavljen_v_profil({
    tokenObstaja() {
      console.debug("Token obstaja!")
    },
    tokenNeObstaja() {
      console.debug("Token ne obstaja!")
    },
    getAuthWhois(authWhois: Profil){
      console.debug("Identiteta uporabnika se je preverila!")
    },
    getAuthWhoisErr(authWhois: Profil){
      console.debug("Identiteta se ni mogla preveriti!")
    },
    getProfil(osebaData: OsebaData) {
      console.debug("Profil podatki so se prenesli!")
    },
    getProfilErr(osebaData: OsebaData) {
      console.debug("Profil podatki se niso prenesli!")
    },
    uspeh() {
      console.debug("Login uspesen!")
    }
  })
</script>

<form class="row" on:submit|preventDefault={prijava}>

  <LinearProgress indeterminate={loading}/>

  <div class="col-12 col-md-6">
    <Textfield bind:value={username} label="Uporabniško ime" class="razsiri" required>
      <Icon class="material-icons" slot="leadingIcon">person</Icon>
    </Textfield>
  </div>

  <div class="col-12 col-md-6">
    <Textfield bind:value={geslo} label="Geslo" class="razsiri" required>
      <Icon class="material-icons" slot="leadingIcon">lock</Icon>
    </Textfield>
  </div>

  <Group>
    <Button variant="raised" class="razsiri">
      <b>Prijava</b>
    </Button>
  </Group>

</form>
