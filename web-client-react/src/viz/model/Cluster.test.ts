import Cluster from './Cluster';
import { Link } from './Link';
import { graphDataJson } from '../../datasource/SampleData';

describe('Cluster', () => {
  it('should handle an empty link array', () => {
    // given
    const emptyLinks: Link[] = [];
    const cluster = Cluster.from(emptyLinks, 'void main()');

    // when & then
    expect(cluster.links).toEqual(emptyLinks);
  });

  it('should identify a network with a single relationship', () => {
    // given
    const singleLink: Link[] = [{ source: 'void main()', target: 'add(Int, Int): Int', value: 1 }];
    const cluster = Cluster.from(singleLink, 'void main()');

    // when & then
    expect(cluster.links).toEqual(singleLink);
  });

  it('should identify a network with multiple relationships', () => {
    // given
    const multipleLinks: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 2 },
    ];
    const cluster = Cluster.from(multipleLinks, 'void main()');

    // when & then
    expect(cluster.links).toEqual(multipleLinks);
  });

  it('should ignore relationships that are not connected to the network involving the start node', () => {
    // given
    const multipleLinks: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
      { source: 'multiply(Int, Int): Int', target: 'round(Double): Int', value: 1 },
    ];
    const cluster = Cluster.from(multipleLinks, 'void main()');

    // when & then
    const expectedResult: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
    ];
    expect(cluster.links).toEqual(expectedResult);
  });

  it(`should include relationships that are connected to the start node's network`, () => {
    // given
    const multipleLinks: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
      { source: 'multiply(Int, Int): Int', target: 'add(Int, Int): Int', value: 1 },
    ];
    const cluster = Cluster.from(multipleLinks, 'void main()');

    // when & then
    const expectedResult: Link[] = [
      { source: 'void main()', target: 'add(Int, Int): Int', value: 1 },
      { source: 'void main()', target: 'subtract(Int, Int): Int', value: 1 },
      { source: 'multiply(Int, Int): Int', target: 'add(Int, Int): Int', value: 1 },
    ];
    expect(cluster.links).toEqual(expectedResult);
  });

  it('should traverse nodes that are part of the start node but that are exclusively part of the blocked node', () => {
    // given
    const multipleLinks: Link[] = [
      { source: 'onViewCreated', target: 'chrome', value: 1 },
      { source: 'hideChromeImmediate', target: 'chrome', value: 1 },
      { source: 'hideChromeImmediate', target: 'animatorSet', value: 1 },
      { source: 'animateChrome', target: 'chrome', value: 1 },
      { source: 'onViewCreated', target: 'hideChromeImmediate', value: 1 },
      { source: 'onViewCreated', target: 'resumeProgress', value: 1 },
      { source: 'onViewCreated', target: 'pauseProgress', value: 1 },
      { source: 'onViewCreated', target: 'onReadyToAnimate', value: 1 },
      { source: 'onViewCreated', target: 'setupProgressBar', value: 1 },
      { source: 'createUi', target: 'setupProgressBar', value: 1 },
    ];
    const cluster = Cluster.from(multipleLinks, 'chrome', 'onViewCreated');

    // when & then
    const expectedResult: Link[] = [
      { source: 'onViewCreated', target: 'chrome', value: 1 },
      { source: 'hideChromeImmediate', target: 'chrome', value: 1 },
      { source: 'animateChrome', target: 'chrome', value: 1 },
      { source: 'hideChromeImmediate', target: 'animatorSet', value: 1 },
      { source: 'onViewCreated', target: 'hideChromeImmediate', value: 1 },
    ];
    expect(cluster.links).toEqual(expectedResult);
  });

  it('should identify a cluster from a large network', () => {
    // given
    const multipleLinks: Link[] = JSON.parse(graphDataJson).links;
    const cluster = Cluster.from(multipleLinks, 'List chrome', 'void onViewCreated(View, Bundle)');

    // when & then
    const expectedResult: Link[] = [
      { source: 'void onViewCreated(View, Bundle)', target: 'List chrome', value: 1 },
      { source: 'void hideChromeImmediate()', target: 'List chrome', value: 1 },
      { source: 'void animateChrome(float)', target: 'List chrome', value: 1 },
      { source: 'void hideChrome()', target: 'void animateChrome(float)', value: 1 },
      { source: 'void animateChrome(float)', target: 'AnimatorSet animatorSet', value: 1 },
      { source: 'void showChrome()', target: 'void animateChrome(float)', value: 1 },
      { source: 'void onViewCreated(View, Bundle)', target: 'void showChrome()', value: 1 },
      { source: 'void hideChromeImmediate()', target: 'AnimatorSet animatorSet', value: 1 },
      { source: 'void onViewCreated(View, Bundle)', target: 'void hideChrome()', value: 1 },
      { source: 'void onViewCreated(View, Bundle)', target: 'void hideChromeImmediate()', value: 1 },
    ];
    expect(cluster.links).toEqual(expectedResult);
  });
});
