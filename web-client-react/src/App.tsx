import React, { useEffect } from 'react';
import './App.css';
import VocabularyPanel from './vocabulary/VocabularyPanel';
import EdgeBundlingGraph from './viz/EdgeBundlingGraph';
import GraphDataSource from './source/GraphDataSource';
import { GraphData } from './viz/model/GraphData';
import SimpleJvmClassName from './toolbar/SimpleJvmClassName';
import { getSimpleClassName } from './types/Functions';
import { ClassInfo } from './viz/model/ClassInfo';
import AppVersion from './toolbar/AppVersion';

function App() {
  function makeTitle(classInfo: ClassInfo | undefined): string {
    return 'TWD ' + getSimpleClassName(classInfo ? classInfo.name : '');
  }

  function setTitle(title: string) {
    useEffect(() => {
      document.title = title;
    }, [title]);
  }

  return (
    <GraphDataSource>
      {(data: GraphData | null) => {
        setTitle(makeTitle(data?.meta.classInfo));

        return (
          <div>
            <div className="toolbar">
              <span className="product-name">TWD</span>
              {data && <SimpleJvmClassName classInfo={data.meta.classInfo} />}
              <AppVersion />
            </div>
            <div className="main-panel">
              <div className="viz">{data && <EdgeBundlingGraph data={data} />}</div>
              {data && <VocabularyPanel data={data} />}
            </div>
          </div>
        );
      }}
    </GraphDataSource>
  );
}

export default App;
