import {GraphData} from "../../viz/model/GraphData";
import {Node} from "../../viz/model/Node";

export function tokenize(signature: string) {
  function tokenizeField(fieldSignature: string) {
    let parts = fieldSignature.split(' ');
    let type = parts[0];
    let identifier = parts[1];
    return {
      types: [type],
      words: splitIdentifier(identifier),
    };
  }

  function getParameterTypes(methodSignature: string) {
    let parameterList = methodSignature
      .substring(methodSignature.indexOf('(') + 1, methodSignature.indexOf(')'));
    return parameterList.split(',')
      .filter(parameter => parameter.length > 0)
      .map(parameter => parameter.trim());
  }

  function returnTypeAndIdentifier(methodSignature: string) {
    let parts = methodSignature.substring(0, methodSignature.indexOf('('))
      .split(' ');
    let returnType = parts[0];
    let identifier = parts[1];
    return {
      returnType,
      identifier
    };
  }

  function splitIdentifier(identifier: string) {
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

  function tokenizeMethod(methodSignature: string) {
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
  all: (_: Node) => true,
  state: (node: Node) => node.group === 1,
  behavior: (node: Node) => node.group === 2,
};

export function vocabulary(graph: GraphData, selector = selectors.all) {
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

function sortObject(stats: any) {
  let sortableArray = [];
  for (let stat in stats) {
    sortableArray.push([stat, stats[stat]]);
  }
  sortableArray.sort((a, b) => b[1] - a[1]);

  let sortedObject: any = {};
  sortableArray.forEach((stat) => {
    sortedObject[stat[0]] = stat[1];
  });
  return sortedObject;
}

export function vocabularyStats(vocabulary: any) {
  let typeStats: any = {};
  let wordStats: any = {};

  vocabulary.types.forEach((type: string) => {
    typeStats.hasOwnProperty(type) ? typeStats[type]++ : typeStats[type] = 1;
  });

  vocabulary.words.forEach((word: string) => {
    let lowerCaseWord = word.toLowerCase();
    wordStats.hasOwnProperty(lowerCaseWord) ? wordStats[lowerCaseWord]++ : wordStats[lowerCaseWord] = 1;
  });

  return {
    types: sortObject(typeStats),
    words: sortObject(wordStats),
  };
}

export function tokenMatches(signature: string, token: string) {
  let tokens = tokenize(signature);
  return tokens.types.includes(token) || tokens.words.map(w => w.toLowerCase()).includes(token);
}
