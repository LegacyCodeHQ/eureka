import React from 'react';
import './SelectedMemberComponent.css';
import { Member } from './Member';

interface SelectedMemberComponentProps {
  member: Member;
  onRemoveClicked: () => void;
}

const SelectedMemberComponent: React.FC<SelectedMemberComponentProps> = ({ member, onRemoveClicked }) => {
  function isMethod(member: Member) {
    return member.nodeId.endsWith(')');
  }

  const memberClass = isMethod(member) ? 'method' : 'field';
  const memberChar = isMethod(member) ? 'm' : 'f';

  return (
    <div className="selected-member-container">
      <div className={`member-icon ${memberClass}`}>{memberChar}</div>
      <div className="member">{member.nodeId}</div>
      <div className="remove-button" onClick={() => onRemoveClicked()}>
        x
      </div>
    </div>
  );
};

export default SelectedMemberComponent;
