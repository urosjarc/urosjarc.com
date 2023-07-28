export interface Alert {
  vsebina: string,
  tip: AlertTip,
  trajanje: number
}

export enum AlertTip {
  INFO = 0,
  WARN = 1,
  ERROR = 2
}
