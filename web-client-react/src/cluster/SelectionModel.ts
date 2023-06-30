import { Member } from './Member';

abstract class SelectionModel<T> {
  constructor(
    public readonly searchTerm: string,
    public readonly searchResult: Member[],
    public readonly focused: Member | null,
    public readonly selected: T,
  ) {
    // empty
  }

  static MIN_SEARCH_TERM_LENGTH = 1;

  isSearchTermEmpty(): boolean {
    return this.sanitizeSearchTerm(this.searchTerm).length === 0;
  }

  sanitizeSearchTerm(searchTerm: string): string {
    return searchTerm.trim();
  }

  focusNextMember(): SelectionModel<T> {
    const currentFocusedMemberIndex = this.searchResult.indexOf(this.focused!);
    const nextFocusedMemberIndex =
      currentFocusedMemberIndex + 1 === this.searchResult.length ? 0 : currentFocusedMemberIndex + 1;
    const nextFocusedMember = this.searchResult[nextFocusedMemberIndex];

    return this.copy(this.searchTerm, this.searchResult, nextFocusedMember, this.selected);
  }

  focusPreviousMember(): SelectionModel<T> {
    const currentFocusedMemberIndex = this.searchResult.indexOf(this.focused!);
    const previousFocusedMemberIndex =
      currentFocusedMemberIndex - 1 < 0 ? this.searchResult.length - 1 : currentFocusedMemberIndex - 1;
    const previousFocusedMember = this.searchResult[previousFocusedMemberIndex];

    return this.copy(this.searchTerm, this.searchResult, previousFocusedMember, this.selected);
  }

  search(members: Member[], searchTerm: string, membersToIgnore: Member[] = []): SelectionModel<T> {
    const trimmedSearchTerm = this.sanitizeSearchTerm(searchTerm);

    let filteredMembers: Member[];
    if (trimmedSearchTerm.length > SelectionModel.MIN_SEARCH_TERM_LENGTH) {
      filteredMembers = members.filter(
        (member) =>
          member.nodeId.toLowerCase().includes(trimmedSearchTerm.toLowerCase()) && !membersToIgnore.includes(member),
      );
    } else {
      filteredMembers = [];
    }

    let focusedMember: Member | null = null;
    if (trimmedSearchTerm.length > SelectionModel.MIN_SEARCH_TERM_LENGTH && filteredMembers.length > 0) {
      focusedMember = filteredMembers[0];
    }
    return this.copy(trimmedSearchTerm, filteredMembers, focusedMember, this.selected as T);
  }

  deselect(): SelectionModel<T> {
    if (Array.isArray(this.selected)) {
      const searchResult = [...this.selected, ...this.searchResult];
      return this.copy(this.searchTerm, searchResult, this.focused, [] as T);
    }
    return this.copy(this.searchTerm, this.searchResult, this.focused, null as T);
  }

  abstract select(): SelectionModel<T>;

  protected abstract copy(
    searchTerm: string,
    searchResult: Member[],
    focused: Member | null,
    selected: T,
  ): SelectionModel<T>;
}

export default SelectionModel;
