import { Member } from './Member';

class SelectionModel {
  constructor(
    public readonly searchTerm: string,
    public readonly focusedMember: Member | null,
    public readonly filteredMembers: Member[],
  ) {
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
    public readonly startNode: Member | null,
    public readonly startNodeSelectionModel: SelectionModel,
  ) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members, null, new SelectionModel('', null, []));
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
      this.startNode,
      new SelectionModel(trimmedSearchTerm, focusedMember, filteredMembers),
    );
  }

  isSearchTermEmpty(): boolean {
    return this.startNodeSelectionModel.isSearchTermEmpty();
  }

  focusNextMember(): ClusterDialogBoxState {
    const currentFocusedMemberIndex = this.startNodeSelectionModel.filteredMembers.indexOf(
      this.startNodeSelectionModel.focusedMember!,
    );
    const nextFocusedMemberIndex =
      currentFocusedMemberIndex + 1 === this.startNodeSelectionModel.filteredMembers.length
        ? 0
        : currentFocusedMemberIndex + 1;
    const nextFocusedMember = this.startNodeSelectionModel.filteredMembers[nextFocusedMemberIndex];

    const selectionModel = new SelectionModel(
      this.startNodeSelectionModel.searchTerm,
      nextFocusedMember,
      this.startNodeSelectionModel.filteredMembers,
    );

    return new ClusterDialogBoxState(this.members, this.startNode, selectionModel);
  }

  focusPreviousMember(): ClusterDialogBoxState {
    const currentFocusedMemberIndex = this.startNodeSelectionModel.filteredMembers.indexOf(
      this.startNodeSelectionModel.focusedMember!,
    );
    const previousFocusedMemberIndex =
      currentFocusedMemberIndex - 1 < 0
        ? this.startNodeSelectionModel.filteredMembers.length - 1
        : currentFocusedMemberIndex - 1;
    const previousFocusedMember = this.startNodeSelectionModel.filteredMembers[previousFocusedMemberIndex];
    return new ClusterDialogBoxState(
      this.members,
      this.startNode,
      new SelectionModel(
        this.startNodeSelectionModel.searchTerm,
        previousFocusedMember,
        this.startNodeSelectionModel.filteredMembers,
      ),
    );
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
}

export default ClusterDialogBoxState;
