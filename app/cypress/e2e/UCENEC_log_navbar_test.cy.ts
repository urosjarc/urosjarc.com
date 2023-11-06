import 'cypress-xpath';

import ucenecResponse from "./responses/ucenecResponse";

function arraysContainSameValues(arr1: string[], arr2: string[]): boolean {
    if (arr1.length !== arr2.length) {
        return false;
    }
    arr1.sort()
    arr2.sort()
    let flag = true;
    for (let i = 0; i < arr1.length; i++) {
        if (arr1[i] !== arr2[i]) {
            flag = false;
        }
    }
    return flag;
}


describe('Log in as UCENEC and test navbar links', () => {
    beforeEach(() => {
        cy.visit('http://localhost:4200/prijava')
    })

    it('should log in as UCENEC and all navbar links must work', () => {
        let oseba_id = '';
        let TIP = '';
        let username = '';
        let ime = '';
        let priimek = '';

        let naslovi: string[] = []
        let DOMnaslovi: string[] = []
        let DOMkontakti: string[] = [];
        let kontaktArrayResponse: string[] = []
        let email = ''; // email = kontakt_refs[kontakt.data]
        cy.get('#mat-input-0').type('ucenec', {force: true})
        cy.get('#mat-input-1').type('geslo', {force: true})
        //log in--------------------------
        cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-prijava/form/div/div[3]/button').click()

        cy.intercept('GET', 'http://127.0.0.1:8080/ucenec').as('interceptedUcenecRequest');
        cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedProfilRequest');
        //wait for response and intercept, then test the body id and tip
        cy.wait('@interceptedUcenecRequest', {timeout: 20000}).then(({response}) => {
            // naredi extrakcijo podatkov responsa za testiranje profil ucenca
            ime = response!.body.oseba.ime;
            priimek = response!.body.oseba.priimek;
            username = response!.body.oseba.username;
            email = response!.body.kontakt_refs[0].kontakt.data
            const naslovi_refs = response!.body.naslov_refs;
            // populating array with naslovi objects, for testing the naslovi divs
            for (const item of naslovi_refs) {
                if (item.naslov) {
                    naslovi.push(
                        item.naslov.ulica,
                        item.naslov.zip + ' ' + item.naslov.mesto,
                        item.naslov.drzava)

                }
            }
            // populating array with kontakti objects, for testing the kontakti/email divs

            ucenecResponse.kontakt_refs.forEach((item: any) => {

                const kontakt = item.kontakt;
                kontaktArrayResponse.push(kontakt.data)

            });

            //TODO: ZUNAJ THEN-A USERNAM IMA VREDNOST IME PRIIMEK PA NE????!!!
            //click profil navbar button---------------------------------------------
            cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
            // test username value
            cy.xpath('//app-prikazi-profil-osebe/div/div[1]/mat-list[1]/mat-list[1]/mat-list-item/span/div[1]').should('have.text', username)
            //  test ime priimek value
            cy.xpath('//app-ucenec-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[1]/mat-list-item/span/div[1]').should('have.text', ime + " " + priimek)
            // test the naslovi
            cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/mat-list-item/span/span/div')
                .find('div.ng-star-inserted')
                .should('have.length.above', 0)
                .then(primaryDiv => {
                    for (let i = 1; i <= primaryDiv.length; i++) {

                        cy.xpath(`//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/mat-list-item/span/span/div/div[${i}]`)
                            .find('div')
                            .should('have.length.above', 0)
                            .then(childDiv => {

                                for (let j = 1; j <= childDiv.length; j++) {
                                    cy.xpath(`//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/mat-list-item/span/span/div/div[${i}]/div[${j}]`)
                                        .invoke('text')
                                        .then(text => {
                                            DOMnaslovi.push(text)
                                        })
                                }
                            })
                    }
                })

            // creates a promise that only executes when the array is not empty, then compare naslovi arrays data
            cy.wrap(DOMnaslovi).should('have.length.above', 0).then(DOMnaslovi => {
                console.error(arraysContainSameValues(DOMnaslovi, naslovi))
            });
            cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/mat-list-item/span/span/div')
                .find('div.ng-star-inserted') // Select the div elements inside the parent element
                .should('have.length.above', 0) // Check if there are more than 0 div elements
                .then(($divElements) => {
                    // Get the number of parent div elements
                    const divCount = $divElements.length;
                    for (let i = 1; i <= divCount; i++) {
                        cy.xpath(`//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/mat-list-item/span/span/div/div[${i}]/div[1]`)
                            .invoke('text')
                            .then(text => {
                                DOMkontakti.push(text)
                            })
                    }

                });


            cy.wrap(DOMkontakti).should('have.length.above', 0).then(DOMkontakti => {
                expect(arraysContainSameValues(DOMkontakti, kontaktArrayResponse)).to.be.true

            });

        });

        //wait for response and intercept, then test the body id and tip
        cy.wait('@interceptedProfilRequest', {timeout: 20000}).then(({response}) => {
            expect(response!.body.tip[0]).equal('UCENEC');
            expect(response!.body.oseba_id).not.be.empty;
            oseba_id = response!.body.oseba_id.trim();
            TIP = response!.body.tip[0].trim();

        });


        //click the testi navbar button------------------------------------
        cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
        cy.get('.mdc-data-table__content').should('be.visible');
        // extract the deadline values from tables
        cy.get('.mdc-data-table__content .mat-mdc-row')
            .each((row) => {
                // Extract the value from the specific <td> element you mentioned within each row
                const valueText = Cypress.$(row).find('.mat-column--ez---').text();
                if(valueText.trim().includes('čez')){
                    console.error(valueText.trim(), 'value text')
                    const numericRegex = /(\d+)/;
                    const deadlineText = valueText.trim();
                    // Use the regular expression to find the numeric value in the string
                    const match = numericRegex.exec(deadlineText);
                    let expectedClass = ''
                    const numericValue = parseInt(match![0], 10);
                    if (match) {
                        console.error('we have a match!!!!!!!!!!!!!!!!!!!!!!')
                        if (numericValue <= 0) {
                            expectedClass = 'danger-pulse';
                        } else if (numericValue <= 4) {
                            expectedClass = 'danger-pulse';
                        } else if (numericValue <= 7) {
                            expectedClass = 'warn-pulse';
                        }
                        // Verify if the row has the expected class
                        cy.wrap(row).should('have.class', expectedClass);
                    }
                }
            })
        // Get the first row of the table
        cy.get('.mdc-data-table__content tr').first().as('firstRow');
        //test tables and filter functionality
        cy.get('@firstRow').invoke('text').then((rowText) => {
            expect(rowText).to.not.be.empty;
            const splitWords = rowText.trim().split(' ');
            const naslov = splitWords[0];
            // type the name of the first row naslov
            cy.get('#mat-input-3').type(naslov);
            //get the first row
            cy.get('.mdc-data-table__content tr').first().as('firstRow');
            // test if the row contains the string from filter input
            cy.get('@firstRow').should('contain.text', naslov)

        })



        //click the delo navbar button----------------------------------------
        cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[3]/app-button-toolbar/div/div[1]/button').click()
        cy.get('.mdc-data-table__content').should('be.visible');
        // Get the first row of the table
        cy.get('.mdc-data-table__content tr').first().as('firstRow');
        //test tables and filter functionality
        cy.get('@firstRow').invoke('text').then((rowText) => {
            expect(rowText).not.to.be.undefined
            const splitWords = rowText.trim().split(' ');
            const naslov = splitWords[0];
            // type the name of the first row naslov
            cy.get('#mat-input-4').type(naslov);
            //get the first row
            cy.get('.mdc-data-table__content tr').first().as('firstRow');
            // test if the row contains the string from filter input
            cy.get('@firstRow').should('contain.text', naslov)

            //click profil navbar button---------------------------------------------
            cy.xpath('/html/body/app/app-ucenec/app-card-navigacija/div/div/div/app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
            //identification number must be equal to oseba_id gotten from response
            cy.xpath('//app-prikazi-profil-osebe/div/div[1]/mat-list[2]/mat-list-item/span/div[1]').should('have.text', oseba_id)
            // had to trim the text because the UCENEC in HTML had whitespace
            cy.xpath('//app-ucenec-profil/app-prikazi-profil-osebe/div/div[1]/mat-list[1]/span').invoke('text').then((TIP_TEXT) => {
                const trimtext = TIP_TEXT.trim();
                expect(trimtext).to.equal(TIP);
            })

            cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/div').should('have.text', 'Naslovi')
            cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/div').should('have.text', 'Kontakti')

        })

    });
})
