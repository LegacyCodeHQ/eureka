'use strict';

const e = React.createElement;

function tokenFrom(row) {
  return Object.keys(row)[0];
}

class VocabularyRow extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { row, position, tokens, onTokenEvent } = this.props;
    const token = tokenFrom(row);
    let className = 'vocabulary-normal';
    if (tokens.hovered === token) {
      className = 'vocabulary-hover';
    } else if (tokens.selected === token) {
      className = 'vocabulary-selected';
    }

    return (
      <tr onMouseOver={() => onTokenEvent('mouse-over', token)}
          onMouseOut={() => onTokenEvent('mouse-out', token)}
          onClick={() => onTokenEvent('click', token)}
          className={className}>
        <td className='property'>{position}. {token}</td>
        <td className='value-number'>{row[token]}</td>
      </tr>
    );
  }
}

class VocabularyPanel extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { title, items, tokens, onTokenEvent } = this.props;

    const rows = [];
    for (const key in items) {
      rows.push({ [key]: items[key] })
    }

    return (
      <div>
        <table className='panel'>
          <thead>
          <tr><th className='panel-title' colSpan='2'>{title + ' (' + Object.keys(items).length + ')'}</th></tr>
          </thead>
          <tbody>
          {rows.map((row, index) => <VocabularyRow key={tokenFrom(row)} row={row} position={index + 1} tokens={tokens} onTokenEvent={onTokenEvent}></VocabularyRow>)}
          </tbody>
        </table>
      </div>
    );
  }
}

function Selector(props, onOptionChanged) {
  const title = props.option.title;

  return (
    <div key={title.toLowerCase()}>
      <input
        type='radio'
        name='selector'
        onChange={_ => onOptionChanged(props.option)}
        checked={props.option === props.selector} />
      <label>{title}</label>
    </div>
  );
}

function Selectors(props, onOptionChanged) {
  function createProps(option) {
    return {
      option: option,
      selector: props.selector,
    }
  }

  return (
    <div>
      {props.options.map((option) => Selector(createProps(option), onOptionChanged))}
    </div>
  );
}

const selectors = {
  all: _ => true,
  state: node => node.group === 1,
  behavior: node => node.group === 2,
};

const selectorRadioButtonOptions = [
  { title: 'All', selector: selectors.all, },
  { title: 'State', selector: selectors.state, },
  { title: 'Behavior', selector: selectors.behavior, },
];

class TypesAndWords extends React.Component {
  constructor(props) {
    super(props);
    this.state = { selector: selectorRadioButtonOptions[0], };

    const tokens = { selected: null, hovered: null };
    this.state = { selector: selectorRadioButtonOptions[0], tokens: tokens };

    this.onTokenEvent = this.onTokenEvent.bind(this);
    this.updateTokens = this.updateTokens.bind(this);
  }

  updateTokens(event, token) {
    let selected = null;
    let hovered = token;
    if (event === 'click' && this.state.tokens.selected === token) {
      selected = null;
    } else if (event === 'click') {
      selected = token;
    }
    if ((event === 'mouse-out' || event === 'mouse-over') && this.state.tokens.selected !== null) {
      selected = this.state.tokens.selected;
    }
    if (event === 'mouse-out') {
      hovered = null;
    }
    const tokens = { hovered: hovered, selected: selected };
    this.setState((state) => ({ selector: state.selector, tokens: tokens }));

    console.log(tokens);
    this.props.onTokensChanged(tokens);
  }

  onTokenEvent(event, token) {
    console.log(event + ': ' + token);
    this.updateTokens(event, token);
  }

