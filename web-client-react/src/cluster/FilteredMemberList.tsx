import React from 'react';
import MemberListItem from './MemberListItem';
import { Member } from './Member';

interface FilteredMemberListProps {
  focusedMember: string | null;
  filteredMembers: Member[];
}

const FilteredMemberList: React.FC<FilteredMemberListProps> = ({ focusedMember, filteredMembers }) => {
  return (
    <React.Fragment>
      {filteredMembers.map((member) => (
        <MemberListItem key={member.nodeId} member={member.nodeId} focusedMember={focusedMember} />
      ))}
    </React.Fragment>
  );
};

export default FilteredMemberList;
