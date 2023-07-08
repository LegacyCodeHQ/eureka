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

  classStats(): ClassStats {
    return new ClassStats(this.methods().length, this.fields().length, this.links.length);
  }

  private methods(): Node[] {
    return this.nodes.filter((node) => node.id.endsWith(')'));
  }

  private fields(): Node[] {
    return this.nodes.filter((node) => !node.id.endsWith(')'));
  }
}

export class ClassStats {
  constructor(
    public readonly fieldCount: number,
    public readonly methodCount: number,
    public readonly relationshipsCount: number,
  ) {
    // empty
  }
}
