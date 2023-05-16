import { Token } from './Token';

export class TokenStat {
  token: Token;
  count: number;

  constructor(token: Token, count: number) {
    this.token = token;
    this.count = count
  }

  isEqual(other: TokenStat | null): boolean {
    return other != null &&
      (this === other || (this.token.isEqual(other.token) && this.count === other.count));
  }
}
