import 'cypress-xpath';
import Chainable = Cypress.Chainable;
const domain = 'http://localhost:4200'
function inputOrError(type: string, num: number, text: string = ''): Cypress.Chainable<JQuery<HTMLElement>>{
  switch (type) {
    case 'inputField':
      const inputField = cy.get(`#mat-input-${num}`);
      if (text) {
        return inputField.clear().type(text);
      } else {
        return inputField.clear();
      }
    case 'errorMessage':
      return cy.get(`#mat-mdc-error-${num}`);
    default:
      throw new Error(`Invalid type: ${type}`);
  }
}

describe('Kontakt page urosjarc.com tests', () => {


  beforeEach(() => {
    cy.visit(`${domain}/kontakt`)

  })
  it('should have form', () => {
    cy.get('form').should('exist');
  });
  it('should have input fields', () => {
    cy.get('form')
      .find('input')
      .should('have.length.above', 0 )
      .then((inputLength) => {
        const len = inputLength.length;
        for (let i = 0; i < len ; i++) {
          inputOrError('inputField', len, '').should('exist');
        }
      })
    // //name input
    // inputOrError('inputField', 0).should('exist');
    // //email input
    // cy.get('#mat-input-1').should('exist');
    // //kontakt input
    // cy.get('#mat-input-2').should('exist');
    // //sporočilo input
    // cy.get('#mat-input-3').should('exist');
  });
  it('should have a pošlji button', () => {
    cy.xpath('//app-public-kontakt/form/div/div[4]/button').should('exist');
  });
  it('should not send if form input fields are empty and should have empty errors messages', () => {
    // click pošlji button
    cy.xpath('//app-public-kontakt/form/div/div[4]/button').click()
    cy.get('#mat-mdc-error-0').should('have.text', 'Vnos je obvezen!');
    cy.get('#mat-mdc-error-1').should('have.text', 'Email je obvezen!');
    cy.get('#mat-mdc-error-2').should('have.text', 'Telefon je obvezen!');
    cy.get('#mat-mdc-error-3').should('have.text', 'Sporočilo je obvezno!');

  });
  it('should have error on input if it has only name without forname', () => {
    inputOrError('inputField', 0,  'Danijel')
    cy.get('#mat-input-1').type('email.com')
    cy.get('#mat-input-2').type('04066424')
    cy.get('#mat-input-3').type('Sporočilo')
    cy.get('#mat-mdc-error-0').should('have.text', 'Vnos nima dveh veljavnih  besed!');
    cy.get('#mat-mdc-error-1').should('have.text', 'Email ni veljaven!');
    cy.get('#mat-mdc-error-2').should('have.text', 'Telefonska je premajhna!');
    //pošlji
    cy.xpath('//app-public-kontakt/form/div/div[4]/button').click()
    cy.get('#mat-mdc-error-3').should('have.text', 'Sporočilo je premajhno!');


  });

  // it('should send the form in the inputs are valid', () => {
  //   //fill out the form with valid information
  //   cy.get('#mat-input-0').type('Danijel Korbar')
  //   cy.get('#mat-input-1').type('korbar41@gmail.com')
  //   cy.get('#mat-input-2').type('+386040664247')
  //   cy.get('#mat-input-3').type('To je testno sporočilo')
  //   //push the pošlji button to send the form to server
  //   cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-kontakt/form/div/div[4]/button').click()
  //   cy.get('.ng-star-inserted').should('be.visible')
  //   cy.get('.ng-star-inserted h1').should('have.text', ' Vaše sporočilo je bilo sprejeto! ')
  // });
})
