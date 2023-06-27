import { Member } from './Member';
import React from 'react';
import './MemberIcon.css';

interface MemberIconProps {
  member: Member;
}

const MemberIcon: React.FC<MemberIconProps> = ({ member }) => {
  const memberClass = member.isMethod() ? 'method' : 'field';
  const memberChar = member.isMethod() ? 'm' : 'f';

  return <div className={`member-icon ${memberClass}`}>{memberChar}</div>;
};

export default MemberIcon;
