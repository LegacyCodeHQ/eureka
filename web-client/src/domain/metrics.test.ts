require('chai')
  .should();

const {
  instability,
  effort,
} = require('./metrics.js');

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

  it('should calculate metrics for a member with equal dependencies and dependents', function () {
    let dependencies = 5;
    let dependents = 5;
    let i = instability(dependencies, dependents);
    i.should.equal(0.5);
  });

  it('should calculate metrics for a member with more dependencies than dependents', function () {
    let dependencies = 10;
    let dependents = 5;
    let i = instability(dependencies, dependents);
    i.should.equal(0.67);
  });
});

describe('effort (E)', () => {
  it('should calculate effort for a maximally stable member', function () {
    let dependencies = 0;
    let dependents = 5;
    let e = effort(dependencies, dependents);
    e.should.equal(2.5);
  });

  it('should calculate effort for a maximally unstable member', function () {
    let dependencies = 5;
    let dependents = 0;
    let e = effort(dependencies, dependents);
    e.should.equal(5);
  });

  it('should calculate effort for a member with equal dependencies and dependents', function () {
    let dependencies = 5;
    let dependents = 5;
    let e = effort(dependencies, dependents);
    e.should.equal(7.5);
  });

  it('should calculate effort for a member with more dependencies than dependents', function () {
    let dependencies = 10;
    let dependents = 5;
    let e = effort(dependencies, dependents);
    e.should.equal(12.5);
  });
});
