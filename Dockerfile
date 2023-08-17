FROM node@sha256:fda98168118e5a8f4269efca4101ee51dd5c75c0fe56d8eb6fad80455c2f5827 as builder

RUN mkdir /app
WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json
COPY public /app/public
COPY src /app/src
RUN npm install
RUN npm install react-router react-router-dom
RUN npm install axios


CMD ["npm", "start"]