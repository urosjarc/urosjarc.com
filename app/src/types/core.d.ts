type Nullable<T> = T | null | undefined
export namespace core.data {
    class OsebaData {
        constructor(oseba: core.domain.Oseba, naslov_refs?: Array<core.domain.Naslov>, ucenje_ucitelj_refs?: Array<core.data.UcenjeData>, kontakt_refs?: Array<core.data.KontaktData>, test_refs?: Array<core.data.TestData>);
        get oseba(): core.domain.Oseba;
        get naslov_refs(): Array<core.domain.Naslov>;
        get ucenje_ucitelj_refs(): Array<core.data.UcenjeData>;
        get kontakt_refs(): Array<core.data.KontaktData>;
        get test_refs(): Array<core.data.TestData>;
        component1(): core.domain.Oseba;
        component2(): Array<core.domain.Naslov>;
        component3(): Array<core.data.UcenjeData>;
        component4(): Array<core.data.KontaktData>;
        component5(): Array<core.data.TestData>;
        copy(oseba?: core.domain.Oseba, naslov_refs?: Array<core.domain.Naslov>, ucenje_ucitelj_refs?: Array<core.data.UcenjeData>, kontakt_refs?: Array<core.data.KontaktData>, test_refs?: Array<core.data.TestData>): core.data.OsebaData;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static OsebaData_init_$Create$(seen1: number, oseba: Nullable<core.domain.Oseba>, naslov_refs: Nullable<Array<core.domain.Naslov>>, ucenje_ucitelj_refs: Nullable<Array<core.data.UcenjeData>>, kontakt_refs: Nullable<Array<core.data.KontaktData>>, test_refs: Nullable<Array<core.data.TestData>>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.data.OsebaData;
        static get Companion(): {
            decode(string: string): core.data.OsebaData;
            serializer(): any/* kotlinx.serialization.KSerializer<core.data.OsebaData> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.data.OsebaData> */;
    }
    class TestData {
        constructor(test: core.domain.Test, status_refs?: Array<core.data.StatusData>, opravljeno: number);
        get test(): core.domain.Test;
        get status_refs(): Array<core.data.StatusData>;
        get opravljeno(): number;
        set opravljeno(value: number);
        component1(): core.domain.Test;
        component2(): Array<core.data.StatusData>;
        component3(): number;
        copy(test?: core.domain.Test, status_refs?: Array<core.data.StatusData>, opravljeno?: number): core.data.TestData;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static TestData_init_$Create$(seen1: number, test: Nullable<core.domain.Test>, status_refs: Nullable<Array<core.data.StatusData>>, opravljeno: number, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.data.TestData;
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.data.TestData> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.data.TestData> */;
    }
    class StatusData {
        constructor(status: core.domain.Status, naloga_refs?: Array<core.data.NalogaData>);
        get status(): core.domain.Status;
        get naloga_refs(): Array<core.data.NalogaData>;
        component1(): core.domain.Status;
        component2(): Array<core.data.NalogaData>;
        copy(status?: core.domain.Status, naloga_refs?: Array<core.data.NalogaData>): core.data.StatusData;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static StatusData_init_$Create$(seen1: number, status: Nullable<core.domain.Status>, naloga_refs: Nullable<Array<core.data.NalogaData>>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.data.StatusData;
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.data.StatusData> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.data.StatusData> */;
    }
    class NalogaData {
        constructor(naloga: core.domain.Naloga, tematika_refs?: Array<core.domain.Tematika>);
        get naloga(): core.domain.Naloga;
        get tematika_refs(): Array<core.domain.Tematika>;
        component1(): core.domain.Naloga;
        component2(): Array<core.domain.Tematika>;
        copy(naloga?: core.domain.Naloga, tematika_refs?: Array<core.domain.Tematika>): core.data.NalogaData;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static NalogaData_init_$Create$(seen1: number, naloga: Nullable<core.domain.Naloga>, tematika_refs: Nullable<Array<core.domain.Tematika>>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.data.NalogaData;
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.data.NalogaData> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.data.NalogaData> */;
    }
    class KontaktData {
        constructor(kontakt: core.domain.Kontakt, oseba_refs?: Array<core.domain.Oseba>, sporocilo_prejemnik_refs?: Array<core.data.SporociloData>, sporocilo_posiljatelj_refs?: Array<core.data.SporociloData>);
        get kontakt(): core.domain.Kontakt;
        get oseba_refs(): Array<core.domain.Oseba>;
        get sporocilo_prejemnik_refs(): Array<core.data.SporociloData>;
        get sporocilo_posiljatelj_refs(): Array<core.data.SporociloData>;
        component1(): core.domain.Kontakt;
        component2(): Array<core.domain.Oseba>;
        component3(): Array<core.data.SporociloData>;
        component4(): Array<core.data.SporociloData>;
        copy(kontakt?: core.domain.Kontakt, oseba_refs?: Array<core.domain.Oseba>, sporocilo_prejemnik_refs?: Array<core.data.SporociloData>, sporocilo_posiljatelj_refs?: Array<core.data.SporociloData>): core.data.KontaktData;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static KontaktData_init_$Create$(seen1: number, kontakt: Nullable<core.domain.Kontakt>, oseba_refs: Nullable<Array<core.domain.Oseba>>, sporocilo_prejemnik_refs: Nullable<Array<core.data.SporociloData>>, sporocilo_posiljatelj_refs: Nullable<Array<core.data.SporociloData>>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.data.KontaktData;
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.data.KontaktData> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.data.KontaktData> */;
    }
    class SporociloData {
        constructor(sporocilo: core.domain.Sporocilo, kontakt_refs?: Array<core.data.KontaktData>);
        get sporocilo(): core.domain.Sporocilo;
        get kontakt_refs(): Array<core.data.KontaktData>;
        component1(): core.domain.Sporocilo;
        component2(): Array<core.data.KontaktData>;
        copy(sporocilo?: core.domain.Sporocilo, kontakt_refs?: Array<core.data.KontaktData>): core.data.SporociloData;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static SporociloData_init_$Create$(seen1: number, sporocilo: Nullable<core.domain.Sporocilo>, kontakt_refs: Nullable<Array<core.data.KontaktData>>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.data.SporociloData;
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.data.SporociloData> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.data.SporociloData> */;
    }
    class UcenjeData {
        constructor(ucenje: core.domain.Ucenje, oseba_refs?: Array<core.domain.Oseba>, ucenje_ucenec_refs?: Array<core.data.UcenjeData>);
        get ucenje(): core.domain.Ucenje;
        get oseba_refs(): Array<core.domain.Oseba>;
        get ucenje_ucenec_refs(): Array<core.data.UcenjeData>;
        component1(): core.domain.Ucenje;
        component2(): Array<core.domain.Oseba>;
        component3(): Array<core.data.UcenjeData>;
        copy(ucenje?: core.domain.Ucenje, oseba_refs?: Array<core.domain.Oseba>, ucenje_ucenec_refs?: Array<core.data.UcenjeData>): core.data.UcenjeData;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static UcenjeData_init_$Create$(seen1: number, ucenje: Nullable<core.domain.Ucenje>, oseba_refs: Nullable<Array<core.domain.Oseba>>, ucenje_ucenec_refs: Nullable<Array<core.data.UcenjeData>>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.data.UcenjeData;
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.data.UcenjeData> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.data.UcenjeData> */;
    }
}
export namespace core.domain {
    class Audit implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, entiteta_id?: Nullable<string>, opis: string, entiteta: string);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get entiteta_id(): Nullable<string>;
        set entiteta_id(value: Nullable<string>);
        get opis(): string;
        get entiteta(): string;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): string;
        component4(): string;
        copy(_id?: Nullable<string>, entiteta_id?: Nullable<string>, opis?: string, entiteta?: string): core.domain.Audit;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
    }
}
export namespace core.domain {
    interface Entiteta {
        _id: Nullable<string>;
        readonly __doNotUseOrImplementIt: {
            readonly "core.domain.Entiteta": unique symbol;
        };
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Entiteta> */;
        } & any/* kotlinx.serialization.internal.SerializerFactory */;
    }
}
export namespace core.domain {
    class Oseba implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, ime: string, priimek: string, username: string, tip: core.domain.Oseba.Tip);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get ime(): string;
        get priimek(): string;
        get username(): string;
        get tip(): core.domain.Oseba.Tip;
        component1(): Nullable<string>;
        component2(): string;
        component3(): string;
        component4(): string;
        component5(): core.domain.Oseba.Tip;
        copy(_id?: Nullable<string>, ime?: string, priimek?: string, username?: string, tip?: core.domain.Oseba.Tip): core.domain.Oseba;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Oseba_init_$Create$(seen1: number, _id: Nullable<string>, ime: Nullable<string>, priimek: Nullable<string>, username: Nullable<string>, tip: Nullable<core.domain.Oseba.Tip>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Oseba;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Oseba> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Oseba> */;
    }
    namespace Oseba {
        abstract class Tip {
            private constructor();
            static get UCENEC(): core.domain.Oseba.Tip & {
                get name(): "UCENEC";
                get ordinal(): 0;
            };
            static get UCITELJ(): core.domain.Oseba.Tip & {
                get name(): "UCITELJ";
                get ordinal(): 1;
            };
            static get INSTRUKTOR(): core.domain.Oseba.Tip & {
                get name(): "INSTRUKTOR";
                get ordinal(): 2;
            };
            static get ADMIN(): core.domain.Oseba.Tip & {
                get name(): "ADMIN";
                get ordinal(): 3;
            };
            static values(): Array<core.domain.Oseba.Tip>;
            static valueOf(value: string): core.domain.Oseba.Tip;
            get name(): "UCENEC" | "UCITELJ" | "INSTRUKTOR" | "ADMIN";
            get ordinal(): 0 | 1 | 2 | 3;
        }
    }
    class Kontakt implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, oseba_id?: Nullable<string>, data: string, tip: core.domain.Kontakt.Tip);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get oseba_id(): Nullable<string>;
        set oseba_id(value: Nullable<string>);
        get data(): string;
        get tip(): core.domain.Kontakt.Tip;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): string;
        component4(): core.domain.Kontakt.Tip;
        copy(_id?: Nullable<string>, oseba_id?: Nullable<string>, data?: string, tip?: core.domain.Kontakt.Tip): core.domain.Kontakt;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Kontakt_init_$Create$(seen1: number, _id: Nullable<string>, oseba_id: Nullable<string>, data: Nullable<string>, tip: Nullable<core.domain.Kontakt.Tip>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Kontakt;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Kontakt> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Kontakt> */;
    }
    namespace Kontakt {
        abstract class Tip {
            private constructor();
            static get EMAIL(): core.domain.Kontakt.Tip & {
                get name(): "EMAIL";
                get ordinal(): 0;
            };
            static get TELEFON(): core.domain.Kontakt.Tip & {
                get name(): "TELEFON";
                get ordinal(): 1;
            };
            static values(): Array<core.domain.Kontakt.Tip>;
            static valueOf(value: string): core.domain.Kontakt.Tip;
            get name(): "EMAIL" | "TELEFON";
            get ordinal(): 0 | 1;
        }
    }
    class Naslov implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, oseba_id?: Nullable<string>, drzava: string, mesto: string, ulica: string, zip: number, dodatno: string);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get oseba_id(): Nullable<string>;
        set oseba_id(value: Nullable<string>);
        get drzava(): string;
        get mesto(): string;
        get ulica(): string;
        get zip(): number;
        get dodatno(): string;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): string;
        component4(): string;
        component5(): string;
        component6(): number;
        component7(): string;
        copy(_id?: Nullable<string>, oseba_id?: Nullable<string>, drzava?: string, mesto?: string, ulica?: string, zip?: number, dodatno?: string): core.domain.Naslov;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Naslov_init_$Create$(seen1: number, _id: Nullable<string>, oseba_id: Nullable<string>, drzava: Nullable<string>, mesto: Nullable<string>, ulica: Nullable<string>, zip: number, dodatno: Nullable<string>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Naslov;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Naslov> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Naslov> */;
    }
    class Sporocilo implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, kontakt_posiljatelj_id?: Nullable<string>, kontakt_prejemnik_id?: Nullable<string>, vsebina: string);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get kontakt_posiljatelj_id(): Nullable<string>;
        set kontakt_posiljatelj_id(value: Nullable<string>);
        get kontakt_prejemnik_id(): Nullable<string>;
        set kontakt_prejemnik_id(value: Nullable<string>);
        get vsebina(): string;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): Nullable<string>;
        component4(): string;
        copy(_id?: Nullable<string>, kontakt_posiljatelj_id?: Nullable<string>, kontakt_prejemnik_id?: Nullable<string>, vsebina?: string): core.domain.Sporocilo;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Sporocilo_init_$Create$(seen1: number, _id: Nullable<string>, kontakt_posiljatelj_id: Nullable<string>, kontakt_prejemnik_id: Nullable<string>, vsebina: Nullable<string>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Sporocilo;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Sporocilo> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Sporocilo> */;
    }
}
export namespace core.domain {
    class Test implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, oseba_id?: Nullable<string>, naslov: string, podnaslov: string, deadline: any/* kotlinx.datetime.LocalDate */);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get oseba_id(): Nullable<string>;
        set oseba_id(value: Nullable<string>);
        get naslov(): string;
        get podnaslov(): string;
        get deadline(): any/* kotlinx.datetime.LocalDate */;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): string;
        component4(): string;
        component5(): any/* kotlinx.datetime.LocalDate */;
        copy(_id?: Nullable<string>, oseba_id?: Nullable<string>, naslov?: string, podnaslov?: string, deadline?: any/* kotlinx.datetime.LocalDate */): core.domain.Test;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Test_init_$Create$(seen1: number, _id: Nullable<string>, oseba_id: Nullable<string>, naslov: Nullable<string>, podnaslov: Nullable<string>, deadline: Nullable<any>/* Nullable<kotlinx.datetime.LocalDate> */, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Test;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Test> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Test> */;
    }
    class Status implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, naloga_id?: Nullable<string>, test_id?: Nullable<string>, tip: core.domain.Status.Tip, pojasnilo: string);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get naloga_id(): Nullable<string>;
        set naloga_id(value: Nullable<string>);
        get test_id(): Nullable<string>;
        set test_id(value: Nullable<string>);
        get tip(): core.domain.Status.Tip;
        get pojasnilo(): string;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): Nullable<string>;
        component4(): core.domain.Status.Tip;
        component5(): string;
        copy(_id?: Nullable<string>, naloga_id?: Nullable<string>, test_id?: Nullable<string>, tip?: core.domain.Status.Tip, pojasnilo?: string): core.domain.Status;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Status_init_$Create$(seen1: number, _id: Nullable<string>, naloga_id: Nullable<string>, test_id: Nullable<string>, tip: Nullable<core.domain.Status.Tip>, pojasnilo: Nullable<string>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Status;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Status> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Status> */;
    }
    namespace Status {
        abstract class Tip {
            private constructor();
            static get USPEH(): core.domain.Status.Tip & {
                get name(): "USPEH";
                get ordinal(): 0;
            };
            static get NEUSPEH(): core.domain.Status.Tip & {
                get name(): "NEUSPEH";
                get ordinal(): 1;
            };
            static values(): Array<core.domain.Status.Tip>;
            static valueOf(value: string): core.domain.Status.Tip;
            get name(): "USPEH" | "NEUSPEH";
            get ordinal(): 0 | 1;
        }
    }
}
export namespace core.domain {
    class Ucenje implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, oseba_ucenec_id?: Nullable<string>, oseba_ucitelj_id?: Nullable<string>);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get oseba_ucenec_id(): Nullable<string>;
        set oseba_ucenec_id(value: Nullable<string>);
        get oseba_ucitelj_id(): Nullable<string>;
        set oseba_ucitelj_id(value: Nullable<string>);
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): Nullable<string>;
        copy(_id?: Nullable<string>, oseba_ucenec_id?: Nullable<string>, oseba_ucitelj_id?: Nullable<string>): core.domain.Ucenje;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Ucenje_init_$Create$(seen1: number, _id: Nullable<string>, oseba_ucenec_id: Nullable<string>, oseba_ucitelj_id: Nullable<string>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Ucenje;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Ucenje> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Ucenje> */;
    }
}
export namespace core.domain {
    class Zvezek implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, tip: core.domain.Zvezek.Tip, naslov: string);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get tip(): core.domain.Zvezek.Tip;
        get naslov(): string;
        component1(): Nullable<string>;
        component2(): core.domain.Zvezek.Tip;
        component3(): string;
        copy(_id?: Nullable<string>, tip?: core.domain.Zvezek.Tip, naslov?: string): core.domain.Zvezek;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Zvezek_init_$Create$(seen1: number, _id: Nullable<string>, tip: Nullable<core.domain.Zvezek.Tip>, naslov: Nullable<string>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Zvezek;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Zvezek> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Zvezek> */;
    }
    namespace Zvezek {
        abstract class Tip {
            private constructor();
            static get DELOVNI(): core.domain.Zvezek.Tip & {
                get name(): "DELOVNI";
                get ordinal(): 0;
            };
            static get TEORETSKI(): core.domain.Zvezek.Tip & {
                get name(): "TEORETSKI";
                get ordinal(): 1;
            };
            static values(): Array<core.domain.Zvezek.Tip>;
            static valueOf(value: string): core.domain.Zvezek.Tip;
            get name(): "DELOVNI" | "TEORETSKI";
            get ordinal(): 0 | 1;
        }
    }
    class Naloga implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, tematika_id?: Nullable<string>, resitev: string, vsebina: string);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get tematika_id(): Nullable<string>;
        set tematika_id(value: Nullable<string>);
        get resitev(): string;
        get vsebina(): string;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): string;
        component4(): string;
        copy(_id?: Nullable<string>, tematika_id?: Nullable<string>, resitev?: string, vsebina?: string): core.domain.Naloga;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Naloga_init_$Create$(seen1: number, _id: Nullable<string>, tematika_id: Nullable<string>, resitev: Nullable<string>, vsebina: Nullable<string>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Naloga;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Naloga> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Naloga> */;
    }
    class Tematika implements core.domain.Entiteta {
        constructor(_id?: Nullable<string>, zvezek_id?: Nullable<string>, naslov: string);
        get _id(): Nullable<string>;
        set _id(value: Nullable<string>);
        get zvezek_id(): Nullable<string>;
        set zvezek_id(value: Nullable<string>);
        get naslov(): string;
        component1(): Nullable<string>;
        component2(): Nullable<string>;
        component3(): string;
        copy(_id?: Nullable<string>, zvezek_id?: Nullable<string>, naslov?: string): core.domain.Tematika;
        toString(): string;
        hashCode(): number;
        equals(other: Nullable<any>): boolean;
        static Tematika_init_$Create$(seen1: number, _id: Nullable<string>, zvezek_id: Nullable<string>, naslov: Nullable<string>, serializationConstructorMarker: Nullable<any>/* Nullable<kotlinx.serialization.internal.SerializationConstructorMarker> */): core.domain.Tematika;
        readonly __doNotUseOrImplementIt: core.domain.Entiteta["__doNotUseOrImplementIt"];
        static get Companion(): {
            serializer(): any/* kotlinx.serialization.KSerializer<core.domain.Tematika> */;
        };
        static get $serializer(): {
        } & any/* kotlinx.serialization.internal.GeneratedSerializer<core.domain.Tematika> */;
    }
}