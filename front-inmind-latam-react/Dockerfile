# Fase de construcción
FROM node:18-alpine AS base

WORKDIR /app

# Copia los archivos de configuración de dependencias
COPY package*.json ./

# Instala las dependencias
RUN npm install

# Copia el resto de la aplicación
COPY . .

# Construye la aplicación Next.js
RUN npm run build

# Fase de producción
FROM node:18-alpine AS production

WORKDIR /app

# Copia solo los archivos necesarios desde la fase de base
COPY --from=base /app/node_modules ./node_modules
COPY --from=base /app/.next ./.next
COPY --from=base /app/public ./public
COPY --from=base /app/package*.json ./

# Establece la variable de entorno
ENV NODE_ENV=production

EXPOSE 3000

# Comando para ejecutar la aplicación
CMD ["npm", "start"]
