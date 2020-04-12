FROM node:12

WORKDIR /app

COPY . .

RUN npx npm-force-resolutions

RUN npm install

RUN npm install http-server -g

RUN npm run build

WORKDIR  /app/dist/covidMapper

ENTRYPOINT ["http-server", "-c1", "-p", "80"]

