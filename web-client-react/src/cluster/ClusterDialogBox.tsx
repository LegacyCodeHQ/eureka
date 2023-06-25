import React, { useEffect, useRef, useState } from 'react';
import './ClusterDialogBox.css';
import FilteredMemberList from './FilteredMemberList';
import SelectedMemberComponent from './SelectedMemberComponent';
import ClusterDialogBoxState from './ClusterDialogBoxState';
import { Member } from './Member';

interface ClusterSelection {
  startMember: string | null;
}

interface ClusterDialogBoxProps {
  members: string[];
  onStartMemberChanged: (member: string | null) => void;
}

const ClusterDialogBox: React.FC<ClusterDialogBoxProps> = ({ members, onStartMemberChanged }) => {
  const [isClusterBoxVisible, setIsClusterBoxVisible] = useState(false);
  const [filteredMembers, setFilteredMembers] = useState<string[]>([]);
  const [focusedMember, setFocusedMember] = useState<string | null>(null);
  const [clusterSelection, setClusterSelection] = useState<ClusterSelection>({ startMember: null });

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

    if (!isClusterBoxVisible) {
      return;
    }

    if (event.key === 'ArrowUp' || event.key === 'ArrowDown') {
      event.preventDefault();
      if (filteredMembers.length === 0 || !focusedMember) {
        return;
      }

      let currentIndex = filteredMembers.indexOf(focusedMember);
      if (currentIndex === -1) {
        currentIndex = 0;
      }

      let nextIndex;
      if (event.key === 'ArrowUp') {
        nextIndex = currentIndex === 0 ? filteredMembers.length - 1 : currentIndex - 1;
      } else {
        nextIndex = currentIndex === filteredMembers.length - 1 ? 0 : currentIndex + 1;
      }

      setFocusedMember(filteredMembers[nextIndex]);
    } else if (event.key === 'Enter') {
      setClusterSelection({ startMember: focusedMember });
    }
  };

  useEffect(() => {
    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [isClusterBoxVisible, focusedMember, filteredMembers]);

  useEffect(() => {
    if (isClusterBoxVisible && startNodeInputRef.current) {
      startNodeInputRef.current.focus();
      startNodeInputRef.current.select();
    }
  }, [isClusterBoxVisible, clusterSelection]);

  function filterMember(searchTerm: string, member: string): boolean {
    const trimmedSearchTerm = searchTerm.trim();
    if (trimmedSearchTerm === '' || trimmedSearchTerm.length === 1) {
      return false;
    }
    return member.toLowerCase().includes(trimmedSearchTerm.toLowerCase());
  }

  useEffect(() => {
    const filterMembers = () => {
      const filtered = members.filter((member) => filterMember(dialogState.searchTerm, member));
      setFilteredMembers(filtered);
      if (filtered.length > 0) {
        setFocusedMember(filtered[0]);
      }
    };

    filterMembers();
  }, [members, dialogState]);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDialogState(dialogState.search(event.target.value));
  };

  const boxClassName = isClusterBoxVisible ? 'cluster-box visible' : 'cluster-box hidden';

  function isStartNodeSelected(): boolean {
    return clusterSelection.startMember !== null;
  }

  function removeSelectedStartNode() {
    setClusterSelection({ startMember: null });
  }

  useEffect(() => {
    onStartMemberChanged(clusterSelection.startMember);
  }, [clusterSelection]);

  return (
    <div className={boxClassName}>
      {isClusterBoxVisible && (
        <div>
          {isStartNodeSelected() ? (
            <SelectedMemberComponent
              member={clusterSelection.startMember!}
              onRemoveClicked={(member) => removeSelectedStartNode()}
            />
          ) : (
            <React.Fragment>
              <label htmlFor="startNodeInput">Cluster member</label>
              <input
                id="startNodeInput"
                className="start-node-input"
                type="text"
                value={dialogState.searchTerm}
                onChange={handleInputChange}
                ref={startNodeInputRef}
              />
            </React.Fragment>
          )}
          {!isStartNodeSelected() && (
            <FilteredMemberList focusedMember={focusedMember} filteredMembers={filteredMembers} />
          )}
        </div>
      )}
    </div>
  );
};

export default ClusterDialogBox;
