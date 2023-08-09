/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { ObjectId } from './object-id';
export interface Zvezek {
  '_id'?: ObjectId;
  naslov?: BsonBinary;
  tip?: 'DELOVNI' | 'TEORETSKI';
}
