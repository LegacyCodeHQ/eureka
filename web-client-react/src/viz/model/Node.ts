export interface Node {
  id: string;
  group: number;
  targets: any[]; /* incoming data does not have this field, the `toChartData` function sets this value. */
}
