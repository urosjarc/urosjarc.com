<script lang="ts">
  import Button, {Group} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import LinearProgress from '@smui/linear-progress';
  import {prijavljen_v_profil} from "../../../usecases/prijavljen_v_profil";
  import {prijava} from "../../../usecases/prijava";
  import Napaka from "../../../components/Napaka.svelte";


  function prijava_submit() {
    loading = true
    prijava({
      username: username,
      uspeh() {
      },
      fatal(err) {
        napaka = err
        loading = false
      }
    })
  }

  let napaka = ""
  let loading = false
  let username = ""
  let geslo = ""

  prijavljen_v_profil({
    uspeh() {
    },
    fatal(err) {
      napaka = err
    }
  })

</script>

<div>
  <Napaka open={napaka} napaka={napaka}/>
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
