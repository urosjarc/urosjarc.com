import 'cypress-xpath';
describe('Navbar tests', () => {
  it('should succesfully tests all the navbar links', () => {
    cy.visit('http://localhost:4200')
    // koledar navbar gumb
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
    cy.url().should('include', '/koledar');
    // kontakt navbar gumb
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()
    cy.url().should('include', '/kontakt');
    // prijava navbar gumb
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
    cy.url().should('include', '/prijava');
  });
})
