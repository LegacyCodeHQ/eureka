import { Link } from './Link';
import { Node } from './Node';
import { Meta } from './Meta';

export class GraphData {
  constructor(public nodes: Node[], public links: Link[], public meta: Meta) {
    // empty
  }

  members(): string[] {
    return this.nodes.map((node) => node.id);
  }
}
