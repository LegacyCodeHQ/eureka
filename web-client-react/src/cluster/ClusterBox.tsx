import React, { useEffect, useRef, useState } from 'react';
import './ClusterBox.css';
import MemberListItem from './MemberListItem';

interface ClusterBoxProps {
  members: string[];
}

const ClusterBox: React.FC<ClusterBoxProps> = ({ members }) => {
  const [isVisible, setIsVisible] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredMembers, setFilteredMembers] = useState<string[]>([]);
  const [focusedMember, setFocusedMember] = useState<string | null>(null);

  const startNodeInputRef = useRef<HTMLInputElement>(null);

  const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'k' && event.metaKey) {
      setIsVisible(!isVisible);
    }
    if (event.key === 'Escape') {
      setIsVisible(false);
    }

    if (isVisible && (event.key === 'ArrowUp' || event.key === 'ArrowDown')) {
      event.preventDefault();
      if (filteredMembers.length === 0) {
        return;
      }

      let currentIndex = filteredMembers.indexOf(focusedMember!);
      if (currentIndex === -1) {
        currentIndex = 0;
      }

      let nextIndex;
      if (event.key === 'ArrowUp') {
        nextIndex = currentIndex === 0 ? filteredMembers.length - 1 : currentIndex - 1;
      } else {
        nextIndex = currentIndex === filteredMembers.length - 1 ? 0 : currentIndex + 1;
      }

      setFocusedMember(filteredMembers[nextIndex]);
    }
  };

  useEffect(() => {
    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [isVisible, focusedMember, filteredMembers]);

  useEffect(() => {
    if (isVisible && startNodeInputRef.current) {
      startNodeInputRef.current.focus();
      startNodeInputRef.current.select();
    }
  }, [isVisible]);

  function filterMember(searchTerm: string, member: string): boolean {
    const trimmedSearchTerm = searchTerm.trim();
    if (trimmedSearchTerm === '' || trimmedSearchTerm.length === 1) {
      return false;
    }
    return member.toLowerCase().includes(trimmedSearchTerm.toLowerCase());
  }

  useEffect(() => {
    const filterMembers = () => {
      const filtered = members.filter((member) => filterMember(searchTerm, member));
      setFilteredMembers(filtered);
      if (filtered.length > 0) {
        setFocusedMember(filtered[0]);
      }
    };

    filterMembers();
  }, [searchTerm, members]);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  const boxClassName = isVisible ? 'cluster-box visible' : 'cluster-box hidden';

  return (
    <div className={boxClassName}>
      {isVisible && (
        <div>
          <input
            className="start-node-input"
            type="text"
            value={searchTerm}
            onChange={handleInputChange}
            ref={startNodeInputRef}
          />
          {filteredMembers.map((member) => (
            <MemberListItem key={member} member={member} focusedMember={focusedMember} />
          ))}
        </div>
      )}
    </div>
  );
};

export default ClusterBox;
