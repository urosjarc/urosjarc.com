import 'cypress-xpath';

import ucenecResponse from "./responses/ucenecResponse";
function arraysContainSameValues(arr1: string[], arr2: string[]) {
  if (arr1.length !== arr2.length) {
    return false;
  }
  // order the arrays
  const set1 = new Set(arr1);
  const set2 = new Set(arr2);
  console.error(set1, 'set1')
  console.error(set2, 'set2')
  // Check if the sets have the same size (same unique elements)
  if (set1.size !== set2.size) {
    return false;
  }

  // Check if every element in set1 is also in set2 and vice versa
  return [...set1].every(value => set2.has(value));
}

describe('Log in as UCENEC and test navbar links', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/prijava')
  })

  it('should log in as UCENEC and all navbar links must work', () => {
    let oseba_id = '';
    let TIP = '';
    let username = '';
    let ime = '';
    let priimek = '';

    let naslovi: string[] = []
    let kontaktArrayResponse: string[] = []
    let email = ''; // email = kontakt_refs[kontakt.data]
    cy.get('#mat-input-0').type('ucenec', {force: true})
    cy.get('#mat-input-1').type('geslo', {force: true})
    //log in--------------------------
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-prijava/form/div/div[3]/button').click()

    cy.intercept('GET', 'http://127.0.0.1:8080/ucenec').as('interceptedUcenecRequest');
    cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedProfilRequest');
    //wait for response and intercept, then test the body id and tip
    cy.wait('@interceptedUcenecRequest', {timeout: 20000}).then(({response}) => {
      // naredi extrakcijo podatkov responsa za testiranje profil ucenca
      ime = response!.body.oseba.ime;
      priimek = response!.body.oseba.priimek;
      username = response!.body.oseba.username;
      email = response!.body.kontakt_refs[0].kontakt.data
      const naslovi_refs = response!.body.naslov_refs;
      // populating array with naslovi objects, for testing the naslovi divs
      for (let i = 0; i < naslovi_refs.length; i++) {
        if (naslovi_refs[i].naslov) {
          naslovi.push(
            naslovi_refs[i].naslov.ulica,
            naslovi_refs[i].naslov.zip + ' ' + naslovi_refs[i].naslov.mesto,
            naslovi_refs[i].naslov.drzava)

        }
      }
      // populating array with kontakti objects, for testing the kontakti/email divs

      ucenecResponse.kontakt_refs.forEach((item: any) => {

        const kontakt = item.kontakt;
        kontaktArrayResponse.push(kontakt.data)

      });
      console.error(kontaktArrayResponse)
      //TODO: ZUNAJ THEN-A USERNAM IMA VREDNOST IME PRIIMEK PA NE????!!!
      //click profil navbar button---------------------------------------------
      cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
      // test username value

      cy.xpath('//app-prikazi-profil-osebe/div/div[1]/mat-list[1]/mat-list[1]/mat-list-item/span/div[1]').should('have.text', username)
      //  test ime priimek value
      cy.xpath('//app-ucenec-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[1]/mat-list-item/span/div[1]').should('have.text', ime + " " + priimek)
      // test the naslovi

      let DOMnaslovi: string[] = []
      let DOMkontakti: string[] = [];
      for (let i = 1; i <= 3; i++) {

        for (let j = 1; j <= 3; j++) {

          cy.xpath(`//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/mat-list-item/span/span/div/div[${i}]/div[${j}]`)
            .invoke('text')
            .then(text => {
              DOMnaslovi.push(text)
            })
        }

      }
      // creates a promise that only executes when the array is not empty, then compare naslovi arrays data
      cy.wrap(DOMnaslovi).should('have.length.above', 0).then(DOMnaslovi => {
        console.error(arraysContainSameValues(DOMnaslovi, naslovi))
      });

      for (let i = 1; i <= 10; i++) {
        cy.xpath(`//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/mat-list-item/span/span/div/div[${i}]/div[1]`)
          .invoke('text')
          .then(text => {
            DOMkontakti.push(text)
          })
      }

      cy.wrap(DOMkontakti).should('have.length.above', 0).then(DOMkontakti => {
        console.error(DOMkontakti, 'domkontakti')
        // @ts-ignore
        expect(arraysContainSameValues(DOMkontakti,kontaktArrayResponse)).to.be.true

      });

    });

    //wait for response and intercept, then test the body id and tip
    cy.wait('@interceptedProfilRequest', {timeout: 20000}).then(({response}) => {
      // naredi extrakcijo podatkov responsa za testiranje profil ucenca

      expect(response!.body.tip[0]).equal('UCENEC');
      expect(response!.body.oseba_id).not.be.empty;
      oseba_id = response!.body.oseba_id.trim();
      TIP = response!.body.tip[0].trim();

    });


    //click the testi navbar button------------------------------------
    cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
    cy.get('.mdc-data-table__content').should('be.visible');

    // Get the first row of the table
    cy.get('.mdc-data-table__content tr').first().as('firstRow');
    //test tables and filter functionality
    cy.get('@firstRow').invoke('text').then((rowText) => {
      expect(rowText).to.not.be.empty;
      const splitWords = rowText.trim().split(' ');
      const naslov = splitWords[0];
      // type the name of the first row naslov
      cy.get('#mat-input-3').type(naslov);
      //get the first row
      cy.get('.mdc-data-table__content tr').first().as('firstRow');
      // test if the row contains the string from filter input
      cy.get('@firstRow').should('contain.text', naslov)

    })

    //click the delo navbar button----------------------------------------
    cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()
    cy.get('.mdc-data-table__content').should('be.visible');
    // Get the first row of the table
    cy.get('.mdc-data-table__content tr').first().as('firstRow');
    //test tables and filter functionality
    cy.get('@firstRow').invoke('text').then((rowText) => {
      expect(rowText).not.to.be.undefined
      const splitWords = rowText.trim().split(' ');
      const naslov = splitWords[0];
      // type the name of the first row naslov
      //TODO: WHEN WRITING AN INPUT ON TO FILTER OUT THE TABLE, THE TEXT IS RENDERED TO PAGE WITH A BIG LAG??
      cy.get('#mat-input-4').type(naslov);
      //get the first row
      cy.get('.mdc-data-table__content tr').first().as('firstRow');
      // test if the row contains the string from filter input
      cy.get('@firstRow').should('contain.text', naslov)

      //click profil navbar button---------------------------------------------
      cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
      //identification number must be equal to oseba_id gotten from response
      cy.xpath('//app-prikazi-profil-osebe/div/div[1]/mat-list[2]/mat-list-item/span/div[1]').should('have.text', oseba_id)
      // had to trim the text because the UCENEC in HTML had whitespace
      cy.xpath('//app-ucenec-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[1]/span').invoke('text').then((TIP_TEXT) => {
        const trimtext = TIP_TEXT.trim();
        expect(trimtext).to.equal(TIP);
      })

      cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/div').should('have.text', 'Naslovi')
      cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/div').should('have.text', 'Kontakti')

    })

  });

})
