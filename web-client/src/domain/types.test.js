require('chai')
  .should();

const { shortenType } = require('./types.js');

describe('a qualified type name', () => {
  it('should return the same name if it is in the default package', function () {
    const className = 'ClassName';
    const actual = shortenType(className);
    actual.should.equal('ClassName');
  });

  it('should return the same name if it is within 32 chars', function () {
    const className = 'com.example.ClassName';
    const actual = shortenType(className);
    actual.should.equal('com.example.ClassName');
  });

  it('should return a shortened package name if it is beyond 32 chars', function () {
    const className = 'com.example.viz.graphs.bundling.ClassName';
    const actual = shortenType(className);
    actual.should.equal('c.e.v.g.b.ClassName');
  });

  it('should return a shortened class name for classes in default packages', function () {
    const className = 'ThisClassIsInTheDefaultPackageButIsLong';
    const actual = shortenType(className);
    actual.should.equal('ThisClassIsInThe...PackageButIsLong');
  });
});
