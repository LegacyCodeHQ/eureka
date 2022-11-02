require('chai')
  .should();

const {
  compressType,
  sortTypes,
  getPackageName,
  getClassName,
} = require('./types.js');

describe('a qualified type name', () => {
  it('should return the same name if it is in the default package', function () {
    const className = 'ClassName';
    const actual = compressType(className);
    actual.should.equal('ClassName');
  });

  it('should return the same name if it is within 32 chars', function () {
    const className = 'com.example.ClassName';
    const actual = compressType(className);
    actual.should.equal('com.example.ClassName');
  });

  it('should return a shortened package name if it is beyond 32 chars', function () {
    const className = 'com.example.viz.graphs.bundling.ClassName';
    const actual = compressType(className);
    actual.should.equal('c.e.v.g.b.ClassName');
  });

  it('should return a shortened class name for classes in default packages', function () {
    const className = 'ThisClassIsInTheDefaultPackageButIsLong';
    const actual = compressType(className);
    actual.should.equal('ThisClassIsInThe...PackageButIsLong');
  });
});

describe('a package name', () => {
  it('should return null for types from the default package', function () {
    const className = 'ClassName';
    const actual = getPackageName(className);
    (actual == null).should.be.true;
  });

  it('should return the package name for types from a non-default package', function () {
    const className = 'com.example.ClassName';
    const actual = getPackageName(className);
    actual.should.equal('com.example');
  });
});

describe('a type name', () => {
  it('should return the simple name for types from the default package', function () {
    const className = 'ClassName';
    const actual = getClassName(className);
    actual.should.equal('ClassName');
  });

  it('should return simple name for types from non-default packages', function () {
    const className = 'com.example.ClassName';
    const actual = getClassName(className);
    actual.should.equal('ClassName');
  });
});
