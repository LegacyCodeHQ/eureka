import React, { useEffect, useRef, useState } from 'react';
import './ClusterDialogBox.css';
import ClusterDialogBoxState from './ClusterDialogBoxState';
import { Member } from './Member';
import MemberList from './MemberList';
import SelectedMemberComponent from './SelectedMemberComponent';

interface ClusterDialogBoxProps {
  members: string[];
  onStartMemberChanged: (member: string | null) => void;
  onBlockMemberChanged: (member: string | null) => void;
}

const StartNode: React.FC = () => {
  return (
    <div className="input-title">
      ‣ Start node
      <span className="experimental">🌱 experimental</span>
    </div>
  );
};

const BlockNode: React.FC = () => {
  return <div className="input-title">‣ Block node</div>;
};

const ClusterDialogBox: React.FC<ClusterDialogBoxProps> = ({ members, onStartMemberChanged, onBlockMemberChanged }) => {
  const [isClusterBoxVisible, setIsClusterBoxVisible] = useState(false);
  const [dialogState, setDialogState] = useState(
    ClusterDialogBoxState.initialState(members.map((member) => new Member(member))),
  );
  const startNodeInputRef = useRef<HTMLInputElement>(null);
  const dialogBoxRef = useRef<HTMLDivElement>(null);

  const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'k' && event.metaKey) {
      setIsClusterBoxVisible(!isClusterBoxVisible);
    }
    if (event.key === 'Escape') {
      setIsClusterBoxVisible(false);
    }

    if (
      !isClusterBoxVisible ||
      dialogState.startNodeSelectionModel.searchResult.length === 0 ||
      (dialogState.startNodeSelectionModel.selected && dialogState.blockNodeSelectionModel.selected)
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
  }, [isClusterBoxVisible, dialogState.startNodeSelectionModel.selected, dialogState.blockNodeSelectionModel.selected]);

  useEffect(() => {
    onStartMemberChanged(
      dialogState.startNodeSelectionModel.selected ? dialogState.startNodeSelectionModel.selected.nodeId : null,
    );
  }, [dialogState.startNodeSelectionModel.selected]);

  useEffect(() => {
    onBlockMemberChanged(
      dialogState.blockNodeSelectionModel.selected ? dialogState.blockNodeSelectionModel.selected.nodeId : null,
    );
  }, [dialogState.blockNodeSelectionModel.selected]);

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
          <StartNode />
          <SelectedMemberComponent
            member={dialogState.startNodeSelectionModel.selected!}
            onRemoveClicked={() => setDialogState(dialogState.deselectStartNode())}
          />
        </React.Fragment>
      ) : (
        <React.Fragment>
          <StartNode />
          <input
            id="startNodeInput"
            type="text"
            value={dialogState.startNodeSelectionModel.searchTerm}
            onChange={handleInputChange}
            ref={startNodeInputRef}
          />
        </React.Fragment>
      )}
      {dialogState.blockNodeSelectionModel.selected && dialogState.startNodeSelectionModel.selected && (
        <React.Fragment>
          <BlockNode />
          <SelectedMemberComponent
            member={dialogState.blockNodeSelectionModel.selected!}
            onRemoveClicked={() => setDialogState(dialogState.deselectBlockNode())}
          />
        </React.Fragment>
      )}
      {dialogState.startNodeSelectionModel.selected && !dialogState.blockNodeSelectionModel.selected && (
        <React.Fragment>
          <BlockNode />
          <input
            id="blockNodeInput"
            type="text"
            value={dialogState.blockNodeSelectionModel.searchTerm}
            onChange={handleInputChange}
            ref={startNodeInputRef}
          />
        </React.Fragment>
      )}
      {!dialogState.startNodeSelectionModel.selected && dialogState.startNodeSelectionModel.focused && (
        <MemberList
          focusedMember={dialogState.startNodeSelectionModel.focused}
          filteredMembers={dialogState.startNodeSelectionModel.searchResult}
        />
      )}
      {dialogState.startNodeSelectionModel.selected &&
        !dialogState.blockNodeSelectionModel.selected &&
        dialogState.blockNodeSelectionModel.focused && (
          <MemberList
            focusedMember={dialogState.blockNodeSelectionModel.focused}
            filteredMembers={dialogState.blockNodeSelectionModel.searchResult}
          />
        )}
    </div>
  );
};

export default ClusterDialogBox;
