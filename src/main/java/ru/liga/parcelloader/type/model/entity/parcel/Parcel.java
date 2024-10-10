package ru.liga.parcelloader.type.model.entity.parcel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;

import java.util.Comparator;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.EAGER)
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

    public Parcel(List<String> layers) {
        this.shape = Shape
                .builder()
                .layers(layers
                        .stream()
                        .map(layer -> Layer
                                .builder()
                                .content(layer)
                                .build()
                        ).toList()
                ).build();
    }

    @JsonIgnore
    public int getWidth() {
        return shape
                .getLayers()
                .stream()
                .map(Layer::getContent)
                .map(String::length)
                .max(Integer::compare)
                .orElse(0);
    }

    @JsonIgnore
    public int getHeight() {
        return shape
                .getLayers()
                .size();
    }

    @JsonIgnore
    public int getWeight() {
        return shape
                .getLayers()
                .stream()
                .map(Layer::getContent)
                .map(content -> content.length() - content.codePoints()
                        .filter(codePoint -> codePoint == ' ')
                        .count())
                .map(Math::toIntExact)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @JsonIgnore
    public Layer getShapeLayer(int index) {
        return shape.getLayers().get(index);
    }

    public String toString() {
        return "name: " + name + "\n" +
                "fillingSymbol: " + fillingSymbol + "\n" +
                "shape:\n" + shape.toString();
    }
}
