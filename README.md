# Sistema de Gestión de Rutas de Transporte

Este proyecto es una aplicación de consola en **Java** diseñada para gestionar la logística de una flota de vehículos (Camiones y Furgonetas), los empleados asignados y las rutas realizadas.

## 🚀 Características

- **Gestión de Flota:** Implementación de jerarquía de clases con `Vehiculo` (abstracta), `Camion` y `Furgoneta`.
- **Cálculo de Carga:** Control automático de capacidad en kg para camiones y m³ para furgonetas.
- **DAO (Data Access Object):** Interfaz `IGestionRutas` para desacoplar la lógica de negocio del almacenamiento de datos.
- **Estadísticas:**
  - Filtrado de rutas por empleado, vehículo o destino.
  - Cálculo de kilómetros totales por matrícula o tipo de vehículo.
  - Estimación de consumo de combustible basado en la carga transportada.

## 🛠️ Tecnologías Utilizadas

* **Java SE 17+**
* **Programación Orientada a Objetos (POO):** Herencia, Polimorfismo e Interfaces.
* **Java Collections Framework:** Uso de `ArrayList`, `HashMap` y `Streams`.
* **Java Time API:** Gestión de fechas con `LocalDate`.

## 📋 Estructura del Proyecto

- `modelo.javabean`: Contiene las clases de datos (`Empleado`, `Vehiculo`, `Ruta`, etc.).
- `modelo.dao`: Contiene la lógica de persistencia en memoria e interfaces.
- `test`: Clases con método `main` para verificar el funcionamiento del sistema.

## ⚙️ Funcionamiento Destacado

El sistema ajusta dinámicamente el consumo del vehículo según la carga:
> "Aumenta 1L de consumo por cada 20% de carga transportada."

```java
public void modificarKilometrosYConsumoVehiculo() {
    this.vehiculoUsado.aumentarKilometraje(kmRecorridos);
    this.vehiculoUsado.setConsumoLitros100km(
        this.vehiculoUsado.getConsumoLitros100km() + (this.getVehiculoUsado().getPorcentajeCarga() / 20)
    );
}
