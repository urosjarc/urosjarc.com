/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
export interface NapakaReq {
  tip?: 'ERROR' | 'WARN' | 'FATAL';
  vsebina?: BsonBinary;
}
