/* tslint:disable */
/* eslint-disable */
import { AnyId } from './any-id';
import { BsonBinary } from './bson-binary';
import { LocalDateTime } from './local-date-time';
import { ObjectId } from './object-id';
export interface Audit {
  '_id'?: ObjectId;
  entiteta?: BsonBinary;
  entitete_id?: Array<AnyId>;
  opis?: BsonBinary;
  tip?: 'STATUS_TIP_POSODOBITEV' | 'TEST_DATUM_POSODOBITEV';
  trajanje?: number;
  ustvarjeno?: LocalDateTime;
}
