import React, { useEffect, useState } from 'react';
import './App.css';
import VocabularyPanel from './vocabulary/VocabularyPanel';
import EdgeBundlingGraph, { NodeHoverEvent } from './viz/EdgeBundlingGraph';
import GraphDataSource from './datasource/GraphDataSource';
import { GraphData } from './viz/model/GraphData';
import { getSimpleClassName } from './types/Functions';
import { ClassInfo } from './viz/model/ClassInfo';
import Toolbar from './toolbar/Toolbar';
import { Host, HostProvider } from './HostContext';
import Legend from './legend/Legend';

function App() {
  const [host, setHost] = useState<Host | null>(null);
  const [dependencyCount, setDependencyCount] = useState<number | null>(null);
  const [dependentCount, setDependentCount] = useState<number | null>(null);

  useEffect(() => {
    const hostName = document.getElementById('root')?.dataset.hostName;
    if (hostName) {
      setHost(new Host(hostName));
    }
  }, []);

  function makeTitle(classInfo: ClassInfo | undefined): string {
    return 'TWD ' + getSimpleClassName(classInfo ? classInfo.name : '');
  }

  function setTitle(title: string) {
    useEffect(() => {
      document.title = title;
    }, [title]);
  }

  const handleNodeHover = (event: NodeHoverEvent | null) => {
    setDependencyCount(event ? event.dependencyCount : null);
    setDependentCount(event ? event.dependentCount : null);
  };

  return (
    <>
      {host && (
        <HostProvider host={host}>
          <GraphDataSource>
            {(data: GraphData | null) => {
              setTitle(makeTitle(data?.meta.classInfo));

              return (
                <div>
                  <Toolbar data={data} />
                  <div className="main-panel">
                    <div className="floating-legend">
                      <Legend dependencyCount={dependencyCount} dependentCount={dependentCount} />
                    </div>
                    <div className="viz">{data && <EdgeBundlingGraph data={data} onNodeHover={handleNodeHover} />}</div>
                    {data && <VocabularyPanel data={data} />}
                  </div>
                </div>
              );
            }}
          </GraphDataSource>
        </HostProvider>
      )}
    </>
  );
}

export default App;
