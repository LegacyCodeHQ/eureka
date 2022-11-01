export function compressType(qualifiedTypeName) {
  const threshold = 32;
  const parts = qualifiedTypeName.split('.');
  const isLongTypeName = qualifiedTypeName.length > threshold;
  const isInDefaultPackage = parts.length === 1;

  if (isLongTypeName && !isInDefaultPackage) {
    let shortenedPackageName = parts.slice(0, parts.length - 1)
      .map(part => part[0])
      .join('.');
    let simpleTypeName = parts.slice(parts.length - 1);
    return shortenedPackageName + '.' + simpleTypeName;
  } else if (isLongTypeName && isInDefaultPackage) {
    let charsToKeep = threshold / 2;
    return qualifiedTypeName.slice(0, charsToKeep) + '...' + qualifiedTypeName.slice(qualifiedTypeName.length - charsToKeep);
  }
  return qualifiedTypeName;
}

export function sortTypes(qualifiedTypeNames) {
  return qualifiedTypeNames.sort();
}

export function getPackageName(qualifiedType) {
  const dotBeforeTypeName = qualifiedType.lastIndexOf('.');
  if (dotBeforeTypeName !== -1) {
    return qualifiedType.slice(0, dotBeforeTypeName);
  }
  return null;
}
