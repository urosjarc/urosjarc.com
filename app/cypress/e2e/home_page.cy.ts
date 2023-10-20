import 'cypress-xpath';
import {before} from "mocha";

describe('Home page urosjarc.com tests', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200')
  })
  it('should visit the site and have text', () => {
    cy.get('div[class="text-center pt-3"]').find('h2').should('have.text', 'Inštruktor programiranja, fizike, matematike')
  })
  it('should click on the phone number button and go to to kontakt page', () => {
    // telefonska številka gumb
    cy.xpath('//app-public-index/div/div/div[1]/button').click()
    cy.url().should('include', '/kontakt');
  });
  it('should click on the email kontakt button and navigate to kontakt page ', () => {
    // email kontakt gumb
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-index/div/div/div[2]/button').click()
    cy.url().should('include', '/kontakt');
  });

  it('should click on the naroči inštrukcije button and navigate to koledar page', () => {
    // naroči inštrukicje gumb
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-index/div/button').click()
    cy.url().should('include', '/koledar');
  });
  // acorrdion dropdown tests
  it('should test mat-accordion mat-expansive-panels', () => {

    cy.get('mat-expansion-panel').should('have.lengthOf.greaterThan', 0)
      .its('length')
      .then((panel_length) => {
        for (let i = 0; i < panel_length; i++) {
          cy.get('mat-expansion-panel').eq(i).find('.mat-expansion-panel-header').click();
          cy.get('mat-expansion-panel').eq(i).find('.mat-expansion-panel-body').should('not.be.empty');
        }

      })

  });

})

