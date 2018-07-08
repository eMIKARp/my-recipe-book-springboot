package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
