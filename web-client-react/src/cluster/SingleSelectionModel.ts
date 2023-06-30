import { Member } from './Member';
import SelectionModel from './SelectionModel';

export class SingleSelectionModel extends SelectionModel<Member | null> {
  static DEFAULT = new SingleSelectionModel('', [], null, null);

  public override select(): SelectionModel<Member | null> {
    return this.copy(undefined, undefined, undefined, this.focused);
  }

  protected override copy(
    searchTerm: string = this.searchTerm,
    searchResult: Member[] = this.searchResult,
    focused: Member | null = this.focused,
    selected: Member | null = this.selected,
  ): SelectionModel<Member | null> {
    return new SingleSelectionModel(searchTerm, searchResult, focused, selected);
  }
}
