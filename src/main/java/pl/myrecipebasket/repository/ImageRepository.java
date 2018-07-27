package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
