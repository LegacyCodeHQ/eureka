export function tokenize(signature) {
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

export const selectors = {
  all: node => true,
  state: node => node.group === 1,
};

export function vocabulary(graph, selector = selectors.all) {
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

export function vocabularyStats(vocabulary) {
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
