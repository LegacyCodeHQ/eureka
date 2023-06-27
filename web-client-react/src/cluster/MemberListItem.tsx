import React from 'react';
import { Member } from './Member';
import MemberIcon from './MemberIcon';

interface MemberListItemProps {
  member: Member;
  focusedMember: Member | null;
}

const MemberListItem: React.FC<MemberListItemProps> = ({ member, focusedMember }) => {
  const focusClassName = member === focusedMember ? 'focused' : 'unfocused';
  return (
    <div className={`member ${focusClassName}`}>
      <MemberIcon member={member} />
      {member.nodeId}
    </div>
  );
};

export default MemberListItem;
