import 'cypress-xpath';
import next from "ajv/dist/vocabularies/next";

describe('Log in as UCENEC and test navbar links', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/prijava')
  })
  it('should log in as UCENEC and all navbar links must work', () => {
    let oseba_id = '';
    let TIP = '';
    const naslov_testa = 'Test naslov 85'
    let tests_table_row_length_before = 0;
    let tests_table_row_lenght_after = 0;
    cy.get('#mat-input-0').type('ucitelj', {force: true})
    cy.get('#mat-input-1').type('geslo', {force: true})
    //log in--------------------------
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-prijava/form/div/div[3]/button').click()
    cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedRequest');
    //wait for response and intercept, then test the body id and tip
    cy.wait('@interceptedRequest', {timeout: 20000}).then(({response}) => {

      expect(response!.body.tip[0]).equal('UCITELJ');
      expect(response!.body.oseba_id).not.be.empty;
      oseba_id = response!.body.oseba_id.trim();
      TIP = response!.body.tip[0].trim();
      //profil button -------------------------------------
      cy.xpath('//app-toolbar-navigacija/div/div[6]/app-button-toolbar/div/div[1]/button').click()
      cy.xpath('//app-ucitelj-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[1]/span').should('contain.text', TIP)
      cy.xpath('/html/body/app/app-ucitelj/app-card-navigacija/div/div/div/div/app-ucitelj-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[2]/mat-list-item/span/div[1]').should('have.text', oseba_id)

    });
    // testi button
    cy.xpath('//app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()
    cy.get('.mdc-data-table__content tr').first().as('firstRow');
    //test tables and filter functionality
    cy.get('@firstRow').invoke('text').then((rowText) => {

      expect(rowText).to.not.be.empty;
    })
    //get total table row number for later tests of creating test
    countTableRows1()

    function countTableRows1() {
      cy.get('.mdc-data-table__content tr')
        .its('length')
        .then(row_length => {
          tests_table_row_length_before += row_length;
          navigateToNextPage1()
        })
    }

    function navigateToNextPage1() {
      cy.xpath('//app-ucitelj-testi/app-table/mat-paginator/div/div/div[2]/button[3]')
        .should('be.visible')
        .then(nextPageButton => {
            if (nextPageButton && !nextPageButton.is(':disabled')) {
              cy.xpath('//app-ucitelj-testi/app-table/mat-paginator/div/div/div[2]/button[3]').click()
              countTableRows1()
            } else return
          }
        )
    }

    // učenci button
    cy.xpath('/html/body/app/app-ucitelj/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
    cy.get('.mdc-data-table__content tr').first().as('firstRow');
    //test tables and filter functionality
    cy.get('@firstRow').invoke('text').then((rowText3) => {

      expect(rowText3).to.not.be.empty;
    })
    //zvezki button-----------------------------------------
    cy.xpath('//app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
    cy.get('.mdc-data-table__content tr').first().as('firstRow');
    //test tables and filter functionality
    cy.get('@firstRow').invoke('text').then((rowText) => {

      expect(rowText).to.not.be.empty;
    })
    cy.intercept('POST', 'http://127.0.0.1:8080/ucitelj/test').as('interceptedTestiRequest');

    // choose tr zvezek naslov in its present
    cy.xpath('//*[@id="cdk-step-content-0-0"]/app-table/table/tbody/tr[1]').click()
    // mat step header tematike
    cy.xpath('//*[@id="cdk-step-label-0-1"]').click()
    // choose tematike tr
    cy.xpath('//*[@id="cdk-step-content-0-1"]/app-table/table/tbody/tr[1]').click()
    // mat step header naloge
    cy.xpath('//*[@id="cdk-step-label-0-2"]').click()
    cy.xpath('//*[@id="cdk-step-content-0-2"]/app-table/table/tbody/tr[1]').click()
    //mat step header učenci
    cy.xpath('//*[@id="cdk-step-label-0-3"]').click()
    // table content učenci test
    cy.get('.mdc-data-table__content tr').first().as('firstRow2');
    //test tables
    cy.get('@firstRow2').invoke('text').then((rowText2) => {

      expect(rowText2).to.not.be.empty;
      cy.xpath('//*[@id="cdk-step-content-0-3"]/app-table/table/tbody/tr[1]').click();
    })
    // mat step header potrditev
    cy.xpath('//*[@id="cdk-step-label-0-4"]').click()
    //input naslov tests
    cy.get('#mat-input-9').type(naslov_testa);
    //chosose a date
    cy.xpath('//*[@id="cdk-step-content-0-4"]/div/form/div[1]/div[2]/app-form-field-datum/mat-form-field').click()
    cy.get('.mat-calendar-body-cell').contains('22').click();
    // type podnaslov
    cy.get('#mat-input-11').type('podnaslov 1');
    // click potrdi button
    cy.xpath('//*[@id="cdk-step-content-0-4"]/div/form/div[2]/button[1]').click()
    // wait for the test to be saved to db and wait for response http://127.0.0.1:8080/ucitelj/test
    // testi button
    cy.xpath('/html/body/app/app-ucitelj/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()

    cy.wait('@interceptedTestiRequest', {timeout: 25000}).then(() => {
      //refresh the page to get the next test visible in table row
      cy.reload()
      //get total table row number for later tests of creating test
      countTableRows()

      async function countTableRows() {
        cy.get('.mdc-data-table__content tr').its('length').then(async row_length => {
          tests_table_row_lenght_after += row_length;
          await navigateToNextPage()
        })
      }

      async function navigateToNextPage() {
        cy.xpath('//app-ucitelj-testi/app-table/mat-paginator/div/div/div[2]/button[3]')
          .should('be.visible')
          .then(async nextPageButton => {
              if (nextPageButton && !nextPageButton.is(':disabled')) {
                cy.xpath('//app-ucitelj-testi/app-table/mat-paginator/div/div/div[2]/button[3]').click()
                await countTableRows()
              } else {
                // if the pagnation button is not visible, all the rows have benn counted and test the length of tests table rows
                expect(tests_table_row_length_before + 1).to.equal(tests_table_row_lenght_after)
                return
              }
            }
          )
      }

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
