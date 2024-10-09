package ru.liga.parcelloader.type.model.entity.parcel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "filling_symbol", nullable = false, unique = true, length = 1)
    private Character fillingSymbol;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shape_id")
    private Shape shape;

    public Parcel(ParcelDTO dto) {
        this.name = dto.getName();
        this.fillingSymbol = dto.getFillingSymbol();
        this.shape = Shape
                .builder()
                .id(dto.getShapeId())
                .build();
    }
}
