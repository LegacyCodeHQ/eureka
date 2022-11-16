require('chai').should();

describe('a test environment', () => {
  it('should have a test runner', () => {
    true.should.be.true;
  });

  it('should have snapshot testing', () => {
    expect('Hello, world!').toMatchSnapshot();
  });
});
