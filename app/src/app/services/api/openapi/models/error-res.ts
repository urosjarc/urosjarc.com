/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
export interface ErrorRes {
  info?: BsonBinary;
  napaka?: 'UPORABNISKA' | 'SISTEMSKA';
  status?: BsonBinary;
}
