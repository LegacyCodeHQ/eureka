import React, { useState, useEffect, useRef } from 'react';
import './ClusterBox.css';

interface ClusterBoxProps {
  members: string[];
}

const ClusterBox: React.FC<ClusterBoxProps> = ({ members }) => {
  const [isVisible, setIsVisible] = useState(false);
  const [inputText, setInputText] = useState('');
  const [filteredMembers, setFilteredMembers] = useState<string[]>([]);
  const inputRef = useRef<HTMLInputElement>(null);

  const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'k' && event.metaKey) {
      setIsVisible(!isVisible);
    }
    if (event.key === 'Escape') {
      setIsVisible(false);
    }
  };

  useEffect(() => {
    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [isVisible]);

  useEffect(() => {
    if (isVisible && inputRef.current) {
      inputRef.current.focus();
      inputRef.current.select();
    }
  }, [isVisible]);

  function filterMember(inputText: string, member: string): boolean {
    const trimmedInputText = inputText.trim();
    if (trimmedInputText === '' || trimmedInputText.length == 1) {
      return false;
    }
    return member.toLowerCase().includes(inputText.toLowerCase());
  }

  useEffect(() => {
    const filterMembers = () => {
      const filtered = members.filter((member) => filterMember(inputText, member));
      setFilteredMembers(filtered);
    };

    filterMembers();
  }, [inputText, members]);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputText(event.target.value);
  };

  const boxClassName = isVisible ? 'cluster-box visible' : 'cluster-box hidden';

  return (
    <div className={boxClassName}>
      {isVisible && (
        <div>
          <input type="text" value={inputText} onChange={handleInputChange} ref={inputRef} />
          {filteredMembers.map((member) => (
            <div key={member}>{member}</div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ClusterBox;
