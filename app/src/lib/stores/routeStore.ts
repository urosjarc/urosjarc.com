export const route = {
  index: "/",
  prijava: "/prijava",
  kontakt: "/kontakt",
  koledar: "/koledar",

  ucenec: "/ucenec",
  ucenec_test: "/ucenec/test",
  ucenec_oseba: "/ucenec/oseba",
  ucenec_test_id: (test_id: String) => `/ucenec/test/${test_id}`,
  ucenec_naloga_id: (test_id: String, naloga_id: String) => route.ucenec_test_id(`${test_id}/naloga/${naloga_id}`),

  admin: "/admin",
}
