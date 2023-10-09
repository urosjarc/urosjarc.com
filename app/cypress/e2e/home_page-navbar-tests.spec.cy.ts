import 'cypress-xpath';

describe('Home page urosjarc.com tests', () => {
    // it('should visit the site and have text', () => {
    //     cy.visit('http://localhost:4200')
    //     cy.get('h2').should('have.text', 'Inštruktor programiranja, fizike, matematike')
    // })
    // it('should click on the phone number button and go to to kontakt page', () => {
    //     cy.visit('http://localhost:4200')
    //     // telefonska številka gumb
    //     cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-index/div/div/div[1]/button').click()
    //     cy.url().should('include', '/kontakt');
    // });
    // it('should click on the email kontakt button and navigate to kontakt page ', () => {
    //     cy.visit('http://localhost:4200')
    //     // email kontakt gumb
    //     cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-index/div/div/div[2]/button').click()
    //     cy.url().should('include', '/kontakt');
    // });
    // it('should succesfully tests all the navbar links', () => {
    //     cy.visit('http://localhost:4200')
    //     // koledar navbar gumb
    //     cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
    //     cy.url().should('include', '/koledar');
    //     // kontakt navbar gumb
    //     cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()
    //     cy.url().should('include', '/kontakt');
    //     // prijava navbar gumb
    //     cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
    //     cy.url().should('include', '/prijava');
    //
    // });
    // it('should click on the naroči inštrukcije button and navigate to koledar page', () => {
    //     cy.visit('http://localhost:4200')
    //     // naroči inštrukicje gumb
    //     cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-index/div/button').click()
    //     cy.url().should('include', '/koledar');
    // });
    // it('should log in as učenec', () => {
    //     cy.visit('http://localhost:4200')
    //     cy.xpath('//*[@id="mat-input-20"]').type('ucenec')
    //     cy.xpath('//*[@id="mat-input-21"]').type('ucenec')
    //
    // });
    it('should show drop down text when clicked on the first button', () => {
        cy.visit('http://localhost:4200')

        cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-index/div/mat-accordion/mat-expansion-panel[1]').click()
        cy.xpath('//*[@id="cdk-accordion-child-56"]/div')
            .should('contain', 'Inštrukcije gimnazijske matematike in fizike, ter programiranja na univerzitetnem nivoju. Priprava učencev na popravni izpit matematike in fizike. Dolgotrajna pomoč pri učenju programiranja za potrebe strokovnih srednjih šol pri programskih jezikih (C, C++, Java, Javascript, Node, Python, Bash, Linux).');
    });

})

