/* tslint:disable */
/* eslint-disable */
import { AnyId } from './any-id';
import { LocalDateTime } from './local-date-time';
export interface Audit {
  '_id'?: string;
  entiteta?: string;
  entitete_id?: Array<AnyId>;
  opis?: string;
  tip?: 'STATUS_TIP_POSODOBITEV' | 'TEST_DATUM_POSODOBITEV';
  trajanje?: number;
  ustvarjeno?: LocalDateTime;
}
