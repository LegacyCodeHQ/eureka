export function getSimpleClassName(qualifiedType: string): string {
  const dotBeforeTypeName = qualifiedType.lastIndexOf('.');
  if (dotBeforeTypeName !== -1) {
    return qualifiedType.slice(dotBeforeTypeName + 1);
  }
  return qualifiedType;
}
