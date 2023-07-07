export const route = {
  index: "/",
  prijava: "/prijava",
  kontakt: "/kontakt",
  koledar: "/koledar",
  profil: "/profil",
  profil_test: "/profil/test",
  profil_oseba: "/profil/oseba",
  profil_odjava: "/profil",
  profil_test_id: (test_id: String) => `/profil/test/${test_id}`,
  profil_naloga_id: (test_id: String, naloga_id: String) => `/profil/test/${test_id}/naloga/${naloga_id}`
}
