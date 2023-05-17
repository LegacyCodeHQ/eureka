import { Link } from './Link';
import { Node } from './Node';
import { Meta } from './Meta';

export interface GraphData {
  nodes: Node[];
  links: Link[];
  meta: Meta;
}
