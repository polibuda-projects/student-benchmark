FROM node:18-alpine AS build

WORKDIR /build
ARG REACT_APP_BACKEND_URL=/api/v1

COPY ./frontend/ /build/

RUN npm ci
RUN npm run build

FROM nginx:stable-alpine AS runtime

WORKDIR /app

ENV NGINX_ENVSUBST_OUTPUT_DIR=/etc/nginx

COPY --from=build /build/build/ /app/public
COPY ./docker/frontend/nginx.conf /etc/nginx/templates/nginx.conf.template