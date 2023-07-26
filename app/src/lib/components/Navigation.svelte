<script>
  import Fab from "@smui/fab";
  import {Icon} from "@smui/common";

  export let classes = "";
  export let icon = "";
  export let text = "";
  export let link = "";
  export let color = "none";

  export let on_click = () => {
  };

  export let radius = "50%"
</script>

<div class="{classes}" on:click={on_click} on:keypress={on_click}>
  <Fab style="background-color: {color}; border-radius: {radius}" href="{link}">
    <Icon class="material-icons">{icon}</Icon>
    <p class="link">{text}</p>
  </Fab>
  <p><b>{text}</b></p>
</div>

<style>
  .link {
    font-size: 0 !important;
  }

  p {
    text-align: center;
    margin: 0;
  }
</style>
