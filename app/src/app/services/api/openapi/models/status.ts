/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { ObjectId } from './object-id';
export interface Status {
  '_id'?: ObjectId;
  naloga_id?: ObjectId;
  oseba_id?: ObjectId;
  pojasnilo?: BsonBinary;
  test_id?: ObjectId;
  tip?: 'NEZACETO' | 'NERESENO' | 'NAPACNO' | 'PRAVILNO';
}
