/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { ObjectId } from './object-id';
export interface Tematika {
  '_id'?: ObjectId;
  naslov?: BsonBinary;
  zvezek_id?: ObjectId;
}