  render() {
    const selectorsProps = {
      options: selectorRadioButtonOptions,
      selector: this.state.selector,
    };

    const { graph } = this.props;
    const { types, words } = vocabularyStats(vocabulary(graph, this.state.selector.selector));

    return (
      <div className='scroll'>
        {Selectors(selectorsProps, (option) => this.setState({ selector: option }))}
        <VocabularyPanel title='Types' items={types} tokens={this.state.tokens} onTokenEvent={this.onTokenEvent}></VocabularyPanel>
        <VocabularyPanel title='Words' items={words} tokens={this.state.tokens} onTokenEvent={this.onTokenEvent}></VocabularyPanel>
      </div>
    );
  }
}

function tokenize(signature) {
  function tokenizeField(fieldSignature) {
    let parts = fieldSignature.split(' ');
    let type = parts[0];
    let identifier = parts[1];
    return {
      types: [type],
      words: splitIdentifier(identifier),
    };
  }

  function getParameterTypes(methodSignature) {
    let parameterList = methodSignature
      .substring(methodSignature.indexOf('(') + 1, methodSignature.indexOf(')'));
    return parameterList.split(',')
      .filter(parameter => parameter.length > 0)
      .map(parameter => parameter.trim());
  }

  function returnTypeAndIdentifier(methodSignature) {
    let parts = methodSignature.substring(0, methodSignature.indexOf('('))
      .split(' ');
    let returnType = parts[0];
    let identifier = parts[1];
    return {
      returnType,
      identifier
    };
  }

  function splitIdentifier(identifier) {
    if (identifier.indexOf('_') !== -1) {
      return identifier.split('_')
        .filter(word => word.length > 0);
    }

    let words = [];
    let currentWord = '';
    let splitByCaps = identifier.split(/(?=[A-Z])/);
    splitByCaps.forEach((word) => {
      if (word.length === 1) {
        currentWord += word;
      } else {
        if (currentWord.length > 0) {
          words.push(currentWord);
          currentWord = '';
        }
        if (word.length > 0) {
          words.push(word);
        }

      }
    });
    if (currentWord.length > 0) {
      words.push(currentWord);
    }
    return words;
  }

  function tokenizeMethod(methodSignature) {
    let {
      returnType,
      identifier
    } = returnTypeAndIdentifier(methodSignature);
    let parameterTypes = getParameterTypes(methodSignature);

    return {
      types: [returnType].concat(parameterTypes),
      words: splitIdentifier(identifier),
    };
  }

  let isMethod = signature.indexOf('(') !== -1 && signature.indexOf(')') !== -1;
  if (isMethod) {
    return tokenizeMethod(signature);
  } else {
    return tokenizeField(signature);
  }
}

function vocabulary(graph, selector = selectors.all) {
  return graph.nodes.filter(selector)
    .map(node => tokenize(node.id))
    .reduceRight((acc, tokens) => {
      return {
        types: acc.types.concat(tokens.types),
        words: acc.words.concat(tokens.words)
          .filter(word => word !== '<init>' && word !== '<clinit>'),
      };
    });
}

function sortObject(stats) {
  let sortableArray = [];
  for (let stat in stats) {
    sortableArray.push([stat, stats[stat]]);
  }
  sortableArray.sort((a, b) => b[1] - a[1]);

  let sortedObject = {};
  sortableArray.forEach((stat) => {
    sortedObject[stat[0]] = stat[1];
  });
  return sortedObject;
}

function vocabularyStats(vocabulary) {
  let typeStats = {};
  let wordStats = {};

  vocabulary.types.forEach(type => {
    typeStats.hasOwnProperty(type) ? typeStats[type]++ : typeStats[type] = 1;
  });

  vocabulary.words.forEach(word => {
    let lowerCaseWord = word.toLowerCase();
    wordStats.hasOwnProperty(lowerCaseWord) ? wordStats[lowerCaseWord]++ : wordStats[lowerCaseWord] = 1;
  });

  return {
    types: sortObject(typeStats),
    words: sortObject(wordStats),
  };
}

function updateVocabularyPanels(props) {
  const app = document.getElementById('app');
  const root = ReactDOM.createRoot(app);
  root.render(e(TypesAndWords, props));
}
