# Sistema de Gestión de Planta Hospitalaria

Este proyecto es una aplicación de consola escrita en Java orientada a gestionar el día a día de una planta de hospital. Está desarrollado como práctica para la asignatura de Acceso a Datos del ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM) y se centra en la persistencia de información utilizando JDBC, una base de datos PostgreSQL y una arquitectura basada en el patrón DAO.

## Características principales

El programa centraliza la información de cuatro elementos clave del entorno clínico: Pacientes, Personal, Habitaciones y Procedimientos.

* **<ins>Gestión de registros (CRUD):<ins>** Permite registrar, consultar, modificar y dar de baja información en cualquiera de las cuatro entidades.
* **<ins>Informes clínicos y consultas avanzadas:<ins>** Incluye funciones específicas diseñadas con consultas SQL complejas para generar información útil en planta. Destacan la generación de historiales clínicos detallados cruzando varias tablas (JOINs), el recuento de plantilla por categoría profesional (GROUP BY) y los listados de organización de pacientes por número de cama (ORDER BY).
* **<ins>Control de entradas:<ins>** Se ha priorizado que la aplicación no se interrumpa ante fallos humanos. Incluye bucles de validación continua que atrapan excepciones comunes (como errores de formato al teclear fechas o IDs) y guían al usuario para que vuelva a introducir el dato.
* **<ins>Verificación de base de datos:<ins>** El sistema gestiona las respuestas de la base de datos comprobando las filas afectadas, de modo que alerta al usuario de forma clara si intenta actualizar o eliminar un identificador que no existe.

## Tecnologías y herramientas

* Lenguaje: Java (JDK 17)
* Acceso a Datos: JDBC
* Base de Datos Relacional: PostgreSQL
* Gestor de Dependencias: Maven
* Arquitectura: Patrón DAO y separación de lógica en capas (MVC)

## Estructura del proyecto

El código fuente está dividido en paquetes para separar claramente la interfaz de usuario, los modelos de datos y la comunicación con la base de datos:

```text
AppAccesoDatos_EV/
├── pom.xml
├── sql/
│    └── creacion_bd_hospital.sql
└── src/
    └── main/
        └── java/
            ├── Main.java
            ├── dao/
            │   ├── HabitacionDAO.java
            │   ├── PacienteDAO.java
            │   ├── PersonalDAO.java
            │   └── ProcedimientoDAO.java
            ├── database/
            │   └── DatabaseConnection.java
            ├── menus/
            │   ├── MenuHabitaciones.java
            │   ├── MenuPacientes.java
            │   ├── MenuPersonal.java
            │   └── MenuProcedimientos.java
            └── model/
                ├── Habitacion.java
                ├── Paciente.java
                ├── Personal.java
                ├── Procedimiento.java
                └── enums/
                    ├── Categoria.java
                    ├── Estado.java
                    ├── Sexo.java
                    └── Turno.java
```