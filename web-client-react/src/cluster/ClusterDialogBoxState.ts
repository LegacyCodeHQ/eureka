import { Member } from './Member';

class ClusterDialogBoxState {
  private constructor(
    public readonly members: Member[],
    public readonly searchTerm: string = '',
    public readonly filteredMembers: Member[] = [],
    public readonly focusedMember: Member | null = null,
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
      filteredMembers = this.members.filter((member) => member.nodeId.includes(trimmedSearchTerm));
    } else {
      filteredMembers = this.filteredMembers;
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

  private sanitizeSearchTerm(searchTerm: string) {
    return searchTerm.trim();
  }
}

export default ClusterDialogBoxState;
