require('chai').should()

function shorten(className) {
  return className;
}

describe('a class name', () => {
  it('should return the same name if it is in the default package', function () {
    const className = 'ClassName'
    const actual = shorten(className)
    actual.should.equal(className)
  });
});
