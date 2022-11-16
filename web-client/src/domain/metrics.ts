export class Metrics {
  public static instability(dependencies: number, dependents: number): number {
    let i = dependencies / (dependencies + dependents);
    return Math.round((i + Number.EPSILON) * 100) / 100;
  }

  /**
   * Experimental: Calculates the effort metric for a given member.
   * @param dependencies
   * @param dependents
   * @returns {*} effort
   */
  public static effort(dependencies: number, dependents: number): number {
    return dependencies + (dependents / 2);
  }
}
