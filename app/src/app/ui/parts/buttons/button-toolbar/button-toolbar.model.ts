export interface ButtonToolbarModel {
  tekst: string
  ikona: string
  route?: string
  style?: string
  onClick?: (() => void)
}
