package ru.liga.parcelloader.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.parcelloader.type.model.entity.parcel.Layer;

public interface LayerRepository extends JpaRepository<Layer, Integer> { }
