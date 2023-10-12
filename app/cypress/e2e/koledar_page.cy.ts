import 'cypress-xpath';
import 'cypress-iframe';

describe('Koledar page urosjarc.com tests', () => {

  it('should have title', () => {
    cy.visit('http://localhost:4200/koledar');
    cy.frameLoaded()
    cy.iframe()
      .find('ul.css-7w2m40')
      .find('li')
      .its('length')
      .then((li_length) => {
        cy.wrap(li_length).should('eq', 4)
          for (let i = 0; i < li_length ; i++) {
              cy.iframe()
                  // subject img exists
                  .find('.css-f7j3rw img').eq(i)
                  .should('exist');
              const subjectValue = [
                'Matematika',
                'Fizika',
                'Programiranje',
                'Programiranje programskih rešitev'
              ]
              //test if the title if present in current li div
              cy.iframe().find('.css-dlbzca').eq(i)
                .should('have.text', subjectValue[i])

              // test the buttons text value
              cy.iframe().find('.css-1ucbshn').eq(i).should('have.text', 'BOOK');
              //loop over all book buttons
              cy.iframe().find('.css-1ucbshn').eq(i).click();
              // check for page title Date & Time
              cy.iframe().find('.css-kol9p1')
                  .should('have.text', 'Date & Time')

              const titleValues = [
                  'Matematika with Inštrukcije',
                  'Fizika with Inštrukcije',
                  'Programiranje with Inštrukcije',
                  'Programiranje programskih rešitev with Inštrukcije',

              ];
              // test if the text is the right study subject
              cy.iframe().find('.css-k59ll6 span')
                  .should('have.text', titleValues[i]);
              //go back page arrow
              cy.iframe().find('.css-19ldml0').click();
              // select calendar page
              cy.iframe().find('.css-kol9p1')
                  .should('have.text', 'Select Calendar')
              // check if the uroš img exists
              cy.iframe().find('.css-f7j3rw img')
                  .should('exist');
              // test the select buttons value
              cy.iframe().find('.css-1ucbshn').should('have.text', 'Select');
              //click the select button and check the pages title
              cy.iframe().find('.css-1ucbshn').click();
              cy.iframe().find('.css-kol9p1')
                  .should('have.text', 'Date & Time')
              //click the X link to go to home page
              cy.iframe().find('.css-4xd708').click();
          }
      })
  });
})
