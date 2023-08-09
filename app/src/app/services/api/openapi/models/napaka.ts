/* tslint:disable */
/* eslint-disable */
import { AnyId } from './any-id';
import { BsonBinary } from './bson-binary';
import { LocalDateTime } from './local-date-time';
import { ObjectId } from './object-id';
export interface Napaka {
  '_id'?: ObjectId;
  dodatno?: BsonBinary;
  entitete_id?: Array<AnyId>;
  tip?: 'ERROR' | 'WARN' | 'FATAL';
  ustvarjeno?: LocalDateTime;
  vsebina?: BsonBinary;
}
