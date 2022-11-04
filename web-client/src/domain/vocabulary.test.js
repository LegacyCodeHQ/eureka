require('chai')
  .should();

function tokenize(field) {
  let parts = field.split(' ');
  let type = parts[0];
  let identifier = parts[1];
  return {
    type: type,
    words: splitIdentifier(identifier),
  };
}

function splitIdentifier(identifier) {
  if (identifier.indexOf('_') !== -1) {
    return identifier.split('_');
  }
  return identifier.split(/(?=[A-Z])/);
}

describe('tokenization', () => {
  it('should tokenize a simple field', () => {
    const tokens = tokenize('int x');
    tokens.should.deep.equal({
      type: 'int',
      words: ['x'],
    });
  });

  it('should tokenize a field in camel case', function () {
    const actual = tokenize('int xCamelCase');
    actual.should.deep.equal({
      type: 'int',
      words: ['x', 'Camel', 'Case'],
    });
  });

  it('should tokenize a field in snake case', function () {
    const actual = tokenize('int x_snake_case');
    actual.should.deep.equal({
      type: 'int',
      words: ['x', 'snake', 'case']
    });
  });

  it('should tokenize constants', function () {
    const actual = tokenize('int X_CONSTANT');
    actual.should.deep.equal({
      type: 'int',
      words: ['X', 'CONSTANT']
    });
  });

  it('should not split types into individual words', function () {
    const actual = tokenize('StoryBook storyBook');
    actual.should.deep.equal({
      type: 'StoryBook',
      words: ['story', 'Book'],
    });
  });

  it('should split identifiers that start with a capital letter into individual words', function () {
    let actual = tokenize('String QuestionTitle');
    actual.should.deep.equal({
      type: 'String',
      words: ['Question', 'Title'],
    });
  });
});
