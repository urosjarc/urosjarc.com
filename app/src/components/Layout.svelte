<script>
  export let lg_size = 7
  export let sm_size = 11
  export let cls = ""
</script>


<div id="app" class="container">

  <div class="row justify-content-center" style="margin: 0">
    <div class="col-12 col-sm-{sm_size} col-lg-{lg_size}" style="padding: 0">

      <div id="navigacija" class="row">
        <slot name="navigation"/>
      </div>
      <div id="vsebina" class="{cls}">
        <slot name="body"/>
      </div>

    </div>
  </div>
</div>


<style>
  #app, #vsebina {
    padding: 0;
  }

  #navigacija {
    text-align: center;
    margin: 5px 0 15px 0;
  }

  #vsebina {
    background-color: white;
    border-radius: 5px;
  }
</style>
