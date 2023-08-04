/* tslint:disable */
/* eslint-disable */
import { NalogaData } from './naloga-data';
import { Status } from './status';
import { Test } from './test';
export interface TestData {
  naloga_refs?: Array<NalogaData>;
  status_refs?: Array<Status>;
  test?: Test;
}
