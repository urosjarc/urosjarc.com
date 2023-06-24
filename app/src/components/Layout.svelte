<div id="app" class="container">

  <div class="row justify-content-center" style="margin: 0">
    <div class="col-12 col-sm-11 col-lg-6" style="padding: 0">

      <div id="navigacija" class="row">
        <slot name="navigation"/>
      </div>
      <div id="vsebina">
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
    opacity: 0.85;
    background-color: white;
    border-radius: 5px;
    -webkit-box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
    -moz-box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
    box-shadow: 0px 0px 10px -1px rgba(0, 0, 0, 0.75);
  }
</style>
