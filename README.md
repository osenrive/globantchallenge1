# API REST Migración de datos

## Descripción general:

***
Esta API REST permite mover datos de archivos planos en formato .CSV a una base de datos MySQL:

hired_employees.csv
departments.csv
jobs.csv

La API tiene como caracteristica realiza Backup de estas tablas en formato .AVRO, como su restauracion respectiva.
La API expone endpoints para consultar los reportes para el usuario final.

## Tecnologia usada

***

- Java 21 y Spring Boot
- MySQL
- Postman

## Instalación y Configuración

***

- Clonar el repositorio git clone https://github.com/osenrive/globantchallenge1.git

- Ingresar al directorio del proyecto

  cd nombre-del-repositorio

Compilar y ejecutar la aplicación
./mvnw spring-boot:run

## Endpoints

***
1. URL: http://localhost:8080/api/upload?entityName=hiredEmployee&filePath=C:/Users/oeriv/Desktop/hired_employees.csv

   Método: POST

   Descripción: Mueve el archivo hired_employees.csv a la base de datos MySQL.

   Parámetros: C:/Users/oeriv/Desktop/hired_employees.csv

   Respuesta: Archivo procesado. Transaction ID: f32efada-660f-42ad-8cb3-ce46e1c064bd


2. URL: http://localhost:8080/api/upload?entityName=department&filePath=C:/Users/oeriv/Desktop/departments.csv

   Método: POST

   Descripción: Mueve el archivo departments.csv a la base de datos MySQL.

   Parámetros: C:/Users/oeriv/Desktop/departments.csv

   Respuesta: Archivo procesado. Transaction ID: 269af2c4-d430-4a06-a97b-c790efcabc04


3. URL: http://localhost:8080/api/upload?entityName=job&filePath=C:/Users/oeriv/Desktop/jobs.csv

   Método: POST

   Descripción: Mueve el archivo jobs.csv a la base de datos MySQL.

   Parámetros: C:/Users/oeriv/Desktop/jobs.csv

   Respuesta: Archivo procesado. Transaction ID: 185e4565-d0db-4828-89f8-611563ffff04


4. URL: http://localhost:8080/api/backup

   Método: GET

   Descripción: Realiza el backup de las tablas de la base de datos MySQL.

   Requisitos: Crear carpeta Backup en la unidad C

   Respuesta: Backup completado.


5. URL: http://localhost:8080/api/restore?entityName=hired_employee

   Método: POST

   Descripción: Realiza la restauracion del backup hired_employees_backup.avro a la base de datos MySQL.

   Parámetros: hired_employee

   Respuesta: Backup de la entidad: hired_employee restaurado


6. URL: http://localhost:8080/api/restore?entityName=department

   Método: POST

   Descripción: Realiza la restauracion del backup deparments_backup.avro a la base de datos MySQL.

   Parámetros: department

   Respuesta: Backup de la entidad: department restaurado


7. URL: http://localhost:8080/api/restore?entityName=job

   Método: POST

   Descripción: Realiza la restauracion del backup jobs_backup.avro a la base de datos MySQL.

   Parámetros: job

   Respuesta: Backup de la entidad: job restaurado


8. URL: http://localhost:8080/api/reports/hiredEmployees

   Método: GET

   Descripción: Genera el reporte de los empleados contratos por Cuatrienio

   Parámetros: N/A

   Respuesta:

   [
   {
   "department": "Accounting",
   "job": "Account Representative IV",
   "firstQuarter": 1,
   "secondQuarter": 0,
   "thirdQuarter": 0,
   "fourthQuarter": 0
   },
   {
   "department": "Accounting",
   "job": "Actuary",
   "firstQuarter": 0,
   "secondQuarter": 1,
   "thirdQuarter": 0,
   "fourthQuarter": 0
   }


9. URL: http://localhost:8080/api/reports/topHiredEmployees

   Método: GET

   Descripción: Genera el reporte de de los empleados contratos por departamento y a su vez que superen el promedio de
   contratacion por los departamentos.

   Parámetros: N/A

   Respuesta:

   [
   {
   "id": 8,
   "departmentName": "Support",
   "hired": 216
   },
   {
   "id": 5,
   "departmentName": "Engineering",
   "hired": 205
   }

## Licencia

***
The MIT license (MIT)

Copyright (c) 2022
