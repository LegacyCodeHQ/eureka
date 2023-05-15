import {Token} from "./Token";

export class TokenStat {
  token: Token;
  count: number;

  constructor(token: Token, count: number) {
    this.token = token;
    this.count = count
  }
}
