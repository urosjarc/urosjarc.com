import {DateOddaljenostPipe} from "./date-oddaljenost/date-oddaljenost.pipe";
import {DateOddaljenostClassPipe} from "./date-oddaljenost-class/date-oddaljenost-class.pipe";
import {DurationPipe} from "./duration/duration.pipe";
import {SekundeStoparicaPipe} from "./sekunde-stoparica/sekunde-stoparica.pipe";
import {StatusTipStylePipe} from "./statusTip-style/statusTip-style.pipe";
import {Pipe} from "@angular/core";
import {DatePipe, PercentPipe} from "@angular/common";

export const ui_pipes: Pipe[] = [
  DatePipe,
  PercentPipe,
  DateOddaljenostPipe,
  DateOddaljenostClassPipe,
  DurationPipe,
  SekundeStoparicaPipe,
  StatusTipStylePipe
]
