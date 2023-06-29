import { Member } from './Member';

class SelectionModel<T> {
  constructor(
    public readonly searchTerm: string,
    public readonly searchResult: Member[],
    public readonly focused: Member | null,
    public readonly selected: T,
  ) {
    // empty
  }

  static DEFAULT = new SelectionModel('', [], null, null);
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
    return new SelectionModel(this.searchTerm, this.searchResult, this.focused, this.focused as T);
  }

  deselect(): SelectionModel<T> {
    return new SelectionModel(this.searchTerm, this.searchResult, this.focused, null as T);
  }
}

class ClusterDialogBoxState {
  constructor(
    public readonly members: Member[],
    public readonly startNodeSelectionModel: SelectionModel<Member | null>,
    public readonly blockNodeSelectionModel: SelectionModel<Member | null>,
  ) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members, SelectionModel.DEFAULT, SelectionModel.DEFAULT);
  }

  search(searchTerm: string): ClusterDialogBoxState {
    if (this.startNodeSelectionModel.selected === null) {
      const selectionModel = this.startNodeSelectionModel.search(this.members, searchTerm);
      return new ClusterDialogBoxState(this.members, selectionModel, this.blockNodeSelectionModel);
    } else {
      const blockSelectionModel = this.blockNodeSelectionModel.search(this.members, searchTerm, [
        this.startNodeSelectionModel.selected,
      ]);
      return new ClusterDialogBoxState(this.members, this.startNodeSelectionModel, blockSelectionModel);
    }
  }

  isSearchTermEmpty(): boolean {
    return this.startNodeSelectionModel.isSearchTermEmpty();
  }

  focusNextMember(): ClusterDialogBoxState {
    const isStartNodeSelected = this.startNodeSelectionModel.selected !== null;
    if (isStartNodeSelected) {
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel,
        this.blockNodeSelectionModel.focusNextMember(),
      );
    }

    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.focusNextMember(),
      this.blockNodeSelectionModel,
    );
  }

  focusPreviousMember(): ClusterDialogBoxState {
    const isStartNodeSelected = this.startNodeSelectionModel.selected !== null;
    if (isStartNodeSelected) {
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel,
        this.blockNodeSelectionModel.focusPreviousMember(),
      );
    }

    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.focusPreviousMember(),
      this.blockNodeSelectionModel,
    );
  }

  select(): ClusterDialogBoxState {
    if (!this.startNodeSelectionModel.selected) {
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel.select(),
        this.blockNodeSelectionModel,
      );
    }
    return new ClusterDialogBoxState(this.members, this.startNodeSelectionModel, this.blockNodeSelectionModel.select());
  }

  deselectBlockNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel,
      this.blockNodeSelectionModel.deselect(),
    );
  }

  deselectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.deselect(),
      this.blockNodeSelectionModel,
    );
  }
}

export default ClusterDialogBoxState;
