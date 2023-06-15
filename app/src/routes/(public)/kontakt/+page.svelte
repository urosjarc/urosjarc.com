<script lang="ts">
  import Button, {Group, Label} from '@smui/button';
  import Textfield from '@smui/textfield';
  import Icon from '@smui/textfield/icon';
  import LayoutGrid, {Cell} from '@smui/layout-grid';

  let ime_priimek = "";
  let email = "";
  let telefon = "";
  let vsebina = "";
</script>

<div>
  <form action="https://formspree.io/f/mayaagwp" method="POST">

    <LayoutGrid>
      <Cell spanDevices={{ desktop: 12, tablet: 12, phone: 12 }}>
        <Textfield
          style="width: 100%;"
          helperLine$style="width: 100%;"
          input$name="name" bind:value={ime_priimek} label="Ime in priimek">
          <Icon class="material-icons" slot="leadingIcon">person</Icon>
        </Textfield>
      </Cell>
      <Cell spanDevices={{ desktop: 6, tablet: 12, phone: 12 }}>
        <Textfield
          style="width: 100%;"
          helperLine$style="width: 100%;"
          input$name="email" bind:value={email} label="Email">
          <Icon class="material-icons" slot="leadingIcon">email</Icon>
        </Textfield>
      </Cell>
      <Cell spanDevices={{ desktop: 6, tablet: 12, phone: 12 }}>
        <Textfield
          style="width: 100%;"
          helperLine$style="width: 100%;"
          input$name="telefon" bind:value={telefon} label="Telefon">
          <Icon class="material-icons" slot="leadingIcon">phone</Icon>
        </Textfield>
      </Cell>
      <Cell spanDevices={{ desktop: 12, tablet: 12, phone: 12 }}>
        <Textfield
          style="width: 100%; height:270px;"
          helperLine$style="width: 100%;"
          input$name="vsebina"
          textarea
          bind:value={vsebina}
          label="Sporočilo">
        </Textfield>
      </Cell>
    </LayoutGrid>


    <Group style="display: flex; justify-content: stretch;">
      <Button variant="raised" type="submit" color="primary" style="flex-grow: 1;">
        <b>Pošlji sporočilo</b>
      </Button>
    </Group>
  </form>
</div>

<style>
  form {
    padding: 10px;
  }

  :global(.mdc-layout-grid) {
    padding: 10px 0;
  }
</style>
