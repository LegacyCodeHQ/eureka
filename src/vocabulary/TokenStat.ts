import {Token} from "./Token";

export class TokenStat {
  token: Token;
  frequency: number;

  constructor(token: Token, frequency: number) {
    this.token = token;
    this.frequency = frequency
  }
}
