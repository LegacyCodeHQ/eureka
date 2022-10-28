const { should } = require('chai');

const approvals = require('approvals');
const approvalsDir = 'test/approved/litmus';

should();

describe('a test environment', () => {
  it('should have mocha setup', () => {
    true.should.be.true; // eslint-disable-line
  });

  it('should have approvals setup', function () {
    approvals.verify(approvalsDir, this.test.title, 'Hello, world!');
  });
});
