import React, { useEffect, useState } from 'react';
import './AppVersion.css';
import { useHost } from '../HostContext';

const AppVersion: React.FC = () => {
  if (process.env.NODE_ENV === 'development') {
    return <AppVersionDev />;
  }
  return <AppVersionProd />;
};

const AppVersionProd: React.FC = () => {
  const [versionName, setVersionName] = useState<string | null>(null);
  const versionEndpoint = useHost().resolveHttp('/version');

  useEffect(() => {
    fetch(versionEndpoint)
      .then((response) => response.text())
      .then((data) => {
        setVersionName(data);
      });
  }, []);

  return <span className="app-version">{versionName ? 'v' + versionName : ''}</span>;
};

const AppVersionDev: React.FC = () => {
  return <span className="app-version">v2023.1.0-DEMO</span>;
};

export default AppVersion;
