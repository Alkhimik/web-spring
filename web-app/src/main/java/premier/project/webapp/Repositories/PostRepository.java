package premier.project.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import premier.project.webapp.Models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
