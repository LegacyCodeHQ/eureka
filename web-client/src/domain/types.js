export function shortenType(className) {
  const threshold = 32;
  const parts = className.split('.');
  const isLongClassName = className.length > threshold;
  const isInDefaultPackage = parts.length === 1;

  if (isLongClassName && !isInDefaultPackage) {
    let shortenedPackageName = parts.slice(0, parts.length - 1)
      .map(part => part[0])
      .join('.');
    let simpleClassName = parts.slice(parts.length - 1);
    return shortenedPackageName + '.' + simpleClassName;
  } else if (isLongClassName && isInDefaultPackage) {
    let charsToKeep = threshold / 2;
    return className.slice(0, charsToKeep) + '...' + className.slice(className.length - charsToKeep);
  }
  return className;
}
