import {Test} from "../services/api/models/test";

export interface TestModel {
  test: Test,
  opravljeno: number,
  deadline: Date,
}
