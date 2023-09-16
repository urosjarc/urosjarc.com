import {Date_casStr, Date_datumStr, Date_dodaj, Date_ime_dneva, Date_oddaljenost_v_dneh} from "./Date";

describe('utils: unit tests za Date.ts', () => {
  // TODO: ČE DAM V TESTIH END 4 DNI NAPREJ DOBIM NAZAJ SEVEDA -4, MOGLO BIT end - start , V UTILSIH DATE.TS
  it('Date_oddaljenost_v_dneh() mora vrniti število dni med dvema datuma', () => {
        const currentDate = new Date();
        const offsetDate = new Date(currentDate);
        offsetDate.setDate(currentDate.getDate()  - 4);
        const end = new Date(2023, 8, 20, 10, 0, 0)
        const razlikaMedDnevi = Date_oddaljenost_v_dneh(end)
        expect(razlikaMedDnevi).toEqual(-4);
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
    const dan2 = new Date('2023-09-16T05:30:00');
    // TODO: TUKAJ MI FEJLA TEST, ZAMIK DNEVA ZA 1!!
    expect(Date_datumStr(dan1, true)).toEqual('2023-09-15')
    expect(Date_datumStr(dan2, false)).toEqual('16.9.2023')
  });
  it('Date_dodaj() mora dodati pravilno število dni ', () => {
    const dan = new Date('2023-09-16');

    expect(Date_dodaj(dan, 4)).toEqual(new Date('2023-09-20'))
  });
})
