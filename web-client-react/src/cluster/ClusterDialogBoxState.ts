import { Member } from './Member';
import { MultipleSelectionModel } from './MultipleSelectionModel';
import { SingleSelectionModel } from './SingleSelectionModel';

class ClusterDialogBoxState {
  constructor(
    public readonly members: Member[],
    public readonly startNodeSelectionModel: SingleSelectionModel,
    public readonly blockNodeSelectionModel: MultipleSelectionModel,
  ) {
    // empty
  }

  static initialState(members: Member[]): ClusterDialogBoxState {
    return new ClusterDialogBoxState(members, SingleSelectionModel.DEFAULT, MultipleSelectionModel.DEFAULT);
  }

  search(searchTerm: string): ClusterDialogBoxState {
    if (this.startNodeSelectionModel.selected === null) {
      const selectionModel = this.startNodeSelectionModel.search(this.members, searchTerm);
      return new ClusterDialogBoxState(
        this.members,
        selectionModel as SingleSelectionModel,
        this.blockNodeSelectionModel,
      );
    } else {
      const blockSelectionModel = this.blockNodeSelectionModel.search(this.members, searchTerm, [
        this.startNodeSelectionModel.selected,
        ...this.blockNodeSelectionModel.selected,
      ]);
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel,
        blockSelectionModel as MultipleSelectionModel,
      );
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
        this.blockNodeSelectionModel.focusNextMember() as MultipleSelectionModel,
      );
    }

    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.focusNextMember() as SingleSelectionModel,
      this.blockNodeSelectionModel,
    );
  }

  focusPreviousMember(): ClusterDialogBoxState {
    const isStartNodeSelected = this.startNodeSelectionModel.selected !== null;
    if (isStartNodeSelected) {
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel,
        this.blockNodeSelectionModel.focusPreviousMember() as MultipleSelectionModel,
      );
    }

    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.focusPreviousMember() as SingleSelectionModel,
      this.blockNodeSelectionModel,
    );
  }

  select(): ClusterDialogBoxState {
    if (!this.startNodeSelectionModel.selected) {
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel.select() as SingleSelectionModel,
        this.blockNodeSelectionModel,
      );
    }
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel,
      this.blockNodeSelectionModel.select() as MultipleSelectionModel,
    );
  }

  deselectBlockNode(member: Member): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel,
      this.blockNodeSelectionModel.deselect(member),
    );
  }

  deselectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.deselect(),
      this.blockNodeSelectionModel,
    );
  }

  deselectAll() {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel,
      this.blockNodeSelectionModel.deselectAll(),
    );
  }
}

export default ClusterDialogBoxState;
