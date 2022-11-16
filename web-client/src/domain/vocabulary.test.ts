require('chai')
  .should();

const {
  tokenize,
  vocabulary,
  vocabularyStats,
  selectors,
  termMatches,
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

  it('should not contain blanks after tokenization', function () {
    let actual = tokenize('String __name');
    actual.should.deep.equal({
      types: ['String'],
      words: ['name'],
    });
  });

  it('should tokenize acronyms in identifiers', function () {
    let actual = tokenize('String HTMLDocument');
    actual.should.deep.equal({
      types: ['String'],
      words: ['HTML', 'Document'],
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

  it('should tokenize a method with parameters', () => {
    const tokens = tokenize('Event pop(Queue)');
    tokens.should.deep.equal({
      types: ['Event', 'Queue'],
      words: ['pop'],
    });
  });

  it('should tokenize a method with multiple parameters', () => {
    let tokens = tokenize('Event pop(Queue, Stack)');
    tokens.should.deep.equal({
      types: ['Event', 'Queue', 'Stack'],
      words: ['pop'],
    });
  });

  it('should tokenize a method with a camel case identifier', () => {
    let tokens = tokenize('Book firstBookByAuthor(Author)');
    tokens.should.deep.equal({
      types: ['Book', 'Author'],
      words: ['first', 'Book', 'By', 'Author'],
    });
  });
});

describe('class vocabulary', () => {
  const graph = {
    'nodes': [{
      'id': 'String name',
      'group': 1
    }, {
      'id': 'String version',
      'group': 1
    }, {
      'id': 'int versionCode',
      'group': 1
    }, {
      'id': 'void <init>()',
      'group': 2
    }, {
      'id': 'String getName()',
      'group': 2
    }, {
      'id': 'void sayHello()',
      'group': 2
    }, {
      'id': 'void sayGoodbye()',
      'group': 2
    }, {
      'id': 'String whatVersion()',
      'group': 2
    }, {
      'id': 'int random()',
      'group': 2
    }, {
      'id': 'String incrementVersion()',
      'group': 2
    }],
    'links': [{
      'source': 'void <init>()',
      'target': 'String name',
      'value': 1
    }, {
      'source': 'void <init>()',
      'target': 'String version',
      'value': 1
    }, {
      'source': 'void <clinit>()',
      'target': 'int versionCode',
      'value': 1
    }, {
      'source': 'String getName()',
      'target': 'String name',
      'value': 1
    }, {
      'source': 'void sayHello()',
      'target': 'String name',
      'value': 1
    }, {
      'source': 'void sayGoodbye()',
      'target': 'String name',
      'value': 1
    }, {
      'source': 'String whatVersion()',
      'target': 'String version',
      'value': 1
    }, {
      'source': 'String incrementVersion()',
      'target': 'String version',
      'value': 1
    }],
    'meta': {
      'classInfo': {
        'name': 'io.redgreen.tumbleweed.samples.PropertiesAndFunctions'
      }
    }
  };

  it('should build a member vocabulary from a given graph', () => {
    let actual = vocabulary(graph);
    expect(actual)
      .toMatchSnapshot();
  });

  it('should build vocabulary stats from the vocabulary object', () => {
    let actual = vocabularyStats(vocabulary(graph));
    expect(actual)
      .toMatchSnapshot();
  });

  it('should build vocabulary for state', () => {
    let actual = vocabularyStats(vocabulary(graph, selectors.state));
    expect(actual)
      .toMatchSnapshot();
  });

  it('should build vocabulary for behavior', () => {
    let actual = vocabularyStats(vocabulary(graph, selectors.behavior));
    expect(actual)
      .toMatchSnapshot();
  });
});

describe('type term match', () => {
  it('should match a field type', () => {
    let actual = termMatches('int versionCode', 'int');
    actual.should.equal(true);
  });

  it('should match a function return type', () => {
    let actual = termMatches('int count(List)', 'int');
    actual.should.equal(true);
  });

  it('should match a function parameter type', () => {
    let actual = termMatches('boolean isGt(int, int)', 'int');
    actual.should.equal(true);
  });
});

describe('word term match', () => {
  it('should match a field identifier', () => {
    let actual = termMatches('int versionCode', 'version');
    actual.should.equal(true);
  });

  it('should match a method identifier', () => {
    let actual = termMatches('int getVersionCode()', 'version');
    actual.should.equal(true);
  });
});
