export class Member {
  constructor(public nodeId: string) {
    // empty
  }

  isMethod() {
    return this.nodeId.endsWith(')');
  }
}
