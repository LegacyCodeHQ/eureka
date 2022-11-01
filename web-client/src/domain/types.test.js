require('chai').should()

function shorten(className) {
  return className;
}

describe('a qualified type name', () => {
  it('should return the same name if it is in the default package', function () {
    const className = 'ClassName'
    const actual = shorten(className)
    actual.should.equal(className)
  });

  it('should return the same name if it is within 32 chars', function () {
    const className = 'com.example.ClassName'
    const actual = shorten(className)
    actual.should.equal(className)
  });
});
