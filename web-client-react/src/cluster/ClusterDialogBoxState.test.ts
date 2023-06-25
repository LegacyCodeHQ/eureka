import ClusterDialogBoxState from './ClusterDialogBoxState';
import { Member } from './Member';

describe('Cluster dialog box state', () => {
  let members: Member[];
  let initialState: ClusterDialogBoxState;

  beforeEach(() => {
    members = [
      new Member('List chrome'),
      new Member('void onCreate()'),
      new Member('void onPause()'),
      new Member('void onResume()'),
    ];
    initialState = ClusterDialogBoxState.initialState(members);
  });

  it('should have an initial state', () => {
    expect(initialState).toMatchSnapshot();
  });

  describe('search functionality', () => {
    it('should not filter members when the search term is empty', () => {
      // when
      const searchState = initialState.search('');

      // then
      expect(searchState).toMatchSnapshot();
    });

    it('should not filter members when the search term only contains spaces', () => {
      // when
      const searchState = initialState.search('      ');

      // then
      expect(searchState).toMatchSnapshot();
      expect(searchState.isSearchTermEmpty()).toBe(true);
    });

    it('should not filter members when the search term has < 2 characters', () => {
      // when
      const searchState = initialState.search('c');

      // then
      expect(searchState).toMatchSnapshot();
    });

    it('should filter members based on the search term', () => {
      // when
      const searchResultState = initialState.search('chr');

      // then
      expect(searchResultState).toMatchSnapshot();
    });

    it('should filter members based on the search term but ignore whitespaces', () => {
      // when
      const searchResultState = initialState.search('  chr  ');

      // then
      expect(searchResultState).toMatchSnapshot();
    });

    it('should not have filtered results for a non-matching search term', () => {
      // when
      const searchResultState = initialState.search('hello');

      // then
      expect(searchResultState).toMatchSnapshot();
      expect(searchResultState.isSearchTermEmpty()).toBe(false);
    });
  });

  describe('focused member functionality', () => {
    let searchResultState: ClusterDialogBoxState;

    beforeEach(() => {
      searchResultState = initialState.search('void');
    });

    it('should select the first matching member and mark it as focused from the search result', () => {
      expect(searchResultState).toMatchSnapshot();
      expect(searchResultState.focusedMember).toEqual(new Member('void onCreate()'));
    });

    it('should select the next member when pressing the down arrow', () => {
      // when
      const state = searchResultState.focusNextMember();

      // then
      expect(state).toMatchSnapshot();
      expect(state.focusedMember).toEqual(new Member('void onPause()'));
    });

    it('should select the previous member when pressing the up arrow', () => {
      // given
      let state = searchResultState.focusNextMember();
      expect(state.focusedMember).toEqual(new Member('void onPause()'));

      // when
      state = state.focusPreviousMember();

      // then
      expect(state).toMatchSnapshot();
      expect(state.focusedMember).toEqual(new Member('void onCreate()'));
    });

    it('should select the last member from the search result when pressing the up arrow on the first item', () => {
      // when
      const state = searchResultState.focusPreviousMember();

      // then
      expect(state).toMatchSnapshot();
      expect(state.focusedMember).toEqual(new Member('void onResume()'));
    });

    it('should select the first member from the search result when pressing down arrow on the last item', () => {
      // given
      let state = searchResultState.focusPreviousMember();
      expect(state.focusedMember).toEqual(new Member('void onResume()'));

      // when
      state = state.focusNextMember();

      // then
      expect(state).toMatchSnapshot();
      expect(state.focusedMember).toEqual(new Member('void onCreate()'));
    });
  });

  describe('start node selection', () => {
    let searchResultState: ClusterDialogBoxState;

    beforeEach(() => {
      searchResultState = initialState.search('void');
    });

    it('should select the currently focused element as the start node', () => {
      // when
      const startNodeSelectedState = searchResultState.selectStartNode();

      // then
      expect(startNodeSelectedState).toMatchSnapshot();
    });

    it('should select the currently focused element as the start node', () => {
      // given
      const startNodeSelectedState = searchResultState.selectStartNode();
      expect(startNodeSelectedState.startNode).toEqual(new Member('void onCreate()'));

      // when
      const startNodeDeselectedState = startNodeSelectedState.deselectStartNode();

      // then
      expect(startNodeDeselectedState).toMatchSnapshot();
    });
  });
});
