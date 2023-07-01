import React, { useEffect, useRef, useState } from 'react';
import './ClusterDialogBox.css';
import ClusterDialogBoxState from './ClusterDialogBoxState';
import { Member } from './Member';
import MemberList from './MemberList';
import SelectedMemberComponent from './SelectedMemberComponent';

interface ClusterDialogBoxProps<T extends Member> {
  members: string[];
  onStartSelectionChanged: (member: T | null) => void;
  onBlockSelectionChanged: (member: T[]) => void;
}

const StartNode: React.FC = () => {
  return (
    <div className="input-title">
      ‣ Start node
      <span className="experimental">🌱 experimental</span>
    </div>
  );
};

function areArrayContentsEqual<T>(a: T[], b: T[], transform: (item: T) => string) {
  const setA = new Set(a.map(transform));
  const setB = new Set(b.map(transform));

  if (setA.size !== setB.size) {
    return false;
  }

  for (const item of setA) {
    if (!setB.has(item)) {
      return false;
    }
  }

  return true;
}

interface BlockNodeProps {
  count: number;
}

const BlockNode: React.FC<BlockNodeProps> = ({ count }) => {
  let title: string;
  if (count === 0) {
    title = 'Select block node';
  } else if (count === 1) {
    title = 'Block node';
  } else {
    title = `Block nodes (${count})`;
  }
  return <div className="input-title">‣ {title}</div>;
};

const ClusterDialogBox: React.FC<ClusterDialogBoxProps<Member>> = ({
  members,
  onStartSelectionChanged,
  onBlockSelectionChanged,
}) => {
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
      (dialogState.startNodeSelectionModel.selected !== null &&
        dialogState.blockNodeSelectionModel.searchResult.length === 0)
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
    onStartSelectionChanged(dialogState.startNodeSelectionModel.selected);
  }, [dialogState.startNodeSelectionModel.selected]);

  const previousSelectedBlockedNodesRef = useRef(dialogState.blockNodeSelectionModel.selected);

  useEffect(() => {
    const selectedMembers = dialogState.blockNodeSelectionModel.selected;
    const previousSelectedMembers = previousSelectedBlockedNodesRef.current;

    if (!areArrayContentsEqual(selectedMembers, previousSelectedMembers, (member) => member.nodeId)) {
      onBlockSelectionChanged(dialogState.blockNodeSelectionModel.selected);
    }

    previousSelectedBlockedNodesRef.current = selectedMembers;
  }, [dialogState.blockNodeSelectionModel.selected, onBlockSelectionChanged]);

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
      {dialogState.startNodeSelectionModel.selected && (
        <BlockNode count={dialogState.blockNodeSelectionModel.selected.length} />
      )}
      {dialogState.blockNodeSelectionModel.selected.length > 0 && dialogState.startNodeSelectionModel.selected && (
        <React.Fragment>
          {dialogState.blockNodeSelectionModel.selected.map((member) => (
            <SelectedMemberComponent
              key={member.nodeId}
              member={member}
              onRemoveClicked={() => setDialogState(dialogState.deselectBlockNode(member))}
            />
          ))}
        </React.Fragment>
      )}
      {dialogState.startNodeSelectionModel.selected && (
        <React.Fragment>
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
      {dialogState.startNodeSelectionModel.selected && dialogState.blockNodeSelectionModel.focused && (
        <MemberList
          focusedMember={dialogState.blockNodeSelectionModel.focused}
          filteredMembers={dialogState.blockNodeSelectionModel.searchResult}
        />
      )}
    </div>
  );
};

export default ClusterDialogBox;
