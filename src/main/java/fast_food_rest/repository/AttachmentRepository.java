package fast_food_rest.repository;

import fast_food_rest.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {
}
