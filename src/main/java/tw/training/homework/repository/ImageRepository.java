package tw.training.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.training.homework.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
