export function compressType(qualifiedType) {
  const threshold = 32;
  const parts = qualifiedType.split('.');
  const isLongTypeName = qualifiedType.length > threshold;
  const isInDefaultPackage = parts.length === 1;

  if (isLongTypeName && !isInDefaultPackage) {
    let shortenedPackageName = parts.slice(0, parts.length - 1)
      .map(part => part[0])
      .join('.');
    let simpleTypeName = parts.slice(parts.length - 1);
    return shortenedPackageName + '.' + simpleTypeName;
  } else if (isLongTypeName && isInDefaultPackage) {
    let charsToKeep = threshold / 2;
    return qualifiedType.slice(0, charsToKeep) + '...' + qualifiedType.slice(qualifiedType.length - charsToKeep);
  }
  return qualifiedType;
}

export function getPackageName(qualifiedType) {
  const dotBeforeTypeName = qualifiedType.lastIndexOf('.');
  if (dotBeforeTypeName !== -1) {
    return qualifiedType.slice(0, dotBeforeTypeName);
  }
  return null;
}

export function getClassName(qualifiedType) {
  const dotBeforeTypeName = qualifiedType.lastIndexOf('.');
  if (dotBeforeTypeName !== -1) {
    return qualifiedType.slice(dotBeforeTypeName + 1);
  }
  return qualifiedType;
}
