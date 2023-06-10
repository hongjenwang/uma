package com.umabikerental.bike;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bikes")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bike {

    @Id
    @SequenceGenerator(
            name="bike_id_sequence",
            sequenceName = "bike_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bike_id_sequence"
    )
    private Long id;

    private String name;
    private Integer quantity;
    private String color;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modified_at;

    public Bike() {
    }

    public Bike(Long id, String name, Integer quantity, String color) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.color = color;
    }
}