import { Member } from './Member';

class SelectionModel {
  private constructor(
    public readonly searchTerm: string,
    public readonly focused: Member | null,
    public readonly searchResult: Member[],
    public readonly selected: Member | null,
  ) {
    // empty
  }

  static DEFAULT = new SelectionModel('', null, [], null);

  isSearchTermEmpty(): boolean {
    return this.sanitizeSearchTerm(this.searchTerm).length === 0;
  }

  sanitizeSearchTerm(searchTerm: string): string {
    return searchTerm.trim();
  }

  focusNextMember(): SelectionModel {
    const currentFocusedMemberIndex = this.searchResult.indexOf(this.focused!);
    const nextFocusedMemberIndex =
      currentFocusedMemberIndex + 1 === this.searchResult.length ? 0 : currentFocusedMemberIndex + 1;
    const nextFocusedMember = this.searchResult[nextFocusedMemberIndex];

    return new SelectionModel(this.searchTerm, nextFocusedMember, this.searchResult, this.selected);
  }

  focusPreviousMember(): SelectionModel {
    const currentFocusedMemberIndex = this.searchResult.indexOf(this.focused!);
    const previousFocusedMemberIndex =
      currentFocusedMemberIndex - 1 < 0 ? this.searchResult.length - 1 : currentFocusedMemberIndex - 1;
    const previousFocusedMember = this.searchResult[previousFocusedMemberIndex];

    return new SelectionModel(this.searchTerm, previousFocusedMember, this.searchResult, this.selected);
  }

  search(searchTerm: string, members: Member[]): SelectionModel {
    const trimmedSearchTerm = this.sanitizeSearchTerm(searchTerm);

    let filteredMembers: Member[];
    if (trimmedSearchTerm.length > 2) {
      filteredMembers = members.filter((member) =>
        member.nodeId.toLowerCase().includes(trimmedSearchTerm.toLowerCase()),
      );
    } else {
      filteredMembers = [];
    }

    let focusedMember: Member | null = null;
    if (trimmedSearchTerm.length > 0 && filteredMembers.length > 0) {
      focusedMember = filteredMembers[0];
    }
    return new SelectionModel(trimmedSearchTerm, focusedMember, filteredMembers, this.selected);
  }

  select(): SelectionModel {
    return new SelectionModel(this.searchTerm, this.focused, this.searchResult, this.focused);
  }

  deselect(): SelectionModel {
    return new SelectionModel(this.searchTerm, this.focused, this.searchResult, null);
  }
}

class ClusterDialogBoxState {
  constructor(public readonly members: Member[], public readonly startNodeSelectionModel: SelectionModel) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members, SelectionModel.DEFAULT);
  }

  search(searchTerm: string): ClusterDialogBoxState {
    const selectionModel = this.startNodeSelectionModel.search(searchTerm, this.members);
    return new ClusterDialogBoxState(this.members, selectionModel);
  }

  isSearchTermEmpty(): boolean {
    return this.startNodeSelectionModel.isSearchTermEmpty();
  }

  focusNextMember(): ClusterDialogBoxState {
    return this.changeFocus(this.startNodeSelectionModel.focusNextMember());
  }

  focusPreviousMember(): ClusterDialogBoxState {
    return this.changeFocus(this.startNodeSelectionModel.focusPreviousMember());
  }

  selectStartNode(): ClusterDialogBoxState {
    const selectionModel = this.startNodeSelectionModel.select();
    return new ClusterDialogBoxState(this.members, selectionModel);
  }

  deselectStartNode(): ClusterDialogBoxState {
    const selectionModel = this.startNodeSelectionModel.deselect();
    return new ClusterDialogBoxState(this.members, selectionModel);
  }

  private changeFocus(selectionModel: SelectionModel) {
    return new ClusterDialogBoxState(this.members, selectionModel);
  }
}

export default ClusterDialogBoxState;
