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
import { WsConnectionStatus } from './toolbar/LiveUpdatesStatus';
import { Count, NodeHoverEvent } from './viz/NodeHoverEvent';
import ClusterBox from './cluster/ClusterBox';

function App() {
  const [host, setHost] = useState<Host | null>(null);
  const [count, setCount] = useState<Count | null>(null);
  const [connectionStatus, setConnectionStatus] = useState(WsConnectionStatus.Disconnected);
  const [startMember, setStartMember] = useState<string | null>(null);

  useEffect(() => {
    const hostName = document.getElementById('root')?.dataset.hostName;
    if (hostName) {
      setHost(new Host(hostName));
    }
  }, []);

  function makeTitle(classInfo: ClassInfo | undefined): string {
    return 'EUREKA ' + (classInfo ? `• ${getSimpleClassName(classInfo.name)}` : '');
  }

  function setTitle(title: string) {
    useEffect(() => {
      document.title = title;
    }, [title]);
  }

  const handleNodeHover = (event: NodeHoverEvent | null) => {
    setCount(event ? event.count : null);
  };

  const handleConnectionStatus = (connectionStatus: WsConnectionStatus) => {
    setConnectionStatus(connectionStatus);
  };

  const handleStartMemberChanged = (member: string | null) => {
    setStartMember(member);
  };

  return (
    <>
      {host && (
        <HostProvider host={host}>
          <GraphDataSource onConnectionStatusChange={handleConnectionStatus}>
            {(data: GraphData | null) => {
              setTitle(makeTitle(data?.meta.classInfo));
              const classInfo = data?.meta.classInfo;

              return (
                <div>
                  {classInfo && <Toolbar classInfo={classInfo} connectionStatus={connectionStatus} />}
                  <div className="main-panel">
                    <div className="floating-legend">
                      <Legend count={count} />
                    </div>
                    {data && <ClusterBox members={data.members()} onStartMemberChanged={handleStartMemberChanged} />}
                    <div className="viz">
                      {data && (
                        <EdgeBundlingGraph data={data} startNodeId={startMember} onNodeHover={handleNodeHover} />
                      )}
                    </div>
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
