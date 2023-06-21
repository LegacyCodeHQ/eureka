import { Link } from './Link';

class Cluster {
  constructor(public links: Link[]) {
    // empty
  }

  static from(classLinks: Link[], startNodeId: string, stopNodeId: string | null = null): Cluster {
    const visited = new Set<string>();
    const connectedLinks = new Set<Link>();
    const stack: string[] = [startNodeId];

    while (stack.length > 0) {
      const currentNodeId = stack.pop()!;
      visited.add(currentNodeId);

      const relatedLinks = classLinks.filter((link) => link.source === currentNodeId || link.target === currentNodeId);

      relatedLinks.forEach((link) => connectedLinks.add(link));

      for (const link of relatedLinks) {
        const nextNodeId = link.source === currentNodeId ? link.target : link.source;
        if (!visited.has(nextNodeId) && !stack.includes(nextNodeId)) {
          stack.push(nextNodeId);
        }
      }
    }

    if (stopNodeId) {
      const filteredLinks = Array.from(connectedLinks).filter(
        (link) =>
          link.source !== stopNodeId ||
          (link.source === stopNodeId && pathExists(link.target, startNodeId, classLinks)),
      );
      return new Cluster(filteredLinks);
    }

    return new Cluster(Array.from(connectedLinks));
  }
}

function pathExists(startNodeId: string, destinationNodeId: string, classLinks: Link[]) {
  const visited = new Set();
  const stack = [startNodeId];

  while (stack.length > 0) {
    const currentNodeId = stack.pop();
    visited.add(currentNodeId);

    const relatedLinks = classLinks.filter((link) => link.source === currentNodeId || link.target === currentNodeId);

    for (const link of relatedLinks) {
      const nextNodeId = link.source === currentNodeId ? link.target : link.source;
      if (nextNodeId === destinationNodeId) {
        return true;
      }
      if (!visited.has(nextNodeId) && !stack.includes(nextNodeId)) {
        stack.push(nextNodeId);
      }
    }
  }

  return false;
}

export default Cluster;
