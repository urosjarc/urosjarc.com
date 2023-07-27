<script lang="ts">
  import {Group} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import {auto_prijava} from "$lib/usecases/auto_prijava";
  import {prijava} from "$lib/usecases/prijava";
  import {onMount} from "svelte";
  import SubmitButton from "$lib/components/SubmitButton.svelte";

  onMount(auto_prijava)

  function prijava_submit() {
    loading = true
    prijava({username: username}).finally(() => {
      loading = false
    })
  }

  let loading = false
  let username = ""
  let geslo = ""

</script>

<form class="row" on:submit|preventDefault={prijava_submit}>
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
    <SubmitButton loading={loading}>Prijava</SubmitButton>
  </Group>
</form>
