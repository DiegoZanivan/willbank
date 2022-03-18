FROM openjdk:11

COPY build/libs/bank-0.0.1-SNAPSHOT.jar /willbank.jar

CMD ["java", "-jar", "willbank.jar"]