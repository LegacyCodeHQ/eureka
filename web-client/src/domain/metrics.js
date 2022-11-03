export function instability(dependencies, dependents) {
  let i = dependencies / (dependencies + dependents);
  return Math.round((i + Number.EPSILON) * 100) / 100;
}
