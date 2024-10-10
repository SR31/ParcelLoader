package ru.liga.parcelloader.type.model.entity.parcel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shape_id")
    private List<Layer> layers;

    public String toString() {
        return layers
                .stream()
                .map(Layer::toString)
                .collect(Collectors.joining("\n"));
    }
}
