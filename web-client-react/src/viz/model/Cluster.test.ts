import Cluster from './Cluster';
import { Link } from './Link';

describe('Cluster', () => {
  it('should handle an empty link array', () => {
    // given
    const emptyLinks: Link[] = [];
    const cluster = Cluster.from(emptyLinks, 'void main()');

    // when
    const result = cluster.links();

    // then
    expect(result).toEqual(emptyLinks);
  });

  it('should identify a network with a single relationship', () => {
    // given
    const singleLink: Link[] = [{ source: 'void main()', target: 'add(Int, Int): Int', value: 1 }];
    const cluster = Cluster.from(singleLink, 'void main()');

    // when
    const result = cluster.links();

    // then
    expect(result).toEqual(singleLink);
  });

  it('should identify a network with multiple relationships', () => {
    // given
    const multipleLinks: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 2 },
    ];
    const cluster = Cluster.from(multipleLinks, 'void main()');

    // when
    const result = cluster.links();

    // then
    expect(result).toEqual(multipleLinks);
  });

  it('should ignore relationships that are not connected to the network involving the start node', () => {
    // given
    const multipleLinks: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
      { source: 'multiply(Int, Int): Int', target: 'round(Double): Int', value: 1 },
    ];
    const cluster = Cluster.from(multipleLinks, 'void main()');

    // when
    const result = cluster.links();

    // then
    const expectedResult: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
    ];
    expect(result).toEqual(expectedResult);
  });

  it(`should include relationships that are connected to the start node's network`, () => {
    // given
    const multipleLinks: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
      { source: 'multiply(Int, Int): Int', target: 'add(Int, Int): Int', value: 1 },
    ];
    const cluster = Cluster.from(multipleLinks, 'void main()');

    // when
    const result = cluster.links();

    // then
    const expectedResult: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
      { source: 'multiply(Int, Int): Int', target: 'add(Int, Int): Int', value: 1 },
    ];
    expect(result).toEqual(expectedResult);
  });
});
