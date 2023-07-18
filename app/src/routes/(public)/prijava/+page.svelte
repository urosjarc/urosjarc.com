<script lang="ts">
  import Button, {Group} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import LinearProgress from '@smui/linear-progress';
  import {auto_prijava} from "../../../usecases/auto_prijava";
  import {prijava} from "../../../usecases/prijava";
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import Alerts from "../../../components/Alerts.svelte";


  function prijava_submit() {
    loading = true
    prijava({
      username: username,
      uspeh() {
        goto(route.profil)
      },
      fatal(err) {
        fatal = err
      },
      error(err) {
        error = err
      },
      warn(err) {
        warn = err
      }
    }).finally(() => (loading = false))
  }

  let fatal = ""
  let error = ""
  let warn = ""

  let loading = false
  let username = ""
  let geslo = ""

  auto_prijava({
    uspeh() {
      goto(route.profil)
    },
    fatal(err) {
    },
    error(err) {
    },
    warn(err) {
    },
  })

</script>

<div>
  <Alerts bind:fatal={fatal} bind:error={error} bind:warn={warn}/>

  <form class="row" on:submit|preventDefault={prijava_submit}>
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
</div>
