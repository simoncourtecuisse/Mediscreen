FROM node:16-alpine
ENV NODE_ENV=production
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
ENTRYPOINT ["npm", "run", "serve"]
#CMD npm run build
EXPOSE 8080

#
#### STAGE 1: Build ###
#FROM node:16-alpine AS build
#WORKDIR /app
#COPY package.json ./
#RUN npm install
#COPY . .
#RUN npm run build
#
#### STAGE 2: Run ###
#FROM nginx:1.17.1-alpine
#COPY default.conf /etc/nginx/conf.d/default.conf
#COPY --from=build /usr/src/app/dist/mediscreen-ui /usr/share/nginx/html
#EXPOSE 8080