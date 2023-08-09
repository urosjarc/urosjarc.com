/* tslint:disable */
/* eslint-disable */
import { BsonBinary } from './bson-binary';
import { Id } from './id';
import { LocalDateTime } from './local-date-time';
import { ObjectId } from './object-id';
export interface Sporocilo {
  '_id'?: ObjectId;
  kontakt_posiljatelj_id?: ObjectId;
  kontakt_prejemnik_id?: Array<Id>;
  poslano?: LocalDateTime;
  vsebina?: BsonBinary;
}
