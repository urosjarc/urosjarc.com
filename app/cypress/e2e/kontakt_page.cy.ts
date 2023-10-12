import 'cypress-xpath';
import {before} from "mocha";
describe('Kontakt page urosjarc.com tests', () => {

  beforeEach(() => {
    cy.visit('http://localhost:4200/kontakt')

  })
  it('should have form', () => {
    cy.get('form').should('exist');
  });
  it('should have input fields', () => {
    //name input
    cy.get('#mat-input-0').should('exist');
    //email input
    cy.get('#mat-input-1').should('exist');
    //kontakt input
    cy.get('#mat-input-2').should('exist');
    //sporočilo input
    cy.get('#mat-input-3').should('exist');
  });
  it('should have a pošlji button', () => {
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-kontakt/form/div/div[4]/button').should('exist');
  });
  it('should not except single name for name input', () => {
    cy.get('#mat-input-0').type('Danijel')
    cy.get('#mat-input-1').focus()
  });
})
