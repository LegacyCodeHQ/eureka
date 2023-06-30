import { Member } from './Member';

export class SelectionModel<T> {
  constructor(
    public readonly searchTerm: string,
    public readonly searchResult: Member[],
    public readonly focused: Member | null,
    public readonly selected: T,
  ) {
    // empty
  }

  static SINGLE = new SelectionModel<Member | null>('', [], null, null);
  static MULTIPLE = new SelectionModel<Member[]>('', [], null, []);
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

    return new SelectionModel(this.searchTerm, this.searchResult, nextFocusedMember, this.selected as T);
  }

  focusPreviousMember(): SelectionModel<T> {
    const currentFocusedMemberIndex = this.searchResult.indexOf(this.focused!);
    const previousFocusedMemberIndex =
      currentFocusedMemberIndex - 1 < 0 ? this.searchResult.length - 1 : currentFocusedMemberIndex - 1;
    const previousFocusedMember = this.searchResult[previousFocusedMemberIndex];

    return new SelectionModel(this.searchTerm, this.searchResult, previousFocusedMember, this.selected as T);
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
    return new SelectionModel(trimmedSearchTerm, filteredMembers, focusedMember, this.selected as T);
  }

  select(): SelectionModel<T> {
    let selected: T;
    let searchResult: Member[];
    let focused: Member | null;

    const isBlockNodesSelectionModel = Array.isArray(this.selected);
    if (isBlockNodesSelectionModel) {
      const selectedMembers = this.selected as Member[];
      selected = [...selectedMembers, this.focused] as T;
      searchResult = this.searchResult.filter((member) => !(selected as Member[]).includes(member));
      const currentFocusedIndex = searchResult.findIndex((member) => member === this.focused);
      if (searchResult[currentFocusedIndex + 1]) {
        focused = searchResult[currentFocusedIndex + 1];
      } else {
        focused = null;
      }
    } else {
      selected = this.focused as T;
      searchResult = this.searchResult;
      focused = this.focused;
    }
    return new SelectionModel(this.searchTerm, searchResult, focused, selected);
  }

  deselect(): SelectionModel<T> {
    if (Array.isArray(this.selected)) {
      const searchResult = [...this.selected, ...this.searchResult];
      return new SelectionModel(this.searchTerm, searchResult, this.focused, [] as T);
    }
    return new SelectionModel(this.searchTerm, this.searchResult, this.focused, null as T);
  }
}
