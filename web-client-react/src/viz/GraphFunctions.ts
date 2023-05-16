import * as d3 from 'd3';
import { ClusterLayout, HierarchyNode, HierarchyPointNode } from 'd3';
import { GraphData } from './model/GraphData';

function bilink(root: any): any {
  const map: Map<string, HierarchyNode<any>> = new Map(root.leaves().map((d: any) => [d.data.id, d]));
  for (const d of root.leaves()) {
    d.dependents = [];
    d.dependencies = d.data.targets.map((i: any) => [d, map.get(i)]);
  }

  for (const d of root.leaves()) {
    for (const o of d.dependencies) {
      o[1].dependents.push(o);
    }
  }

  return root;
}

function toChartData(graphData: GraphData): any {
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

  return {children: [...groupById.values()]};
}

function tree(radius: number): ClusterLayout<any> {
  return d3.cluster()
    .size([2 * Math.PI, radius - 100]);
}

export function createRoot(graphData: GraphData, radius: number): HierarchyPointNode<any> {
  return tree(radius)(bilink(d3.hierarchy(toChartData(graphData))))
    .sort((a, b) => d3.ascending(a.height, b.height) || d3.ascending(a.data.id, b.data.id));
}

export const line = d3.lineRadial()
  .curve(d3.curveBundle.beta(0.85))
  .radius((d: any) => d.y)
  .angle((d: any) => d.x);

export function parseGraphData(graphDataJson: string): GraphData {
  return JSON.parse(graphDataJson);
}
