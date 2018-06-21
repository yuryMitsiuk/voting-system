package ru.graduation.votingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.graduation.votingsystem.domain.Vote;
import ru.graduation.votingsystem.domain.VoteIdentity;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, VoteIdentity> {

    @Query("select v from Vote v left join fetch v.restaurant where v.identity.user.id=:id")
    List<Vote> findAllOfUser(@Param("id") long id);

    // 1+N problem
    @Query("select v from Vote v join fetch v.identity.user where v.restaurant.id=:restaurantId")
    List<Vote> findAllOfRestaurants(@Param("restaurantId") long restaurantId);
}
