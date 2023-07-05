<script lang="ts">
  import {token} from "../../../stores/tokenStore";
  import {goto} from "$app/navigation";
  import {route} from "../../../stores/routeStore";
  import {api} from "../../../stores/apiStore";
  import Button from "@smui/button";
  import {onMount} from "svelte";

  let oseba = {}
  let naslovi = []
  let kontakti = []
  let testi = []
  let ucenci = []
  let ucitelji = []

  function logout() {
    token.clear()
    goto(route.prijava)
  }

  onMount(() => {
    api.profil.index().then(data => {
      console.log(data)
      oseba = data.oseba
      naslovi = data.naslov_refs || []
      kontakti = data.kontakt_refs || []
      testi = data.test_refs || []
      ucitelji = data.ucenje_ucitelj_refs || []
      ucenci = data.ucenje_ucenci_refs || []
    }).catch(data => {
      console.error(data)
    })
  })

</script>

<div>
  <Button on:click={logout}>Logout</Button>
  <Button on:click={oseba}>Oseba</Button>

  <h1>Oseba</h1>
  <h2>{oseba._id}</h2>
  <h2>{oseba.ime} {oseba.priimek}</h2>
  <h3>{oseba.tip} {oseba.username}</h3>


  <h1>Naslovi</h1>
  {#each naslovi as naslov}
    <ul>
      <li>{naslov.drzava}</li>
      <li>{naslov.zip} {naslov.mesto}</li>
      <li>{naslov.ulica}</li>
    </ul>
  {/each}

  <h1>Kontakti</h1>
  {#each kontakti as kontakt_ref}
    {@const kontakt = kontakt_ref.kontakt}
    <ul>
      <li>{kontakt.tip}</li>
      <li>{kontakt.data}</li>
    </ul>
  {/each}

  <h1>Testi</h1>
  {#each testi as test_ref}
    {@const test = test_ref.test}
    <ul>
      <li>{test.deadline}</li>
      <li>{test.naslov}</li>
      <li>{test.podnaslov}</li>
      <li>Naloge: {test_ref.status_refs.length}</li>
    </ul>
  {/each}

  <h1>Ucenci</h1>
  <ul>
    {#each ucenci as ucenec_ref}
      {@const ucenec = ucenec_ref.oseba_refs[0]}
      <li>{ucenec.ime} {ucenec.priimek}</li>
    {/each}
  </ul>

  <h1>Ucitelji</h1>
  <ul>
    {#each ucitelji as ucitelj_ref}
      {@const ucitelj = ucitelj_ref.oseba_refs[0]}
      <li>{ucitelj.ime} {ucitelj.priimek}</li>
    {/each}
  </ul>


</div>
