<script lang="ts">
  import Button from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';

  function kontakt_submit(){

  }

  let ime_priimek = "";
  let email = "";
  let telefon = "";
  let vsebina = "";
</script>

<form class="row" on:submit|preventDefault={kontakt_submit}>

  <div class="col-sm-12 col-xl-4">
    <Textfield required class="razsiri"
               input$name="name"
               bind:value={ime_priimek}
               label="Ime in priimek">
      <Icon class="material-icons" slot="leadingIcon">person</Icon>
    </Textfield>
  </div>

  <div class="col-sm-6 col-xl-4">
    <Textfield required class="razsiri"
               input$name="email"
               bind:value={email}
               label="Email">
      <Icon class="material-icons" slot="leadingIcon">email</Icon>
    </Textfield>
  </div>

  <div class="col-sm-6 col-xl-4">
    <Textfield required class="razsiri"
               input$name="telefon"
               bind:value={telefon}
               label="Telefon">
      <Icon class="material-icons" slot="leadingIcon">phone</Icon>
    </Textfield>
  </div>

  <div class="col-sm-12">
    <Textfield required class="razsiri" style="height:270px"
               input$name="vsebina"
               textarea
               bind:value={vsebina}
               label="Sporočilo">
    </Textfield>
  </div>

  <div class="col-12">
      <Button class="razsiri" variant="raised" type="submit">
        <b>Pošlji sporočilo</b>
      </Button>
  </div>

</form>
