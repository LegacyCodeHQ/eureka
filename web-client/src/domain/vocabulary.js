export function tokenize(signature) {
  function tokenizeField(fieldSignature) {
    let parts = fieldSignature.split(' ');
    let type = parts[0];
    let identifier = parts[1];
    return {
      type: type,
      words: splitIdentifier(identifier),
    };
  }

  function tokenizeMethod(methodSignature) {
    let parts = methodSignature.substring(0, methodSignature.indexOf('(')).split(' ');
    let returnType = parts[0];
    let identifier = parts[1];

    return {
      type: returnType,
      words: splitIdentifier(identifier),
    };
  }

  if (signature.indexOf('(') !== -1 && signature.indexOf(')') !== -1) {
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
