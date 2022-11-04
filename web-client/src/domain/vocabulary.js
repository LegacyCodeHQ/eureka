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

  function tokenizeMethod(methodSignature) {
    let parts = methodSignature.substring(0, methodSignature.indexOf('(')).split(' ');
    let returnType = parts[0];
    let identifier = parts[1];

    let parameterList = methodSignature
      .substring(methodSignature.indexOf('(') + 1, methodSignature.indexOf(')'));
    let parameterTypes = parameterList.split(',')
      .filter(parameter => parameter.length > 0)
      .map(parameter => parameter.trim());

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

function splitIdentifier(identifier) {
  if (identifier.indexOf('_') !== -1) {
    return identifier.split('_');
  }
  return identifier.split(/(?=[A-Z])/);
}
