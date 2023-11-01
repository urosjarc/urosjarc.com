import 'cypress-xpath';
import Ajv from "ajv";

// @ts-ignore
import generateSchema from "generate-schema"
import ucenecResponse from "./ucenecResponse";
import uciteljResponse from "./uciteljResponse";
const ucenecSchema = generateSchema.json(ucenecResponse);
const uciteljSchema = generateSchema.json(uciteljResponse);
//delete the delete ucenecSchema property
delete ucenecSchema['$schema'];
delete uciteljSchema['$schema'];
const ajv = new Ajv();
const domain = 'http://localhost:4200'

describe('Prijava page urosjarc.com tests', () => {
  function prijava(username:string, geslo:string){
    cy.get('#mat-input-0').type(username, { force: true })
    cy.get('#mat-input-1').type(geslo, { force: true })
  }
  beforeEach(() => {
    cy.visit(`${domain}/prijava`)
  })

  it('should get a error message if trying to log in on empty inputs field', () => {
    // wait for http://127.0.0.1:8080/auth/prijava response
    cy.intercept('POST', 'http://127.0.0.1:8080/auth/prijava').as('interceptedPrijavaRequest');
    cy.get('#mat-input-0').focus()
    cy.get('#mat-input-1').focus()
    cy.get('body').click();
    cy.get('#mat-mdc-error-0').should('have.text', 'Vnos je obvezen!');
    cy.get('#mat-mdc-error-1').should('have.text', 'Geslo je obvezno!');
    cy.xpath('/html/body/app/app-public/app-card-navigacija/div/div/div/div/app-public-prijava/form/div/div[3]/button').click()
    cy.wait('@interceptedPrijavaRequest', {timeout: 30000}).then(({response}) => {
      cy.get('#mat-mdc-dialog-title-0').should('have.text', ' KRITIČNA NAPAKA ')
      // if clicked on zapri on error message the message should not be visible
      cy.get('.mdc-dialog__container button').click()
      cy.get('.mdc-dialog__container button').should('not.be.visible')
    })
  });
  it('should log in ucenec page with correct credentials', () => {
    cy.intercept('POST', 'http://127.0.0.1:8080/auth/prijava').as('interceptedPrijavaRequest');
    cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedProfilRequest');
    cy.intercept('GET', 'http://127.0.0.1:8080/ucenec').as('interceptedUcenecRequest');
    let root_id = '';
    prijava('ucenec', 'geslo')
    //click prijava
    cy.xpath('//app-public-prijava/form/div/div[3]/button').click()

    cy.wait('@interceptedPrijavaRequest',{ timeout: 20000 }).then(({ response }) => {
      expect(response!.statusCode).to.equal(200);
      const token = response!.body.token;
      expect(token).not.be.empty;
    });
    cy.wait('@interceptedUcenecRequest',{ timeout: 20000 }).then(({ response }) => {
      const validate = ajv.compile(ucenecSchema);
      const isValid = validate(response!.body);
      expect(response!.statusCode).to.equal(200);

      expect(isValid).to.be.true;

      expect(response!.body.oseba.tip[0]).to.equal('UCENEC');
      expect(response!.body.oseba._id).to.not.be.empty;


    });
    //wait for profil response and intercept, then test the body id and tip
    cy.wait('@interceptedProfilRequest',{ timeout: 20000 }).then(({ response }) => {
      expect(response!.statusCode).to.equal(200)
      root_id = response!.body.oseba_id;
      // TODO: every now and then it skips waiting for response and jumps to this tests
      expect(response!.body.tip[0]).equal('UCENEC');
      expect(root_id).not.be.empty;
    });
    //test local storage
    cy.window().its('localStorage').invoke('getItem', 'root_id').as('rootId');
    cy.window().its('localStorage').invoke('getItem', 'token').as('token');
    cy.get('@rootId').then((rootIdLS) => {
      expect(rootIdLS).equal(root_id)

    });
    cy.get('@token').then((tokenLS) => {
      expect(tokenLS).not.to.be.null;

    });
    cy.url().should('include', '/ucenec');
    //click on nazaj
    cy.xpath('//app-toolbar-navigacija/div/div[1]/app-button-toolbar/div/div[1]/button').click()
    //click  prijava
    cy.xpath('//app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
    cy.url().should('include', '/ucenec');
    //odjavi
    cy.xpath('//app-toolbar-navigacija/div/div[5]/app-button-toolbar/div/div[1]/button').click()
    cy.url().should('include', '/prijava');


  });
  it('should log in ucitelj page with correct credentials', () => {
    cy.intercept('POST', 'http://127.0.0.1:8080/auth/prijava').as('interceptedPrijavaRequest');
    cy.intercept('GET', 'http://127.0.0.1:8080/auth/profil').as('interceptedProfilRequest');
    cy.intercept('GET', 'http://127.0.0.1:8080/ucitelj').as('interceptedUciteljRequest');
    let root_id = '';
    prijava('ucitelj', 'geslo')
    //click prijava
    cy.xpath('//app-public-prijava/form/div/div[3]/button').click()
    cy.wait('@interceptedPrijavaRequest',{ timeout: 20000 }).then(({ response }) => {
      const token = response!.body.token;
      expect(response!.statusCode).to.equal(200)
      expect(token).not.be.empty;
    });
    cy.wait('@interceptedUciteljRequest',{ timeout: 20000 }).then(({ response }) => {
      const validate = ajv.compile(uciteljSchema);
      const isValid = validate(response!.body);
      expect(response!.statusCode).to.equal(200);

      expect(isValid).to.be.true;
      expect(response!.body.oseba.tip[0]).equal('UCITELJ');
      expect(response!.body.oseba._id).not.be.empty;
    });
    //wait for profil response and intercept, then test the body id and tip
    cy.wait('@interceptedProfilRequest',{ timeout: 20000 }).then(({ response }) => {
      root_id = response!.body.oseba_id;
      expect(response!.body.tip[0]).equal('UCITELJ');
      expect(root_id).not.be.undefined;
    });

    //test local storage
    cy.window().its('localStorage').invoke('getItem', 'root_id').as('rootId');
    cy.window().its('localStorage').invoke('getItem', 'token').as('token');
    cy.get('@rootId').then((rootIdLS) => {
      expect(rootIdLS).equal(root_id)

    });
    cy.get('@token').then((tokenLS) => {
      expect(tokenLS).not.to.be.null;

    });
    cy.url().should('include', '/ucitelj');
    //click on nazaj
    cy.xpath('//app-toolbar-navigacija/div/div[1]/app-button-toolbar/div/div[1]/button').click()
    //click  prijava
    cy.xpath('//app-toolbar-navigacija/div/div[4]/app-button-toolbar/div/div[1]/button').click()
    cy.url().should('include', '/ucitelj');
    //odjavi
    cy.xpath('//app-toolbar-navigacija/div/div[7]/app-button-toolbar/div/div[1]/button').click()
    cy.url().should('include', '/prijava');
  })
  it('should not be able to force url / guards test', () => {
    // local host to const
    cy.visit(`${domain}/ucenec`)
    //should redirect to prijava page
    cy.url().should('include', '/prijava')
    cy.visit(`${domain}/ucitelj`)
    //should redirect to prijava page
    cy.url().should('include', '/prijava')
  });

})
