import React from 'react';
import MemberListItem from './MemberListItem';
import { Member } from './Member';
import './MemberList.css';

interface FilteredMemberListProps {
  focusedMember: Member | null;
  filteredMembers: Member[];
}

const MemberList: React.FC<FilteredMemberListProps> = ({ focusedMember, filteredMembers }) => {
  return (
    <div className="member-list">
      {filteredMembers.map((member) => (
        <MemberListItem key={member.nodeId} member={member} focusedMember={focusedMember} />
      ))}
    </div>
  );
};

export default MemberList;
