package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByRole(String role);
}
