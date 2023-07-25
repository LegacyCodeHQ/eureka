import React, { useEffect, useRef, useState } from 'react';
import './ClusterDialogBox.css';
import ClusterDialogBoxState from './ClusterDialogBoxState';
import { Member } from './Member';
import MemberList from './MemberList';
import SelectedMemberComponent from './SelectedMemberComponent';

interface ClusterDialogBoxProps<T extends Member> {
  members: string[];
  onStartSelectionChanged: (member: T | null) => void;
  onHubSelectionChanged: (member: T[]) => void;
}

interface StartNodeProps {
  selected: boolean;
}

const StartNode: React.FC<StartNodeProps> = ({ selected }) => {
  let title;
  if (selected) {
    title = 'Start node';
  } else {
    title = 'Select start node';
  }

  return (
    <div className="input-title">
      ‣ {title}
      <span className="experimental">🌱 experimental</span>
    </div>
  );
};

interface HubNodeProps {
  count: number;
  onClearClicked: () => void;
}

const HubNode: React.FC<HubNodeProps> = ({ count, onClearClicked }) => {
  let title: string;
  if (count === 0) {
    title = 'Select hub node';
  } else if (count === 1) {
    title = 'Hub node';
  } else {
    title = `Hub nodes (${count})`;
  }
  return (
    <div>
      <div className="input-title">
        ‣ {title}
        {count > 0 && (
          <span className="clear" onClick={onClearClicked}>
            Clear
          </span>
        )}
      </div>
    </div>
  );
};

const ClusterDialogBox: React.FC<ClusterDialogBoxProps<Member>> = ({
  members,
  onStartSelectionChanged,
  onHubSelectionChanged,
}) => {
  const [isClusterBoxVisible, setIsClusterBoxVisible] = useState(false);
  const [dialogState, setDialogState] = useState(
    ClusterDialogBoxState.initialState(members.map((member) => new Member(member))),
  );
  const startNodeInputRef = useRef<HTMLInputElement>(null);
  const dialogBoxRef = useRef<HTMLDivElement>(null);

  const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'k' && (event.metaKey || event.ctrlKey)) {
      setIsClusterBoxVisible(!isClusterBoxVisible);
    }
    if (event.key === 'Escape') {
      setIsClusterBoxVisible(false);
    }

    if (
      !isClusterBoxVisible ||
      dialogState.startNodeSelectionModel.visibleSearchResult().length === 0 ||
      (dialogState.startNodeSelectionModel.selected !== null &&
        dialogState.hubNodesSelectionModel.visibleSearchResult().length === 0)
    ) {
      return;
    }

    if (event.key === 'ArrowUp' || event.key === 'ArrowDown') {
      event.preventDefault();

      if (event.key === 'ArrowUp') {
        setDialogState(dialogState.focusPreviousMember());
      } else {
        setDialogState(dialogState.focusNextMember());
      }
    } else if (event.key === 'Enter') {
      setDialogState(dialogState.select());
    }
  };

  const handleInputChange = (event: React.FormEvent<HTMLInputElement>) => {
    const newSearchTerm = event.currentTarget.value;
    setDialogState(dialogState.search(newSearchTerm));
  };

  const handleClearClicked = () => {
    setDialogState(dialogState.deselectAll());
  };

  useEffect(() => {
    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [isClusterBoxVisible, dialogState]);

  useEffect(() => {
    if (isClusterBoxVisible && startNodeInputRef.current) {
      startNodeInputRef.current.focus();
      startNodeInputRef.current.select();
    }
  }, [isClusterBoxVisible, dialogState.startNodeSelectionModel.selected, dialogState.hubNodesSelectionModel.selected]);

  useEffect(() => {
    onStartSelectionChanged(dialogState.startNodeSelectionModel.selected);
  }, [dialogState.startNodeSelectionModel.selected]);

  useEffect(() => {
    const selectedMembers = dialogState.hubNodesSelectionModel.selected;
    onHubSelectionChanged(dialogState.hubNodesSelectionModel.selected);
  }, [dialogState.hubNodesSelectionModel.selected]);

  useEffect(() => {
    const parentContainer = dialogBoxRef.current?.parentElement;
    if (parentContainer) {
      const top = parentContainer.offsetHeight / 3;
      dialogBoxRef.current.style.top = `${top}px`;
    }
  }, [isClusterBoxVisible]);

  const dialogBoxClassName = isClusterBoxVisible ? 'cluster-box visible' : 'cluster-box hidden';

  return (
    <div className={dialogBoxClassName} ref={dialogBoxRef}>
      {dialogState.startNodeSelectionModel.selected ? (
        <React.Fragment>
          <StartNode selected={true} />
          <SelectedMemberComponent
            member={dialogState.startNodeSelectionModel.selected!}
            onRemoveClicked={() => setDialogState(dialogState.deselectStartNode())}
          />
        </React.Fragment>
      ) : (
        <React.Fragment>
          <StartNode selected={false} />
          <input
            id="startNodeInput"
            type="text"
            value={dialogState.startNodeSelectionModel.searchTerm}
            onChange={handleInputChange}
            ref={startNodeInputRef}
          />
        </React.Fragment>
      )}
      {dialogState.startNodeSelectionModel.selected && (
        <HubNode count={dialogState.hubNodesSelectionModel.selected.length} onClearClicked={handleClearClicked} />
      )}
      {dialogState.hubNodesSelectionModel.selected.length > 0 && dialogState.startNodeSelectionModel.selected && (
        <React.Fragment>
          {dialogState.hubNodesSelectionModel.selected.map((member) => (
            <SelectedMemberComponent
              key={member.nodeId}
              member={member}
              onRemoveClicked={() => setDialogState(dialogState.deselectHubNode(member))}
            />
          ))}
        </React.Fragment>
      )}
      {dialogState.startNodeSelectionModel.selected && (
        <React.Fragment>
          <input
            id="hubNodeInput"
            type="text"
            value={dialogState.hubNodesSelectionModel.searchTerm}
            onChange={handleInputChange}
            ref={startNodeInputRef}
          />
        </React.Fragment>
      )}
      {!dialogState.startNodeSelectionModel.selected && dialogState.startNodeSelectionModel.focused && (
        <MemberList
          focusedMember={dialogState.startNodeSelectionModel.focused}
          filteredMembers={dialogState.startNodeSelectionModel.visibleSearchResult()}
        />
      )}
      {dialogState.startNodeSelectionModel.selected && (
        <MemberList
          focusedMember={dialogState.hubNodesSelectionModel.focused}
          filteredMembers={dialogState.hubNodesSelectionModel.visibleSearchResult()}
        />
      )}
    </div>
  );
};

export default ClusterDialogBox;
