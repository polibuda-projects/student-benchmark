FROM node:18-alpine

WORKDIR /app

COPY ./frontend/ /app/

RUN npm ci

ENTRYPOINT [ "npm", "start" ]