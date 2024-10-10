package ru.liga.parcelloader.type.model.entity.parcel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.parcelloader.api.dto.parcel.LayerDTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Layer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    @Column(name = "shape_id")
    private Integer shapeId;

    public Layer(LayerDTO layerDTO) {
        this.content = layerDTO.getContent();
        this.shapeId = layerDTO.getShapeId();
    }

    public String toString() {
        return content;
    }
}
