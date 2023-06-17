<script lang="ts">
  import Button, {Group, Label} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';

  let ime_priimek = "";
  let email = "";
  let telefon = "";
  let vsebina = "";
</script>

<form class="row" action="https://formspree.io/f/mayaagwp" method="POST">

  <div class="col-sm-12 col-xl-4">
    <Textfield
      style="width: 100%;"
      helperLine$style="width: 100%;"
      input$name="name" bind:value={ime_priimek} label="Ime in priimek">
      <Icon class="material-icons" slot="leadingIcon">person</Icon>
    </Textfield>
  </div>
  <div class="col-sm-6 col-xl-4">
    <Textfield
      style="width: 100%;"
      helperLine$style="width: 100%;"
      input$name="email" bind:value={email} label="Email">
      <Icon class="material-icons" slot="leadingIcon">email</Icon>
    </Textfield>
  </div>
  <div class="col-sm-6 col-xl-4">
    <Textfield
      style="width: 100%;"
      helperLine$style="width: 100%;"
      input$name="telefon" bind:value={telefon} label="Telefon">
      <Icon class="material-icons" slot="leadingIcon">phone</Icon>
    </Textfield>
  </div>
  <div class="col-sm-12">
    <Textfield
      style="width: 100%; height:270px;"
      helperLine$style="width: 100%;"
      input$name="vsebina"
      textarea
      bind:value={vsebina}
      label="Sporočilo">
    </Textfield>
  </div>

  <div class="col-12">
    <Group style="display: flex; justify-content: stretch;">
      <Button variant="raised" type="submit" color="primary" style="flex-grow: 1;">
        <b>Pošlji sporočilo</b>
      </Button>
    </Group>
  </div>
</form>
