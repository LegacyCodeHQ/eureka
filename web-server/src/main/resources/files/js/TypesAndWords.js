'use strict';

const e = React.createElement;

function VocabularyRow(props) {
  const token = Object.keys(props)[0];
  return (
    <tr key={token}>
      <td className='property'>{token}</td>
      <td className='value-number'>{props[token]}</td>
    </tr>
  );
}

function VocabularyPanel(props) {
  const { title, items } = props;
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
          {rows.map((row) => VocabularyRow(row))}
        </tbody>
      </table>
    </div>
  );
}

function Selector(props, onOptionChanged) {
  const title = props.option.title;

  return (
    <div key={title.toLowerCase()}>
      <input
        type='radio'
        name='selector'
        onChange={_ => onOptionChanged(props.option)}
        checked={props.option === props.selected} />
      <label>{title}</label>
    </div>
  );
}

function Selectors(props, onOptionChanged) {
  function createProps(option) {
    return {
      option: option,
      selected: props.selected,
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
    this.state = { selected: selectorRadioButtonOptions[0], };
  }

  render() {
    const selectorsProps = {
      options: selectorRadioButtonOptions,
      selected: this.state.selected,
    };

    const { types, words } = vocabularyStats(vocabulary(this.props, this.state.selected.selector));
    const typesProps = { title: 'Types', items: types };
    const wordsProps = { title: 'Words', items: words };

    return (
      <div className='scroll'>
        {Selectors(selectorsProps, (option) => this.setState({ selected: option }))}
        {VocabularyPanel(typesProps)}
        {VocabularyPanel(wordsProps)}
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
    return identifier.split(/(?=[A-Z])/);
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
