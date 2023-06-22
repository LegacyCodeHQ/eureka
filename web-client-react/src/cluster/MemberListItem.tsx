import React from 'react';
import './MemberListItem.css';

interface MemberListItemProps {
  member: string;
  focusedMember: string | null;
}

const MemberListItem: React.FC<MemberListItemProps> = ({ member, focusedMember }) => {
  const focusClassName = member === focusedMember ? 'focused' : 'unfocused';
  return <div className={`member ${focusClassName}`}>{member}</div>;
};

export default MemberListItem;
