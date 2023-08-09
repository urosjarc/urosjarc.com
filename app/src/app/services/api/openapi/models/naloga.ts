/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { ObjectId } from './object-id';
export interface Naloga {
  '_id'?: ObjectId;
  resitev?: BsonBinary;
  tematika_id?: ObjectId;
  vsebina?: BsonBinary;
}
