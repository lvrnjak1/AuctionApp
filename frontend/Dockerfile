FROM node:12.19.0
RUN mkdir /app
WORKDIR /app
COPY package.json /app
COPY package-lock.json /app
RUN npm install --silent
COPY . /app
EXPOSE 3000
ENTRYPOINT ["npm", "start"]
