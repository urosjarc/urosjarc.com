import 'cypress-xpath';
describe('Log in as UCENEC and test navbar links', () => {
    beforeEach(() => {
        cy.visit('http://localhost:4200/prijava')
    })
    it('should log in as UCENEC and all navbar links must work', () => {
        let oseba_id = '';
        let TIP = '';
        cy.get('#mat-input-0').type('ucitelj', {force: true})
        cy.get('#mat-input-1').type('geslo', {force: true})
        //log in--------------------------
        cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-prijava/form/div/div[3]/button').click()
        cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedRequest');
        //wait for response and intercept, then test the body id and tip
        cy.wait('@interceptedRequest', { timeout: 10000 }).then(({ response }) => {

            expect(response!.body.tip[0]).equal('UCITELJ');
            expect(response!.body.oseba_id).not.be.empty;
            oseba_id = response!.body.oseba_id.trim();
            TIP = response!.body.tip[0].trim();
            //profil button -------------------------------------
            cy.xpath('//app-toolbar-navigacija/div/div[6]/app-button-toolbar/div/div[1]/button').click()
            cy.xpath('//app-ucitelj-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[1]/span').should('contain.text', TIP)
            cy.xpath('/html/body/app/app-ucitelj/app-card-navigacija/div/div/div/div/app-ucitelj-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[2]/mat-list-item/span/div[1]').should('have.text', oseba_id)

        });
        //zvezki button-----------------------------------------
      // cy.xpath('//app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
      // cy.get('.mdc-data-table__content tr').first().as('firstRow');
      // //test tables and filter functionality
      // cy.get('@firstRow').invoke('text').then((rowText) => {
      //
      //   expect(rowText).to.not.be.empty;
      // })
      // // choose tr zvezek naslov in its present
      // cy.xpath('//*[@id="cdk-step-content-0-0"]/app-table/table/tbody/tr[1]').click()
      //     // mat step header tematike
      //     cy.xpath('//*[@id="cdk-step-label-0-1"]').click()
      //     // choose tematike tr
      //     cy.xpath('//*[@id="cdk-step-content-0-1"]/app-table/table/tbody/tr[1]').click()
      //     // mat step header naloge
      //     cy.xpath('//*[@id="cdk-step-label-0-2"]').click()
      //     cy.xpath('//*[@id="cdk-step-content-0-2"]/app-table/table/tbody/tr[1]').click()
      //     //mat step header učenci
      //     cy.xpath('//*[@id="cdk-step-label-0-3"]').click()
      //         // table content učenci test
      //         cy.get('.mdc-data-table__content tr').first().as('firstRow2');
      //         //test tables
      //         cy.get('@firstRow2').invoke('text').then((rowText2) => {
      //
      //           expect(rowText2).to.not.be.empty;
      //           cy.xpath('//*[@id="cdk-step-content-0-3"]/app-table/table/tbody/tr[1]').click();
      //         })
      //     // mat step header potrditev
      //     cy.xpath('//*[@id="cdk-step-label-0-4"]').click()
      //       //input naslov tests
      //       cy.get('#mat-input-7').type('naslov test 1');
      //         //chosose a date
      //         cy.xpath('//*[@id="cdk-step-content-0-4"]/div/form/div[1]/div[2]/app-form-field-datum/mat-form-field').click()
      //         cy.get('.mat-calendar-body-cell').contains('22').click();
      //         // type podnaslov
      //         cy.get('#mat-input-9').type('podnaslov 1');
      //         // click potrdi button
      //         cy.xpath('//*[@id="cdk-step-content-0-4"]/div/form/div[2]/button[1]').click()
      // testi button
      cy.xpath('//app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()
      cy.get('.mdc-data-table__content tr').first().as('firstRow');
      //test tables and filter functionality
      cy.get('@firstRow').invoke('text').then((rowText) => {

        expect(rowText).to.not.be.empty;
      })
      // učenci button
      cy.xpath('/html/body/app/app-ucitelj/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
      cy.get('.mdc-data-table__content tr').first().as('firstRow');
      //test tables and filter functionality
      cy.get('@firstRow').invoke('text').then((rowText3) => {

        expect(rowText3).to.not.be.empty;
      })
    })
})
