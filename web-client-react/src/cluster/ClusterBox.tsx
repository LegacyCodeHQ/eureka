import React, { useState, useEffect, useRef } from 'react';
import './ClusterBox.css';

interface ClusterBoxProps {
  text: string;
}

const ClusterBox: React.FC<ClusterBoxProps> = ({ text }) => {
  const [isVisible, setIsVisible] = useState(false);
  const [inputText, setInputText] = useState(text);
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

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputText(event.target.value);
  };

  const boxClassName = isVisible ? 'cluster-box visible' : 'cluster-box hidden';

  return (
    <div className={boxClassName}>
      {isVisible && <input type="text" value={inputText} onChange={handleInputChange} ref={inputRef} />}
    </div>
  );
};

export default ClusterBox;
