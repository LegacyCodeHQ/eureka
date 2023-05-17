import { ClassInfo } from './viz/model/ClassInfo';
import React from 'react';
import { getSimpleClassName } from './types/Functions';

interface SimpleJvmClassNameProps {
  classInfo: ClassInfo;
}

const SimpleJvmClassName: React.FC<SimpleJvmClassNameProps> = ({ classInfo }) => {
  return <span>{getSimpleClassName(classInfo.name)}</span>;
};

export default SimpleJvmClassName;
