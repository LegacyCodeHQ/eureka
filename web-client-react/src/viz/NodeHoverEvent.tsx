export type Count = {
  dependencies: number;
  dependents: number;
};

export interface NodeHoverEvent {
  name: string;
  count: Count;
}
