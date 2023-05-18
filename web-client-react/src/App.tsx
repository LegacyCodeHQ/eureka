import React, { useEffect } from 'react';
import './App.css';
import VocabularyPanel from './vocabulary/VocabularyPanel';
import EdgeBundlingGraph from './viz/EdgeBundlingGraph';
import GraphDataSource from './datasource/GraphDataSource';
import { GraphData } from './viz/model/GraphData';
import { getSimpleClassName } from './types/Functions';
import { ClassInfo } from './viz/model/ClassInfo';
import Toolbar from './toolbar/Toolbar';
import { Host, HostProvider } from './HostContext';

function App() {
  const host = new Host('localhost:7070');

  function makeTitle(classInfo: ClassInfo | undefined): string {
    return 'TWD ' + getSimpleClassName(classInfo ? classInfo.name : '');
  }

  function setTitle(title: string) {
    useEffect(() => {
      document.title = title;
    }, [title]);
  }

  return (
    <HostProvider host={host}>
      <GraphDataSource>
        {(data: GraphData | null) => {
          setTitle(makeTitle(data?.meta.classInfo));

          return (
            <div>
              <Toolbar data={data} />
              <div className="main-panel">
                <div className="viz">{data && <EdgeBundlingGraph data={data} />}</div>
                {data && <VocabularyPanel data={data} />}
              </div>
            </div>
          );
        }}
      </GraphDataSource>
    </HostProvider>
  );
}

export default App;
