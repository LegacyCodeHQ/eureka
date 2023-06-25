import { Member } from './Member';

class ClusterDialogBoxState {
  private constructor(
    public readonly members: Member[],
    public readonly searchTerm: string = '',
    public readonly filteredMembers: Member[] = [],
    public readonly focusedMember: Member | null = null,
    public readonly startNode: Member | null = null,
  ) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members);
  }

  search(searchTerm: string): ClusterDialogBoxState {
    const trimmedSearchTerm = this.sanitizeSearchTerm(searchTerm);

    let filteredMembers: Member[];
    if (trimmedSearchTerm.length > 2) {
      filteredMembers = this.members.filter((member) =>
        member.nodeId.toLowerCase().includes(trimmedSearchTerm.toLowerCase()),
      );
    } else {
      filteredMembers = [];
    }

    let focusedMember: Member | null = null;
    if (trimmedSearchTerm.length > 0) {
      focusedMember = filteredMembers[0];
    }

    return new ClusterDialogBoxState(this.members, trimmedSearchTerm, filteredMembers, focusedMember);
  }

  isSearchTermEmpty(): boolean {
    return this.sanitizeSearchTerm(this.searchTerm).length === 0;
  }

  focusNextMember(): ClusterDialogBoxState {
    const currentFocusedMemberIndex = this.filteredMembers.indexOf(this.focusedMember!);
    const nextFocusedMemberIndex =
      currentFocusedMemberIndex + 1 === this.filteredMembers.length ? 0 : currentFocusedMemberIndex + 1;
    const nextFocusedMember = this.filteredMembers[nextFocusedMemberIndex];
    return new ClusterDialogBoxState(this.members, this.searchTerm, this.filteredMembers, nextFocusedMember);
  }

  private sanitizeSearchTerm(searchTerm: string) {
    return searchTerm.trim();
  }

  focusPreviousMember(): ClusterDialogBoxState {
    const currentFocusedMemberIndex = this.filteredMembers.indexOf(this.focusedMember!);
    const previousFocusedMemberIndex =
      currentFocusedMemberIndex - 1 < 0 ? this.filteredMembers.length - 1 : currentFocusedMemberIndex - 1;
    const previousFocusedMember = this.filteredMembers[previousFocusedMemberIndex];
    return new ClusterDialogBoxState(this.members, this.searchTerm, this.filteredMembers, previousFocusedMember);
  }

  selectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.searchTerm,
      this.filteredMembers,
      this.focusedMember,
      this.focusedMember,
    );
  }

  deselectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(this.members, this.searchTerm, this.filteredMembers, this.focusedMember, null);
  }
}

export default ClusterDialogBoxState;
