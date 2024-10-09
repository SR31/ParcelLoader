package ru.liga.parcelloader.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.parcelloader.api.dto.parcel.ParcelDTO;
import ru.liga.parcelloader.type.model.entity.parcel.Parcel;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
    @Modifying
    @Query("UPDATE Parcel SET name = :name WHERE id = :id")
    int updateName(@Param("name") String name, @Param("id") int id);

    @Modifying
    @Query("UPDATE Parcel SET shape.id = :shapeId WHERE id = :id")
    int updateShapeId(@Param("shapeId") int shapeId, @Param("id") int id);
}
