import { CracoConfig } from '@craco/types';
import { resolve } from 'path';


const config:CracoConfig ={
  webpack: {
    alias: {
      '@views': resolve(__dirname, 'src/views'),
      '@components': resolve(__dirname, 'src/components'),
    },

    configure: {
      watchOptions: {
        poll: 1000,
        ignored: /node_modules/,
      },
    },
  },
};

export default config;
