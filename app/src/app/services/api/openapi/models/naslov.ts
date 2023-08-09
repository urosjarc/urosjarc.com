/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { ObjectId } from './object-id';
export interface Naslov {
  '_id'?: ObjectId;
  dodatno?: BsonBinary;
  drzava?: BsonBinary;
  mesto?: BsonBinary;
  oseba_id?: ObjectId;
  ulica?: BsonBinary;
  zip?: BsonBinary;
}
