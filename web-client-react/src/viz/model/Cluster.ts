import { Link } from './Link';

class Cluster {
  constructor(public links: Link[]) {
    // empty
  }

  static from(classLinks: Link[], startNodeId: string, blockedNodeIds: string[] = []): Cluster {
    const visited = new Set<string>();
    const connectedLinks = new Set<Link>();
    const stack: string[] = [startNodeId];
    const blockedNodeId = blockedNodeIds.length > 0 ? blockedNodeIds[0] : null;

    while (stack.length > 0) {
      const currentNodeId = stack.pop()!;
      visited.add(currentNodeId);

      const relatedLinks = classLinks.filter((link) => link.source === currentNodeId || link.target === currentNodeId);
      relatedLinks.forEach((link) => connectedLinks.add(link));

      for (const link of relatedLinks) {
        const nextNodeId = link.source !== currentNodeId ? link.source : link.target;
        if (!visited.has(nextNodeId) && !stack.includes(nextNodeId) && nextNodeId !== blockedNodeId) {
          stack.push(nextNodeId);
        }
      }
    }

    if (blockedNodeId) {
      const filteredLinks = Array.from(connectedLinks).filter(
        (link) =>
          link.source !== blockedNodeId ||
          (link.source === blockedNodeId && pathExists(startNodeId, link.target, blockedNodeId, classLinks)),
      );
      return new Cluster(removeDuplicates(filteredLinks));
    }

    return new Cluster(Array.from(connectedLinks));
  }
}

function pathExists(
  startNodeId: string,
  destinationNodeId: string,
  blockedNodeId: string,
  classLinks: Link[],
): boolean {
  const visited = new Set<string>();
  const stack = [startNodeId];

  while (stack.length > 0) {
    const currentNodeId = stack.pop()!;
    visited.add(currentNodeId);

    const relatedLinks = classLinks.filter((link) => link.source === currentNodeId || link.target === currentNodeId);

    for (const link of relatedLinks) {
      const nextNodeId = link.source === currentNodeId ? link.target : link.source;
      if (nextNodeId === destinationNodeId) {
        return true;
      }
      if (nextNodeId !== blockedNodeId && !visited.has(nextNodeId) && !stack.includes(nextNodeId)) {
        stack.push(nextNodeId);
      }
    }
  }

  return false;
}

function removeDuplicates(links: Link[]): Link[] {
  const uniqueLinks: Link[] = [];
  const visitedLinks = new Set<string>();

  for (const link of links) {
    const linkKey = `${link.source}-${link.target}-${link.value}`;

    if (!visitedLinks.has(linkKey)) {
      uniqueLinks.push(link);
      visitedLinks.add(linkKey);
    }
  }

  return uniqueLinks;
}

export default Cluster;
