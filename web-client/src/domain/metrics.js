export function instability(dependencies, dependents) {
  let i = dependencies / (dependencies + dependents);
  return Math.round((i + Number.EPSILON) * 100) / 100;
}

/**
 * Experimental: Calculates the effort metric for a given member.
 * @param dependencies
 * @param dependents
 * @returns {*} effort
 */
export function effort(dependencies, dependents) {
  return dependencies + (dependents / 2);
}
