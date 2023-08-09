/* tslint:disable */
/* eslint-disable */
import { ObjectId } from './object-id';
export interface Profil {
  id?: ObjectId;
  tip?: Array<'UCENEC' | 'UCITELJ' | 'INSTRUKTOR' | 'ADMIN' | 'KONTAKT' | 'SERVER'>;
}
