/* tslint:disable */
/* eslint-disable */
import { LocalDate } from './local-date';
import { ObjectId } from './object-id';
export interface Ucenje {
  '_id'?: ObjectId;
  oseba_ucenec_id?: ObjectId;
  oseba_ucitelj_id?: ObjectId;
  razred?: string;
  ustvarjeno?: LocalDate;
}
