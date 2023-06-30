import { Member } from './Member';
import { SelectionModel } from './SelectionModel';

class ClusterDialogBoxState {
  constructor(
    public readonly members: Member[],
    public readonly startNodeSelectionModel: SelectionModel<Member | null>,
    public readonly blockNodeSelectionModel: SelectionModel<Member[]>,
  ) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members, SelectionModel.SINGLE, SelectionModel.MULTIPLE);
  }

  search(searchTerm: string): ClusterDialogBoxState {
    if (this.startNodeSelectionModel.selected === null) {
      const selectionModel = this.startNodeSelectionModel.search(this.members, searchTerm);
      return new ClusterDialogBoxState(this.members, selectionModel, this.blockNodeSelectionModel);
    } else {
      const blockSelectionModel = this.blockNodeSelectionModel.search(this.members, searchTerm, [
        this.startNodeSelectionModel.selected,
        ...this.blockNodeSelectionModel.selected,
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
