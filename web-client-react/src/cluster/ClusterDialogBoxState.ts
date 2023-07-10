import { Member } from './Member';
import { MultipleSelectionModel } from './MultipleSelectionModel';
import { SingleSelectionModel } from './SingleSelectionModel';

class ClusterDialogBoxState {
  constructor(
    public readonly members: Member[],
    public readonly startNodeSelectionModel: SingleSelectionModel,
    public readonly hubNodesSelectionModel: MultipleSelectionModel,
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
        this.hubNodesSelectionModel,
      );
    } else {
      const hubNodesSelectionModel = this.hubNodesSelectionModel.search(this.members, searchTerm, [
        this.startNodeSelectionModel.selected,
        ...this.hubNodesSelectionModel.selected,
      ]);
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel,
        hubNodesSelectionModel as MultipleSelectionModel,
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
        this.hubNodesSelectionModel.focusNextMember() as MultipleSelectionModel,
      );
    }

    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.focusNextMember() as SingleSelectionModel,
      this.hubNodesSelectionModel,
    );
  }

  focusPreviousMember(): ClusterDialogBoxState {
    const isStartNodeSelected = this.startNodeSelectionModel.selected !== null;
    if (isStartNodeSelected) {
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel,
        this.hubNodesSelectionModel.focusPreviousMember() as MultipleSelectionModel,
      );
    }

    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.focusPreviousMember() as SingleSelectionModel,
      this.hubNodesSelectionModel,
    );
  }

  select(): ClusterDialogBoxState {
    if (!this.startNodeSelectionModel.selected) {
      return new ClusterDialogBoxState(
        this.members,
        this.startNodeSelectionModel.select() as SingleSelectionModel,
        this.hubNodesSelectionModel,
      );
    }
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel,
      this.hubNodesSelectionModel.select() as MultipleSelectionModel,
    );
  }

  deselectHubNode(member: Member): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel,
      this.hubNodesSelectionModel.deselect(member),
    );
  }

  deselectStartNode(): ClusterDialogBoxState {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel.deselect(),
      this.hubNodesSelectionModel,
    );
  }

  deselectAll() {
    return new ClusterDialogBoxState(
      this.members,
      this.startNodeSelectionModel,
      this.hubNodesSelectionModel.deselectAll(),
    );
  }
}

export default ClusterDialogBoxState;
