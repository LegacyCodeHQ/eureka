import { Member } from './Member';
import SelectionModel from './SelectionModel';

export class MultipleSelectionModel extends SelectionModel<Member[]> {
  static DEFAULT = new MultipleSelectionModel('', [], null, []);

  deselect(): MultipleSelectionModel {
    const searchResult = [...this.selected, ...this.searchResult];
    return <MultipleSelectionModel>this.copy(undefined, searchResult, undefined, []);
  }

  public override select(): SelectionModel<Member[]> {
    const selectedMembers = this.selected as Member[];
    let selected: Member[];
    if (this.focused) {
      selected = [...selectedMembers, this.focused];
    } else {
      selected = [...selectedMembers];
    }

    const searchResult = this.searchResult.filter((member) => !(selected as Member[]).includes(member));
    const currentFocusedIndex = searchResult.findIndex((member) => member === this.focused);
    let focused: Member | null;
    if (searchResult[currentFocusedIndex + 1]) {
      focused = searchResult[currentFocusedIndex + 1];
    } else {
      focused = null;
    }
    return this.copy(undefined, searchResult, focused, selected);
  }

  protected override copy(
    searchTerm: string = this.searchTerm,
    searchResult: Member[] = this.searchResult,
    focused: Member | null = this.focused,
    selected: Member[] = this.selected,
  ): SelectionModel<Member[]> {
    return new MultipleSelectionModel(searchTerm, searchResult, focused, selected);
  }
}
