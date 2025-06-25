package com.tienda_abarrotes.jefrey_torres_java_campus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idCliente;

    private Integer cantidadComprada;

    private LocalDate fechaVenta;

    private Double totalVenta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
