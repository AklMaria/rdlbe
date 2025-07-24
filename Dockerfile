# Fase 1: Build dell'applicazione con Maven
# Usiamo un'immagine con JDK e Maven per compilare il progetto.
# 'eclipse-temurin' è un'ottima scelta multi-architettura.
FROM eclipse-temurin:17-jdk-jammy as builder

# Imposta la directory di lavoro
WORKDIR /app

# Copia prima il pom.xml per sfruttare la cache di Docker
COPY pom.xml .
COPY .mvn/ .mvn
COPY mvnw .

# Scarica le dipendenze
RUN ./mvnw dependency:go-offline

# Copia il resto del codice sorgente
COPY src ./src

# Pulisce e crea il file .jar eseguibile
# Assicurati che mvnw sia eseguibile! (vedi sotto)
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests


# Fase 2: Creazione dell'immagine finale
# Partiamo da un'immagine leggera con solo il Java Runtime Environment.
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia solo il file .jar creato nella fase precedente
# Il wildcard (*) gestisce i nomi di versione diversi nel .jar
COPY --from=builder /app/target/*.jar app.jar

# Specifica la porta su cui l'applicazione sarà in ascolto
EXPOSE 8082

# Questo è il comando FONDAMENTALE per avviare l'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]