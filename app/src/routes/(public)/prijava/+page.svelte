<script lang="ts">
  import Button, {Group} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import LinearProgress from '@smui/linear-progress';
  import {usecase} from "../../../stores/usecaseStore";

  function prijava() {
    loading = true
    usecase.prijava_v_profil(username, geslo)
  }

  let loading = false
  let username = ""
  let geslo = ""
</script>

<form class="row justify-content-center">

  <LinearProgress indeterminate={loading}/>

  <div class="col-6">
    <Textfield bind:value={username} label="Uporabniško ime" class="razsiri" required>
      <Icon class="material-icons" slot="leadingIcon">person</Icon>
    </Textfield>
  </div>

  <div class="col-6">
    <Textfield bind:value={geslo} label="Geslo" class="razsiri" required>
      <Icon class="material-icons" slot="leadingIcon">lock</Icon>
    </Textfield>
  </div>

  <Group on:click={prijava}>
    <Button variant="raised" class="razsiri">
      <b>Prijava</b>
    </Button>
  </Group>

</form>
