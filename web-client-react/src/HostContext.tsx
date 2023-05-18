import React, { createContext, ReactNode, useContext } from 'react';

export class Host {
  name: string;

  constructor(name: string) {
    this.name = name;
  }

  resolveHttp(path: string): string {
    return `http://${this.name}/${path}`;
  }

  resolveWs(path: string): string {
    return `ws://${this.name}/${path}`;
  }
}

interface HostContextType {
  host: Host;
}

const HostContext = createContext<HostContextType | undefined>(undefined);

export const HostProvider: React.FC<HostContextType & { children: ReactNode }> = ({ host, children }) => {
  return <HostContext.Provider value={{ host: host }}>{children}</HostContext.Provider>;
};

export const useHost = (): Host => {
  const context = useContext(HostContext);
  if (!context) {
    throw new Error(`"'useHost' must be used within a 'HostProvider'"`);
  }
  return context.host;
};

export { HostContext };
