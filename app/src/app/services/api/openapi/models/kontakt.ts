/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { Id } from './id';
import { ObjectId } from './object-id';
export interface Kontakt {
  '_id'?: ObjectId;
  data?: BsonBinary;
  oseba_id?: Array<Id>;
  tip?: 'EMAIL' | 'TELEFON';
}
