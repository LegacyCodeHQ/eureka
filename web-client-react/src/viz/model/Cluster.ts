import { Link } from './Link';

class Cluster {
  constructor(private classLinks: Link[]) {
    // empty
  }

  static from(classLinks: Link[], startNode: string): Cluster {
    const filteredLinks = classLinks.filter((link) => link.source === startNode || link.target === startNode);
    return new Cluster(filteredLinks);
  }

  links(): Link[] {
    return this.classLinks;
  }
}

export default Cluster;
