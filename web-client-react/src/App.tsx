import React, { useEffect, useMemo, useState } from 'react';
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
import ClusterDialogBox from './cluster/ClusterDialogBox';
import { Member } from './cluster/Member';

function App() {
  const [host, setHost] = useState<Host | null>(null);
  const [count, setCount] = useState<Count | null>(null);
  const [connectionStatus, setConnectionStatus] = useState(WsConnectionStatus.Disconnected);
  const [startMember, setStartMember] = useState<Member | null>(null);
  const [blockedMembers, setBlockedMembers] = useState<Member[]>([]);

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

  const handleStartSelectionChanged = (selection: Member | null) => {
    setStartMember(selection);
  };

  const handleBlockSelectionChanged = (selection: Member[]) => {
    setBlockedMembers(selection);
  };

  return (
    <>
      {host && (
        <HostProvider host={host}>
          <GraphDataSource onConnectionStatusChange={handleConnectionStatus}>
            {(data: GraphData | null) => {
              setTitle(makeTitle(data?.meta.classInfo));
              const classInfo = data?.meta.classInfo;
              const blockedNodeIds = useMemo(() => {
                return blockedMembers.map((member) => member.nodeId);
              }, [blockedMembers]);

              return (
                <div>
                  {classInfo && (
                    <Toolbar
                      classInfo={classInfo}
                      classStats={data?.classStats()}
                      connectionStatus={connectionStatus}
                    />
                  )}
                  <div className="main-panel">
                    {data && (
                      <div className="floating-legend">
                        <Legend count={count} />
                      </div>
                    )}
                    <div className="viz">
                      {data && (
                        <ClusterDialogBox
                          members={data.members()}
                          onStartSelectionChanged={handleStartSelectionChanged}
                          onBlockSelectionChanged={handleBlockSelectionChanged}
                        />
                      )}
                      {data && (
                        <EdgeBundlingGraph
                          data={data}
                          startNodeId={startMember?.nodeId ? startMember.nodeId : null}
                          blockedNodeIds={blockedNodeIds}
                          onNodeHover={handleNodeHover}
                        />
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
