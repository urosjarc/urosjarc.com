import 'cypress-xpath';

import ucenecResponse from "./responses/ucenecResponse";
import {arraysContainSameValues} from "./utils/helperFunctions";
const domain = 'http://localhost:4200'



describe('Log in as UCENEC and test navbar links', () => {

    before(() => {
        cy.visit(`${domain}/prijava`)
        cy.get('#mat-input-0').type('ucenec', {force: true})
        cy.get('#mat-input-1').type('geslo', {force: true})
        //log in--------------------------
        cy.xpath('//app-public-prijava/form/div/div[3]/button').click()
        cy.intercept('GET', 'http://127.0.0.1:8080/ucenec').as('interceptedUcenecRequest');
        cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedProfilRequest');
    });

    it('should log in as UCENEC and test all the pages', () => {
        let oseba_id = '';
        let TIP = '';
        let username = '';
        let ime = '';
        let priimek = '';
        let naslovi: string[] = []
        let DOMnaslovi: string[] = []
        let DOMkontakti: string[] = [];
        let kontaktArrayResponse: string[] = []

        cy.wait('@interceptedUcenecRequest', {timeout: 20000}).then(({response}) => {
            // naredi extrakcijo podatkov responsa za testiranje profil ucenca
            oseba_id = response!.body.oseba._id;
            ime = response!.body.oseba.ime;
            priimek = response!.body.oseba.priimek;
            username = response!.body.oseba.username;
            TIP = response!.body.oseba.tip[0]
            const naslovi_refs = response!.body.naslov_refs;
            // populating array with naslovi objects from učenec response, for testing the naslovi divs
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

            //click profil navbar button---------------------------------------------
            cy.get('app-button-toolbar[ng-reflect-tekst="Profil"]').click();
            cy.url().should('include', 'ucenec/profil')
            //identification number must be equal to oseba._id gotten from response
            cy.xpath('//app-prikazi-profil-osebe/div/div[1]/mat-list[2]/mat-list-item/span/div[1]').should('have.text', oseba_id)

            // had to trim the text because the UCENEC in HTML had whitespace
            cy.xpath('//app-prikazi-profil-osebe/div/div[1]/mat-list[1]/span')
                .should('be.visible')
                .then($ucenecText => {
                    const formatUcenecText = $ucenecText.text().trim()
                    expect(formatUcenecText).to.equal(TIP)
                })

            cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[1]/mat-list/div').should('have.text', 'Naslovi')
            cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/div').should('have.text', 'Kontakti')
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
            cy.wrap(DOMnaslovi).should('have.length.above', 0)

            cy.xpath('//app-prikazi-profil-osebe/div/div[2]/div/div[2]/mat-list/mat-list-item/span/span/div')
                .find('div.ng-star-inserted') // Select the div elements inside the parent element
                .should('have.length.above', 0)
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



        //click the testi navbar button------------------------------------
        cy.get('app-button-toolbar[ng-reflect-tekst="Testi"]').click();
        cy.url().should('include', 'ucenec/testi')
        cy.get('.mdc-data-table__content').should('be.visible');
        // extract the deadline values from tables
        cy.get('.mdc-data-table__content .mat-mdc-row')
            .each((row) => {
                // Extract the value from the specific <td> element you mentioned within each row
                const valueText = Cypress.$(row).find('.mat-column--ez---').text();
                if(valueText.trim().includes('čez')){
                    const numericRegex = /(\d+)/;
                    const deadlineText = valueText.trim();
                    // Use the regular expression to find the numeric value in the string
                    const match = numericRegex.exec(deadlineText);
                    let expectedClass = ''
                    const numericValue = parseInt(match![0]);
                    if (match) {
                        if (numericValue <= 0) {
                            expectedClass = 'danger-pulse';
                        } else if (numericValue < 4) {
                            expectedClass = 'danger-pulse';
                        } else if (numericValue < 7) {
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
        cy.get('app-button-toolbar[ng-reflect-tekst="Delo"]').click();
        cy.url().should('include', 'ucenec/delo')
        cy.get('.mdc-data-table__content').should('be.visible');
        // Get the first row of the table
        cy.get('.mdc-data-table__content tr').first().as('firstRow');
        //test tables and filter functionality
        cy.get('@firstRow').invoke('text').then((rowText) => {
            expect(rowText).not.to.be.empty
            const splitWords = rowText.trim().split(' ');
            const naslov = splitWords[0];
            // paste the name of the first row naslov, because of teh slow typing in tests
            cy.get('#mat-input-4').invoke('val', naslov).trigger('input');
            //get the first row
            cy.get('.mdc-data-table__content tr').first().as('firstRow');
            // test if the row contains the string from filter input
            cy.get('@firstRow').should('contain.text', naslov)


        })

        });
    });
    // it('should test navbar test link', () => {
    //     //click of test link
    //
    //     cy.xpath('//app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
    //     cy.wait(5000)
    //
    // });
    //
    // it('should test navbar delo link', () => {
    //     //click of test link
    //     cy.xpath('//app-toolbar-navigacija/div/div[2]/app-button-toolbar/div/div[1]/button').click()
    // });

})
