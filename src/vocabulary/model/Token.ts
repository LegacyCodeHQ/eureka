export class Token {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

  isEqual(other: Token | null) {
    return other != null && (this === other || this.name === other.name);
  }
}
