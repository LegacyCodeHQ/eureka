import { Member } from './Member';

class SelectionModel {
  constructor(public readonly searchTerm: string) {
    // empty
  }

  isSearchTermEmpty(): boolean {
    return this.sanitizeSearchTerm(this.searchTerm).length === 0;
  }

  sanitizeSearchTerm(searchTerm: string): string {
    return searchTerm.trim();
  }
}

class ClusterDialogBoxState {
  constructor(
    public readonly members: Member[],
    public readonly filteredMembers: Member[],
    public readonly focusedMember: Member | null,
    public readonly startNode: Member | null,
    public readonly startNodeSelectionModel: SelectionModel,
  ) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members, [], null, null, new SelectionModel(''));
  }

  search(searchTerm: string): ClusterDialogBoxState {
    const trimmedSearchTerm = this.startNodeSelectionModel.sanitizeSearchTerm(searchTerm);

    let filteredMembers: Member[];
    if (trimmedSearchTerm.length > 2) {
      filteredMembers = this.members.filter((member) =>
        member.nodeId.toLowerCase().includes(trimmedSearchTerm.toLowerCase()),
      );
    } else {
      filteredMembers = [];
    }

    let focusedMember: Member | null = null;
    if (trimmedSearchTerm.length > 0 && filteredMembers.length > 0) {
      focusedMember = filteredMembers[0];
    }

    return new ClusterDialogBoxState(
      this.members,
      filteredMembers,
      focusedMember,
      this.startNode,
      new SelectionModel(trimmedSearchTerm),
    );
  }

  isSearchTermEmpty(): boolean {
    return this.startNodeSelectionModel.isSearchTermEmpty();
  }

  focusNextMember(): ClusterDialogBoxState {
    const currentFocusedMemberIndex = this.filteredMembers.indexOf(this.focusedMember!);
    const nextFocusedMemberIndex =
      currentFocusedMemberIndex + 1 === this.filteredMembers.length ? 0 : currentFocusedMemberIndex + 1;
    const nextFocusedMember = this.filteredMembers[nextFocusedMemberIndex];
    return new ClusterDialogBoxState(
      this.members,
      this.filteredMembers,
      nextFocusedMember,
      this.startNode,
      this.startNodeSelectionModel,
    );
  }

  focusPreviousMember(): ClusterDialogBoxState {
    const currentFocusedMemberIndex = this.filteredMembers.indexOf(this.focusedMember!);
    const previousFocusedMemberIndex =
      currentFocusedMemberIndex - 1 < 0 ? this.filteredMembers.length - 1 : currentFocusedMemberIndex - 1;
    const previousFocusedMember = this.filteredMembers[previousFocusedMemberIndex];
    return new ClusterDialogBoxState(
      this.members,
      this.filteredMembers,
      previousFocusedMember,
      this.startNode,
      this.startNodeSelectionModel,
    );
  }

  selectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.filteredMembers,
      this.focusedMember,
      this.focusedMember,
      this.startNodeSelectionModel,
    );
  }

  deselectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.filteredMembers,
      this.focusedMember,
      null,
      this.startNodeSelectionModel,
    );
  }
}

export default ClusterDialogBoxState;
