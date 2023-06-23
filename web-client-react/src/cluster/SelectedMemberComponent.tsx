import React from 'react';
import './SelectedMemberComponent.css';

interface SelectedMemberComponentProps {
  member: string;
  onRemoveClicked: (member: string) => void;
}

const SelectedMemberComponent: React.FC<SelectedMemberComponentProps> = ({ member, onRemoveClicked }) => {
  function isMethod(nodeId: string) {
    return nodeId.endsWith(')');
  }

  const memberClass = isMethod(member) ? 'method' : 'field';
  const memberChar = isMethod(member) ? 'm' : 'f';

  return (
    <div className="selected-member-container">
      <div className={`member-icon ${memberClass}`}>{memberChar}</div>
      <div className="member">{member}</div>
      <div className="remove-button" onClick={() => onRemoveClicked(member)}>
        x
      </div>
    </div>
  );
};

export default SelectedMemberComponent;
