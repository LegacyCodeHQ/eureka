import { Link } from './Link';

class Cluster {
  constructor(public links: Link[]) {
    // empty
  }

  static from(classLinks: Link[], startNode: string, stopNode: string | null = null): Cluster {
    const visited = new Set<string>();
    const connectedLinks = new Set<Link>();
    const stack: string[] = [startNode];

    while (stack.length > 0) {
      const currentNode = stack.pop()!;
      visited.add(currentNode);

      const relatedLinks = classLinks.filter((link) => link.source === currentNode || link.target === currentNode);

      relatedLinks.forEach((link) => connectedLinks.add(link));

      for (const link of relatedLinks) {
        const nextNode = link.source === currentNode ? link.target : link.source;
        if (!visited.has(nextNode) && !stack.includes(nextNode)) {
          stack.push(nextNode);
        }
      }
    }

    if (stopNode) {
      const filteredLinks = Array.from(connectedLinks).filter(
        (link) =>
          link.source !== stopNode || (link.source === stopNode && pathExists(link.target, startNode, classLinks)),
      );
      return new Cluster(filteredLinks);
    }

    return new Cluster(Array.from(connectedLinks));
  }
}

function pathExists(startNode: string, destinationNode: string, classLinks: Link[]) {
  const visited = new Set();
  const stack = [startNode];

  while (stack.length > 0) {
    const currentNode = stack.pop();
    visited.add(currentNode);

    const relatedLinks = classLinks.filter((link) => link.source === currentNode || link.target === currentNode);

    for (const link of relatedLinks) {
      const nextNode = link.source === currentNode ? link.target : link.source;
      if (nextNode === destinationNode) {
        return true;
      }
      if (!visited.has(nextNode) && !stack.includes(nextNode)) {
        stack.push(nextNode);
      }
    }
  }

  return false;
}

export default Cluster;
