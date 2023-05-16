export interface GraphData {
  nodes: Node[];
  links: Link[];
}

interface Node {
  id: string;
  group: number;
  targets: any[]; /* incoming data does not have this field, the `toChartData` function sets this value. */
}

interface Link {
  source: String;
  target: String;
  value: number;
}

export function toChartData(graphData: GraphData): any {
  const {nodes, links} = graphData;
  const groupById = new Map();
  const nodeById = new Map(nodes.map(node => [node.id, node]));

  for (const node of nodes) {
    let group = groupById.get(node.group);
    if (!group) {
      groupById.set(node.group, group = {id: node.group, children: []});
    }
    group.children.push(node);
    node.targets = [];
  }

  for (const {source: sourceId, target: targetId} of links) {
    let sourceNode = nodeById.get(sourceId.toString());
    if (sourceNode) {
      sourceNode.targets.push(targetId);
    }
  }

  return { children: [...groupById.values()] }
}
