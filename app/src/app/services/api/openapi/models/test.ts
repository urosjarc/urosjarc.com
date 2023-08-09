/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { Id } from './id';
import { LocalDate } from './local-date';
import { ObjectId } from './object-id';
export interface Test {
  '_id'?: ObjectId;
  deadline?: LocalDate;
  naloga_id?: Array<Id>;
  naslov?: BsonBinary;
  oseba_admin_id?: Array<Id>;
  oseba_ucenec_id?: Array<Id>;
  podnaslov?: BsonBinary;
}
