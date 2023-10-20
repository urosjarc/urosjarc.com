import 'cypress-xpath';
describe('Log in as UCENEC and test navbar links', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/prijava')
  })
  // TODO: preveri root id is response z local storage!
  it('should log in as UCENEC and all navbar links must work', () => {
      let oseba_id = '';
      let TIP = '';
      cy.get('#mat-input-0').type('ucenec', { force: true })
      cy.get('#mat-input-1').type('geslo', { force: true })
      //log in--------------------------
      cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-prijava/form/div/div[3]/button').click()
      cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedRequest');
      //wait for response and intercept, then test the body id and tip
      cy.wait('@interceptedRequest', { timeout: 10000 }).then(({ response }) => {

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
        const splitWords = rowText.trim().split( ' ');
        const naslov = splitWords[0];
        // type the name of the first row naslov
        cy.get('#mat-input-3').type(naslov);
        //get the first row
        cy.get('.mdc-data-table__content tr').first().as('firstRow');
        // test if the row contains the string from filter input
        cy.get('@firstRow').should('contain.text', naslov)


      })

      // click the delo navbar button----------------------------------------
      cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()
      cy.get('.mdc-data-table__content').should('be.visible');
      // Get the first row of the table
      cy.get('.mdc-data-table__content tr').first().as('firstRow');
      //test tables and filter functionality
      cy.get('@firstRow').invoke('text').then((rowText) => {
        expect(rowText).not.to.be.undefined
        // TODO: ALI ŠE ENKRAT PREVERITI FUNCTIONALNOST FILTRA?
        // const splitWords = rowText.trim().split( ' ');
        // const naslov = splitWords[0];
        // // type the name of the first row naslov
        // cy.get('#mat-input-3').type(naslov);
        // //get the first row
        // cy.get('.mdc-data-table__content tr').first().as('firstRow');
        // // test if the row contains the string from filter input
        // cy.get('@firstRow').should('contain.text', naslov)

      //click profil navbar button---------------------------------------------
        cy.xpath('//app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
        //identification number must be equal to oseba_id gotten from response
        cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/div/app-ucenec-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[2]/mat-list-item/span/div[1]').should('have.text', oseba_id)
        //had to trim the text because the UCENEC in HTML had whitespace
        cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/div/app-ucenec-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[1]/span').invoke('text').then((TIP_TEXT) => {
          const trimtext  = TIP_TEXT.trim();
          expect(trimtext).to.equal(TIP);
        })
        cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/div').should('have.text', 'Naslovi')
        cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/div').should('have.text', 'Kontakti')

      })

  });

})
