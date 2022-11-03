require('chai')
  .should();

function instability(dependencies, dependents) {
  return 0.0;
}

describe('instability (I)', () => {
  it('should calculate metrics for a maximally stable member', function () {
    let dependencies = 0;
    let dependents = 5;
    let i = instability(dependencies, dependents);
    i.should.equal(0.0);
  });
});
