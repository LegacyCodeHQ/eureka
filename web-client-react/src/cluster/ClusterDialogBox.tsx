import React, { useEffect, useRef, useState } from 'react';
import './ClusterDialogBox.css';
import ClusterDialogBoxState from './ClusterDialogBoxState';
import { Member } from './Member';
import FilteredMemberList from './FilteredMemberList';
import SelectedMemberComponent from './SelectedMemberComponent';

interface ClusterDialogBoxProps {
  members: string[];
  onStartMemberChanged: (member: string | null) => void;
}

const ClusterDialogBox: React.FC<ClusterDialogBoxProps> = ({ members, onStartMemberChanged }) => {
  const [isClusterBoxVisible, setIsClusterBoxVisible] = useState(false);
  const [dialogState, setDialogState] = useState(
    ClusterDialogBoxState.initialState(members.map((member) => new Member(member))),
  );
  const startNodeInputRef = useRef<HTMLInputElement>(null);

  const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'k' && event.metaKey) {
      setIsClusterBoxVisible(!isClusterBoxVisible);
    }
    if (event.key === 'Escape') {
      setIsClusterBoxVisible(false);
    }

    if (
      !isClusterBoxVisible ||
      dialogState.startNodeSelectionModel.filteredMembers.length === 0 ||
      dialogState.startNode
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
      setDialogState(dialogState.selectStartNode());
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
  }, [isClusterBoxVisible, dialogState.startNode]);

  useEffect(() => {
    onStartMemberChanged(dialogState.startNode ? dialogState.startNode.nodeId : null);
  }, [dialogState.startNode]);

  const dialogBoxClassName = isClusterBoxVisible ? 'cluster-box visible' : 'cluster-box hidden';

  return (
    <div className={dialogBoxClassName}>
      {dialogState.startNode ? (
        <SelectedMemberComponent
          member={dialogState.startNode!}
          onRemoveClicked={() => setDialogState(dialogState.deselectStartNode())}
        />
      ) : (
        <React.Fragment>
          <label htmlFor="startNodeInput">Cluster member</label>
          <input
            id="startNodeInput"
            className="start-node-input"
            type="text"
            value={dialogState.startNodeSelectionModel.searchTerm}
            onChange={handleInputChange}
            ref={startNodeInputRef}
          />
        </React.Fragment>
      )}
      {!dialogState.startNode && dialogState.startNodeSelectionModel.focusedMember && (
        <FilteredMemberList
          focusedMember={dialogState.startNodeSelectionModel.focusedMember.nodeId}
          filteredMembers={dialogState.startNodeSelectionModel.filteredMembers}
        />
      )}
    </div>
  );
};

export default ClusterDialogBox;
