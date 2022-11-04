require('chai')
  .should();

const {
  tokenize,
} = require('./vocabulary');

describe('field tokenization', () => {
  it('should tokenize a simple field', () => {
    const tokens = tokenize('int x');
    tokens.should.deep.equal({
      types: ['int'],
      words: ['x'],
    });
  });

  it('should tokenize a field in camel case', function () {
    const actual = tokenize('int xCamelCase');
    actual.should.deep.equal({
      types: ['int'],
      words: ['x', 'Camel', 'Case'],
    });
  });

  it('should tokenize a field in snake case', function () {
    const actual = tokenize('int x_snake_case');
    actual.should.deep.equal({
      types: ['int'],
      words: ['x', 'snake', 'case']
    });
  });

  it('should tokenize constants', function () {
    const actual = tokenize('int X_CONSTANT');
    actual.should.deep.equal({
      types: ['int'],
      words: ['X', 'CONSTANT']
    });
  });

  it('should not split types into individual words', function () {
    const actual = tokenize('StoryBook storyBook');
    actual.should.deep.equal({
      types: ['StoryBook'],
      words: ['story', 'Book'],
    });
  });

  it('should split identifiers that start with a capital letter into individual words', function () {
    let actual = tokenize('String QuestionTitle');
    actual.should.deep.equal({
      types: ['String'],
      words: ['Question', 'Title'],
    });
  });
});

describe('method tokenization', () => {
  it('should tokenize a method without parameters', () => {
    const tokens = tokenize('int add()');
    tokens.should.deep.equal({
      types: ['int'],
      words: ['add'],
    });
  });
});
