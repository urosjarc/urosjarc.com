import {Date_casStr, Date_datumStr, Date_dodaj, Date_ime_dneva, Date_oddaljenost_v_dneh} from "./Date";

describe('utils: unit tests za Date.ts', () => {
  it('Date_oddaljenost_v_dneh() mora vrniti število dni med dvema datuma', () => {
    const trenutniDatum = new Date();

    //želimo preveriti funkcijo za datum, ki je 5 dni v prihodnosti
    const ciljniDatum = new Date(trenutniDatum);
    const ciljniDatum2 = new Date(trenutniDatum);
    ciljniDatum.setDate(trenutniDatum.getDate() + 5);
    ciljniDatum2.setDate(trenutniDatum.getDate() - 5);
    const razlikaVDneh = Date_oddaljenost_v_dneh(ciljniDatum);
    const razlikaVDneh2 = Date_oddaljenost_v_dneh(ciljniDatum2);


    // Preverimo, ali je razlika v dneh pravilno izračunana
    expect(razlikaVDneh).toEqual(5);
    expect(razlikaVDneh2).toEqual(-5);
  });
  it('Date_ime_dneva() mora vrniti dan v pravilnem prevodu ', () => {
    const dan = new Date(2023, 8, 16, 10, 0, 0)
    expect(Date_ime_dneva(dan)).toEqual('sobota')
  });
  it('Date_casStr() mora vrniti pravi format ure ', () => {
    const dan = new Date(2023, 8, 16, 10, 0, 0)


    expect(Date_casStr(dan)).toEqual('10:00:00')
  });

  it('Date_datumStr() mora vrniti pravi format ure glede na boolean parameter ', () => {
    const dan1 = new Date('   2023-09-16   ');
    const dan2 = new Date('2023-09-16T23:30:00');

    expect(Date_datumStr(dan1)).toEqual('16.9.2023')
    expect(Date_datumStr(dan2)).toEqual('16.9.2023')
  });
  it('Date_dodaj() mora dodati pravilno število dni ', () => {
    const dan = new Date('2023-09-16');

    expect(Date_dodaj(dan, 4)).toEqual(new Date('2023-09-20'))
  });
})
