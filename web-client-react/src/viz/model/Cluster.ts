import { Link } from './Link';

class Cluster {
  constructor(private classLinks: Link[]) {
    // empty
  }

  static from(classLinks: Link[], startNode: string): Cluster {
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

    return new Cluster(Array.from(connectedLinks));
  }

  links(): Link[] {
    return this.classLinks;
  }
}

export default Cluster;
