require('chai')
  .should();

function tokenize(field) {
  return field.split(" ");
}

describe('tokenization', () => {
  it('should tokenize a simple field', () => {
    const tokens = tokenize('int x');
    tokens.should.deep.equal(['int', 'x']);
  });
});
