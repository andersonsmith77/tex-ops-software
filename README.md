## Instrucciones para ejecutar el proyecto

## Requisitos Previos

* [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y en ejecución.
* Git instalado.

## Cómo desplegar el proyecto

Sigue estos pasos para levantar la aplicación y la base de datos automáticamente:

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/andersonsmith77/tex-ops-software.git
    ```

2.  **Construir y levantar los contenedores:**
    Ejecuta el siguiente comando en la raíz del proyecto:
    ```bash
    docker-compose up --build
    ```

3.  **Verificar el estado:**
    - La API estará disponible en: `http://localhost:8080`
    - La base de datos SQL Server estará escuchando en el puerto `1433`.

4.  **Datos iniciales:**
    Al iniciar, la aplicación ejecuta un `CommandLineRunner` que carga automáticamente:
    - 2 Empleados.
    - 2 Turnos de trabajo iniciales.

## Notas de Desarrollo
- El algoritmo de solapamiento se ejecuta en la capa de servicio (`WorkShiftService`) para asegurar la compatibilidad entre tipos de datos de Java y SQL Server.
- Se utiliza `spring.jpa.hibernate.ddl-auto=update` para preservar los datos generados entre reinicios.

## Detener la aplicación
Para apagar los servicios, presiona `Ctrl + C` o ejecuta:
```bash
docker-compose down
```
## Descripción de la lógica utilizada para validar los turnos solapados
La validación de solapamiento se implementó en la capa de servicio para garantizar que un empleado no tenga dos turnos asignados en el mismo rango horario. La lógica sigue estos pasos:

* Recuperación de datos: Se obtienen todos los turnos previos del empleado desde la base de datos.

* Verificación de solapamiento: Se recorre la lista de turnos existentes comparándolos con el nuevo turno mediante la siguiente condición lógica:

* NuevoTurno.Inicio < Existente.Fin Y Existente.Inicio < NuevoTurno.Fin

* Gestión de excepciones: Si la condición anterior se cumple, se identifica un conflicto de horario y el sistema lanza una WorkShiftOverlapException, cancelando la operación de guardado y protegiendo la integridad de los datos.

```java
    public static boolean isWorkShiftOverlaping(WorkShift workShift, List<WorkShift> existingWorkShifts) {
        for (WorkShift existing : existingWorkShifts) {
        if (workShift.getId() != null && existing.getId().equals(workShift.getId())) {
        continue;
        }

        return workShift.getStartTime().isBefore(existing.getEndTime()) &&
        existing.getStartTime().isBefore(workShift.getEndTime());
        }
        return false;
        }
```

## Lista de endpoint utilizados
#### Employees
1. Listar todos los empleados. \
   GET http://localhost:8080/api/employees
2. Crear un nuevo empleado. \
   POST http://localhost:8080/api/employees
3. Editar un empleado existente. \
   PUT http://localhost:8080/api/employees/{id}
4. Eliminar un empleado (validando que no posea turnos). \
   DELETE http://localhost:8080/api/employees/{id}
5. Activar o desactivar un empleado. \
   (Se usa el mismo que para editar)

#### WorkShifts

1. Listar los turnos asignados a un empleado. \
   GET http://localhost:8080/api/work_shifts/employees/{employeeId}
2. Crear un turno. \
   POST http://localhost:8080/api/work_shifts/employees/{employeeId}
3. Editar un turno existente. \
   PUT http://localhost:8080/api/work_shifts/{id}
4. Eliminar un turno. \
   DELETE http://localhost:8080/api/work_shifts/{id}

## Detalles de la base de datos utilizada
Microsoft SQL SERVER