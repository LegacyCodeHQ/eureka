import { Member } from './Member';

class SelectionModel {
  private constructor(
    public readonly searchTerm: string,
    public readonly focusedMember: Member | null,
    public readonly filteredMembers: Member[],
  ) {
    // empty
  }

  static DEFAULT = new SelectionModel('', null, []);

  isSearchTermEmpty(): boolean {
    return this.sanitizeSearchTerm(this.searchTerm).length === 0;
  }

  sanitizeSearchTerm(searchTerm: string): string {
    return searchTerm.trim();
  }

  focusNextMember(): SelectionModel {
    const currentFocusedMemberIndex = this.filteredMembers.indexOf(this.focusedMember!);
    const nextFocusedMemberIndex =
      currentFocusedMemberIndex + 1 === this.filteredMembers.length ? 0 : currentFocusedMemberIndex + 1;
    const nextFocusedMember = this.filteredMembers[nextFocusedMemberIndex];

    return new SelectionModel(this.searchTerm, nextFocusedMember, this.filteredMembers);
  }

  focusPreviousMember(): SelectionModel {
    const currentFocusedMemberIndex = this.filteredMembers.indexOf(this.focusedMember!);
    const previousFocusedMemberIndex =
      currentFocusedMemberIndex - 1 < 0 ? this.filteredMembers.length - 1 : currentFocusedMemberIndex - 1;
    const previousFocusedMember = this.filteredMembers[previousFocusedMemberIndex];

    return new SelectionModel(this.searchTerm, previousFocusedMember, this.filteredMembers);
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
    return new SelectionModel(trimmedSearchTerm, focusedMember, filteredMembers);
  }
}

class ClusterDialogBoxState {
  constructor(
    public readonly members: Member[],
    public readonly startNode: Member | null,
    public readonly startNodeSelectionModel: SelectionModel,
  ) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members, null, SelectionModel.DEFAULT);
  }

  search(searchTerm: string): ClusterDialogBoxState {
    const selectionModel = this.startNodeSelectionModel.search(searchTerm, this.members);
    return new ClusterDialogBoxState(this.members, this.startNode, selectionModel);
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
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.focusedMember,
      this.startNodeSelectionModel,
    );
  }

  deselectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(this.members, null, this.startNodeSelectionModel);
  }

  private changeFocus(selectionModel: SelectionModel) {
    return new ClusterDialogBoxState(this.members, this.startNode, selectionModel);
  }
}

export default ClusterDialogBoxState;
