/* tslint:disable */
/* eslint-disable */
import { Tematika } from './tematika';
import { ZvezekData } from './zvezek-data';
export interface TematikaData {
  tematika: Tematika;
  zvezek_refs: Array<ZvezekData>;
}
