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
  it('should not send if form input fields are empty and should have empty errors messages', () => {
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-kontakt/form/div/div[4]/button').click()
    cy.get('#mat-mdc-error-0').should('have.text', 'Vnos je obvezen!');
    cy.get('#mat-mdc-error-1').should('have.text', 'Email je obvezen!');
    cy.get('#mat-mdc-error-2').should('have.text', 'Telefon je obvezen!');
    cy.get('#mat-mdc-error-3').should('have.text', 'Sporočilo je obvezno!');
  });
  it('should have error on input if it has only name without forname', () => {
    cy.get('#mat-input-0').focus()
    cy.get('#mat-input-1').focus()
    cy.get('#mat-mdc-error-0').should('have.text', 'Vnos je obvezen!');
    cy.get('#mat-input-0').type('Danijel')
    cy.get('#mat-input-1').focus()
    cy.get('#mat-mdc-error-0').should('have.text', 'Vnos nima dveh veljavnih  besed!');
  });
  it('should have an email error message if email is not valid', () => {
    cy.get('#mat-input-1').focus()
    cy.get('#mat-input-2').focus()
    cy.get('#mat-mdc-error-1').should('have.text', 'Email je obvezen!');
    cy.get('#mat-input-1').type('email.com')
    cy.get('#mat-input-2').focus()
    cy.get('#mat-mdc-error-1').should('have.text', 'Email ni veljaven!');
  });
  it('should have an email error message if phone number is to small', () => {
    cy.get('#mat-input-2').focus()
    cy.get('#mat-input-3').focus()
    cy.get('#mat-mdc-error-2').should('have.text', 'Telefon je obvezen!');
    cy.get('#mat-input-2').type('04066424')
    cy.get('#mat-input-3').focus()
    cy.get('#mat-mdc-error-2').should('have.text', 'Telefonska je premajhna!');
  });
  it('should have an email error message if message is to small', () => {
    cy.get('#mat-input-3').focus()
    //switch focus on other input to get error message
    cy.get('#mat-input-2').focus()
    cy.get('#mat-mdc-error-3').should('have.text', 'Sporočilo je obvezno!');
    cy.get('#mat-input-3').type('Sporočilo')
    cy.get('#mat-mdc-error-3').should('have.text', 'Sporočilo je premajhno!');
  });
  it('should send the form in the inputs are valid', () => {
    //fill out the form with valid information
    cy.get('#mat-input-0').type('Danijel Korbar')
    cy.get('#mat-input-1').type('korbar41@gmail.com')
    //TODO: telefonsko je treba mogoče obdelati na serverju, kot pri SMS sender ? Za zdaj je validiran samo format +386...
    cy.get('#mat-input-2').type('+386040664247')
    cy.get('#mat-input-3').type('To je testno sporočilo')
    //push the pošlji button to send the form to server
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-kontakt/form/div/div[4]/button').click()
    // TODO: tukaj mogoče ni potrebno imeti timeout ?
    cy.get('.ng-star-inserted').should('be.visible')
    cy.get('.ng-star-inserted h1').should('have.text', ' Vaše sporočilo je bilo sprejeto! ')
  });
})
