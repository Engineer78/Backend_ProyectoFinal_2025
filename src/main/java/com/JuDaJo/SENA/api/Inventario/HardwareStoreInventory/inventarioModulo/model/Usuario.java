package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * La anotación @Entity indica que la clase Usuario representa una entidad persistente,
 * es decir, una tabla dentro de la base de datos gestionada por JPA (Jakarta Persistence API).
 * Esta clase será utilizada para mapear los registros de la tabla correspondiente a usuarios.
 */
@Entity
public class Usuario {
    /*
    * Identificador Unico de Usuario
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;


}
