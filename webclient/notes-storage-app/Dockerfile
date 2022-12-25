FROM node:19.3.0 AS builder
WORKDIR /app
COPY package*.json /
RUN npm install
COPY . .
ENV PUBLIC_URL=http://localhost
RUN npm run build

FROM nginx:1.23.3-alpine
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
RUN rm /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/nginx.conf
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /app/build .
EXPOSE 3000
ENTRYPOINT ["nginx", "-g", "daemon off;"]