/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { ObjectId } from './object-id';
export interface Oseba {
  '_id'?: ObjectId;
  geslo?: BsonBinary;
  ime?: BsonBinary;
  priimek?: BsonBinary;
  tip?: Array<'UCENEC' | 'UCITELJ' | 'INSTRUKTOR' | 'ADMIN' | 'KONTAKT' | 'SERVER'>;
  username?: BsonBinary;
}
