import { MultipleSelectionModel } from './MultipleSelectionModel';
import { Member } from './Member';

describe('A multiple selection model', () => {
  it('should retain search results when an item is selected', () => {
    // given
    const multipleSelectionModel = new MultipleSelectionModel(
      'void',
      [new Member('void onCreate()'), new Member('void onPause()'), new Member('void onResume()')],
      new Member('void onCreate()'),
      [],
    );

    // when
    const memberSelectedModel = multipleSelectionModel.select();

    // then
    expect(memberSelectedModel).toMatchSnapshot();
    expect(memberSelectedModel.visibleSearchResult()).toEqual([
      new Member('void onPause()'),
      new Member('void onResume()'),
    ]);
  });
});
