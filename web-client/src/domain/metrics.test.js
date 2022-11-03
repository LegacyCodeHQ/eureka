require('chai')
  .should();

function instability(dependencies, dependents) {
  return dependencies / (dependencies + dependents);
}

describe('instability (I)', () => {
  it('should calculate metrics for a maximally stable member', function () {
    let dependencies = 0;
    let dependents = 5;
    let i = instability(dependencies, dependents);
    i.should.equal(0.0);
  });

  it('should calculate metrics for a maximally unstable member', function () {
    let dependencies = 5;
    let dependents = 0;
    let i = instability(dependencies, dependents);
    i.should.equal(1.0);
  });
});
