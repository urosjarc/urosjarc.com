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

    })
})
