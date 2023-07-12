<script lang="ts">
  import Button, {Group} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import {usecase} from "../../../stores/usecaseStore";
  import {onMount} from "svelte";
  import LinearProgress from '@smui/linear-progress';

  let response: Object = {}
  let loading = false;
  let username = "";
  let geslo = "";

  function poslji() {
    if(username.length * geslo.length > 0){
      loading = true
      usecase.prijava_v_profil(username)
    }
  }

  onMount(usecase.prijavljen_v_profil)

</script>

<form class="row justify-content-center">
  <LinearProgress indeterminate={loading}/>
  <div class="col-6">
    <Textfield bind:value={username} label="Uporabniško ime" style="width: 100%" required>
      <Icon class="material-icons" slot="leadingIcon">person</Icon>
    </Textfield>
  </div>
  <div class="col-6">
    <Textfield bind:value={geslo} label="Geslo" style="width: 100%" required>
      <Icon class="material-icons" slot="leadingIcon">lock</Icon>
    </Textfield>
  </div>
  <Group on:click={poslji} style="display: flex; justify-content: stretch;">
    <Button variant="raised" color="primary" style="flex-grow: 1;">
      <b>Prijava</b>
    </Button>
  </Group>
</form>
