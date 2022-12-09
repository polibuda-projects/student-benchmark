import { CracoConfig } from '@craco/types';
import { resolve } from 'path';


const config:CracoConfig ={
  webpack: {
    alias: {
      '@views': resolve(__dirname, 'src/views'),
      '@components': resolve(__dirname, 'src/components'),
      '@resources': resolve(__dirname, 'src/resources'),
    },
  },
};

export default config;
