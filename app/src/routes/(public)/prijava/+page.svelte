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

  function poslji(){
    loading = true
    usecase.prijava_v_profil(username)
  }

  onMount(usecase.prijavljen_v_profil)

</script>

<form class="row">
  <div class="col">
    <LinearProgress indeterminate={loading}/>
    <Textfield
      style="width: 100%;margin-bottom: 10px;"
      helperLine$style="width: 100%;"
      bind:value={username} label="Uporabnik">
      <Icon class="material-icons" slot="leadingIcon">person</Icon>
    </Textfield>
    <Group on:click={poslji} style="display: flex; justify-content: stretch;">
      <Button variant="raised" color="primary" style="flex-grow: 1;">
        <b>Prijava</b>
      </Button>
    </Group>
  </div>
</form>
