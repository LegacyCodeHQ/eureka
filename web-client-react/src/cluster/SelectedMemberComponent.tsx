import React from 'react';
import './SelectedMemberComponent.css';
import { Member } from './Member';
import MemberIcon from './MemberIcon';

interface SelectedMemberComponentProps {
  member: Member;
  onRemoveClicked: () => void;
}

const SelectedMemberComponent: React.FC<SelectedMemberComponentProps> = ({ member, onRemoveClicked }) => {
  return (
    <div className="selected-member">
      <MemberIcon member={member} />
      {member.nodeId}
      <div className="remove-button" onClick={() => onRemoveClicked()}>
        x
      </div>
    </div>
  );
};

export default SelectedMemberComponent;
