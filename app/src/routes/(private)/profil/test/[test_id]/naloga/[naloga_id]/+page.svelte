<script lang="ts">
  import Accordion, {Panel, Header, Content} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group, Label} from "@smui/button";
  import {time} from "../../../../../../../libs/utils";
  import {goto} from "$app/navigation";
  import {route} from "../../../../../../../stores/routeStore";

  let seconds = 0
  let open_naloga = false

  function koncaj(){
    console.log("kocaj nalogo")
    goto(route.profil_test_id($page.params.test_id))
  }

  setInterval(() => {
    seconds += 1
  }, 1000)
</script>

<div class="row">

  <Accordion>
    <Panel open>
      <Header color="primary">
        <h1 style="text-align: center">{time(seconds)}</h1>
      </Header>
      <Content>
        <img width="100%"
             src="https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg">
      </Content>
    </Panel>
    <Panel>
      <Header>
        <h3 style="text-align: center">Resitev</h3>
      </Header>
      <Content>
        <img width="100%"
             src="https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg">

        <Group style="display: flex; justify-content: stretch; width: 100%;">
          <Button on:click={koncaj} variant="raised" class="red" style="flex-grow: 1">
            <b>NERESENO</b>
          </Button>
          <Button on:click={koncaj} variant="raised" class="orange" style="flex-grow: 1">
            <b>NAPACNO</b>
          </Button>
          <Button on:click={koncaj} variant="raised" class="green" style="flex-grow: 1">
            <b>PRAVILNO</b>
          </Button>
        </Group>
      </Content>
    </Panel>
    <Panel>
      <Header color="primary">
        <h3 style="text-align: center">Zgodovina</h3>
      </Header>
      <Content>
        <b>TODO: Zgodovina</b>
      </Content>
    </Panel>
  </Accordion>
</div>
