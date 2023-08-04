/* tslint:disable */
/* eslint-disable */
import { AnyId } from './any-id';
import { LocalDateTime } from './local-date-time';
export interface Napaka {
  '_id'?: string;
  dodatno?: string;
  entitete_id?: Array<AnyId>;
  tip?: 'ERROR' | 'WARN' | 'FATAL';
  ustvarjeno?: LocalDateTime;
  vsebina?: string;
}
