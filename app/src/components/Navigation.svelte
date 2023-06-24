<script>
  import Fab from "@smui/fab";
  import {Icon} from "@smui/common";

  export let cls;
  export let icon;
  export let text;
  export let link;
</script>

<div class="{cls}">
  <Fab href="{link}">
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
