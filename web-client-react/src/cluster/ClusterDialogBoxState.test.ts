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

  describe('search start node functionality', () => {
    it('should not filter members when the search term is empty', () => {
      // when
      const searchResultState = initialState.search('');

      // then
      expect(searchResultState).toMatchSnapshot();
    });

    it('should not filter members when the search term only contains spaces', () => {
      // when
      const searchResultState = initialState.search('      ');

      // then
      expect(searchResultState).toMatchSnapshot();
      expect(searchResultState.isSearchTermEmpty()).toBe(true);
    });

    it('should not filter members when the search term has < 2 characters', () => {
      // when
      const searchResultState = initialState.search('c');

      // then
      expect(searchResultState).toMatchSnapshot();
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

    it('should do a case insensitive search', () => {
      // when
      const searchResultState = initialState.search('resume');

      // then
      expect(searchResultState).toMatchSnapshot();
    });
  });

  describe('focused member functionality', () => {
    let searchResultState: ClusterDialogBoxState;

    beforeEach(() => {
      searchResultState = initialState.search('void');
    });

    it('should select the first matching member and mark it as focused from the search result', () => {
      expect(searchResultState).toMatchSnapshot();
      expect(searchResultState.startNodeSelectionModel.focused).toEqual(new Member('void onCreate()'));
    });

    it('should select the next member when pressing the down arrow', () => {
      // when
      const focusNextMemberState = searchResultState.focusNextMember();

      // then
      expect(focusNextMemberState).toMatchSnapshot();
      expect(focusNextMemberState.startNodeSelectionModel.focused).toEqual(new Member('void onPause()'));
    });

    it('should select the previous member when pressing the up arrow', () => {
      // given
      const focusNextMemberState = searchResultState.focusNextMember();
      expect(focusNextMemberState.startNodeSelectionModel.focused).toEqual(new Member('void onPause()'));

      // when
      const focusPreviousMemberState = focusNextMemberState.focusPreviousMember();

      // then
      expect(focusPreviousMemberState).toMatchSnapshot();
      expect(focusPreviousMemberState.startNodeSelectionModel.focused).toEqual(new Member('void onCreate()'));
    });

    it('should select the last member from the search result when pressing the up arrow on the first item', () => {
      // when
      const focusPreviousMemberState = searchResultState.focusPreviousMember();

      // then
      expect(focusPreviousMemberState).toMatchSnapshot();
      expect(focusPreviousMemberState.startNodeSelectionModel.focused).toEqual(new Member('void onResume()'));
    });

    it('should select the first member from the search result when pressing down arrow on the last item', () => {
      // given
      const focusPreviousMemberState = searchResultState.focusPreviousMember();
      expect(focusPreviousMemberState.startNodeSelectionModel.focused).toEqual(new Member('void onResume()'));

      // when
      const focusNextMemberState = focusPreviousMemberState.focusNextMember();

      // then
      expect(focusNextMemberState).toMatchSnapshot();
      expect(focusNextMemberState.startNodeSelectionModel.focused).toEqual(new Member('void onCreate()'));
    });
  });

  describe('start node selection', () => {
    let searchResultState: ClusterDialogBoxState;

    beforeEach(() => {
      searchResultState = initialState.search('void');
    });

    it('should select the currently focused element as the start node', () => {
      // when
      const startNodeSelectedState = searchResultState.select();

      // then
      expect(startNodeSelectedState).toMatchSnapshot();
    });

    it('should deselect the start node', () => {
      // given
      const startNodeSelectedState = searchResultState.select();
      expect(startNodeSelectedState.startNodeSelectionModel.selected).toEqual(new Member('void onCreate()'));

      // when
      const startNodeDeselectedState = startNodeSelectedState.deselectStartNode();

      // then
      expect(startNodeDeselectedState).toMatchSnapshot();
    });
  });

  describe('hub node search', () => {
    it('should list search results for hub node', () => {
      // given
      const initialState = ClusterDialogBoxState.initialState(members);
      const startNodeSearchResultState = initialState.search('voi');
      const startNodeSelectedState = startNodeSearchResultState.select();

      // when
      const hubNodesSearchResultState = startNodeSelectedState.search('void');

      // then
      expect(hubNodesSearchResultState).toMatchSnapshot();
    });
  });

  describe('hub node focus', () => {
    let hubNodesSearchResultState: ClusterDialogBoxState;

    beforeEach(() => {
      const initialState = ClusterDialogBoxState.initialState(members);
      const startNodeSearchResultState = initialState.search('voi');
      const startNodeSelectedState = startNodeSearchResultState.select();
      hubNodesSearchResultState = startNodeSelectedState.search('void');
    });

    it('should focus the next member on the search result after pressing down arrow', () => {
      // when
      const nextMemberFocusedState = hubNodesSearchResultState.focusNextMember();

      // then
      expect(nextMemberFocusedState).toMatchSnapshot();
      expect(nextMemberFocusedState.hubNodesSelectionModel.focused).toEqual(new Member('void onResume()'));
    });

    it('should focus the previous member on the search result after pressing up arrow', () => {
      // when
      const previousMemberFocusedState = hubNodesSearchResultState.focusPreviousMember();

      // then
      expect(previousMemberFocusedState).toMatchSnapshot();
      expect(previousMemberFocusedState.hubNodesSelectionModel.focused).toEqual(new Member('void onResume()'));
    });
  });

  describe('hub node selection', () => {
    let hubNodesSearchResultState: ClusterDialogBoxState;

    beforeEach(() => {
      const initialState = ClusterDialogBoxState.initialState(members);
      const startNodeSearchResultState = initialState.search('voi');
      const startNodeSelectedState = startNodeSearchResultState.select();
      hubNodesSearchResultState = startNodeSelectedState.search('void');
    });

    it('should select the currently focused node on pressing enter', () => {
      // when
      const hubNodesSelectedState = hubNodesSearchResultState.select();

      // then
      expect(hubNodesSelectedState.hubNodesSelectionModel.selected).toEqual([new Member('void onPause()')]);
      expect(hubNodesSelectedState).toMatchSnapshot();
    });

    it("should deselect the currently focused node on clicking the 'x' button", () => {
      // given
      const hubNodesSelectedState = hubNodesSearchResultState.select();

      // when
      const hubNodesDeselectedState = hubNodesSelectedState.deselectHubNode(new Member('void onPause()'));

      // then
      expect(hubNodesDeselectedState.hubNodesSelectionModel.selected).toEqual([]);
      expect(hubNodesDeselectedState).toMatchSnapshot();
    });

    it('should allow to deselect the start node', () => {
      // given
      const hubNodesSelectedState = hubNodesSearchResultState.select();

      // when
      const startNodeDeselectedState = hubNodesSelectedState.deselectStartNode();

      // then
      expect(startNodeDeselectedState.startNodeSelectionModel.selected).toBeNull();
      expect(startNodeDeselectedState).toMatchSnapshot();
    });

    it('should allow selecting multiple nodes', () => {
      // given
      const hubNodesSelectedState = hubNodesSearchResultState.select();

      // when
      const multipleHubNodesSelectedState = hubNodesSelectedState.select();

      // then
      expect(multipleHubNodesSelectedState).toMatchSnapshot();
    });

    it('should allow deselecting all nodes', () => {
      // given
      const hubNodesSelectedState = hubNodesSearchResultState.select();
      const multipleHubNodesSelectedState = hubNodesSelectedState.select();

      // when
      const allDeselectedModel = multipleHubNodesSelectedState.deselectAll();

      // then
      expect(allDeselectedModel).toMatchSnapshot();
    });
  });
});
