import React from 'react';
import MemberListItem from './MemberListItem';

interface FilteredMemberListProps {
  focusedMember: string | null;
  filteredMembers: string[];
}

const FilteredMemberList: React.FC<FilteredMemberListProps> = ({ focusedMember, filteredMembers }) => {
  return (
    <React.Fragment>
      {filteredMembers.map((member) => (
        <MemberListItem key={member} member={member} focusedMember={focusedMember} />
      ))}
    </React.Fragment>
  );
};

export default FilteredMemberList;
