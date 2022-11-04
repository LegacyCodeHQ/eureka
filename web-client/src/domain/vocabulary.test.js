require('chai')
  .should();

function tokenize(field) {
  let parts = field.split(' ');
  let type = parts[0];
  let identifier = parts[1];
  return [type].concat(splitIdentifier(identifier));
}

function splitIdentifier(identifier) {
  return identifier.split(/(?=[A-Z])/);
}

describe('tokenization', () => {
  it('should tokenize a simple field', () => {
    const tokens = tokenize('int x');
    tokens.should.deep.equal(['int', 'x']);
  });

  it('should tokenize a field in camel case', function () {
    const actual = tokenize('int xCamelCase');
    actual.should.deep.equal(['int', 'x', 'Camel', 'Case']);
  });
});
