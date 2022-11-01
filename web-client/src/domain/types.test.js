require('chai')
  .should();

function shorten(className) {
  if (className.length > 32) {
    let parts = className.split('.');
    let shortenedPackageName = parts.slice(0, parts.length - 1)
      .map(part => part[0])
      .join('.');
    let simpleClassName = parts.slice(parts.length - 1);
    return shortenedPackageName + '.' + simpleClassName;
  }
  return className;
}

describe('a qualified type name', () => {
  it('should return the same name if it is in the default package', function () {
    const className = 'ClassName';
    const actual = shorten(className);
    actual.should.equal(className);
  });

  it('should return the same name if it is within 32 chars', function () {
    const className = 'com.example.ClassName';
    const actual = shorten(className);
    actual.should.equal(className);
  });

  it('should return a shortened package name if it is beyond 32 chars', function () {
    const className = 'com.example.viz.graphs.bundling.ClassName';
    const actual = shorten(className);
    actual.should.equal('c.e.v.g.b.ClassName');
  });
});
