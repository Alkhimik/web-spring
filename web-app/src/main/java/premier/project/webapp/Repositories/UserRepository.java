package premier.project.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import premier.project.webapp.Models.projectUser;

public interface UserRepository extends CrudRepository<projectUser, Long> {
}
