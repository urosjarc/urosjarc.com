<script lang="ts">
  import Accordion, {Panel, Header, Content} from '@smui-extra/accordion';
  import {page} from "$app/stores";
  import Button, {Group} from "@smui/button";
  import {time} from "../../../../../../../libs/utils";
  import {goto} from "$app/navigation";
  import {route} from "../../../../../../../stores/routeStore";

  let seconds = 0

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
      <Header>Resitev</Header>
      <Content>
        <img width="100%"
             src="https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg">

        <Group style="display: flex; justify-content: stretch; width: 100%;">
          <Button on:click={koncaj} variant="raised" color="primary" style="flex-grow: 1;">
            <b>Ne znam</b>
          </Button>
          <Button on:click={koncaj} variant="raised" color="primary" style="flex-grow: 1;">
            <b>Dobim napačno rešitev</b>
          </Button>
          <Button on:click={koncaj} variant="raised" color="primary" style="flex-grow: 1;">
            <b>Opraviljeno</b>
          </Button>
        </Group>
      </Content>
    </Panel>
  </Accordion>
</div>
