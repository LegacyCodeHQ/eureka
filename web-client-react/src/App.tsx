import React, { useEffect, useState } from 'react';
import './App.css';
import VocabularyPanel from './vocabulary/VocabularyPanel';
import EdgeBundlingGraph from './viz/EdgeBundlingGraph';
import GraphDataSource from './datasource/GraphDataSource';
import { GraphData } from './viz/model/GraphData';
import { getSimpleClassName } from './types/Functions';
import { ClassInfo } from './viz/model/ClassInfo';
import Toolbar from './toolbar/Toolbar';
import { Host, HostProvider } from './HostContext';
import Legend from './legend/Legend';

function App() {
  const [host, setHost] = useState<Host | null>(null);

  useEffect(() => {
    const hostName = document.getElementById('root')?.dataset.hostName;
    if (hostName) {
      setHost(new Host(hostName));
    }
  });

  function makeTitle(classInfo: ClassInfo | undefined): string {
    return 'TWD ' + getSimpleClassName(classInfo ? classInfo.name : '');
  }

  function setTitle(title: string) {
    useEffect(() => {
      document.title = title;
    }, [title]);
  }

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
                      <Legend dependencies={5} dependents={12} />
                    </div>
                    <div className="viz">{data && <EdgeBundlingGraph data={data} />}</div>
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
