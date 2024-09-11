package com.app.restserver.persistance.core;

import com.app.restserver.entities.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    default User findByEmailIfExists(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with email:%s does not exist", email)));
    }

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User set lastLogin = :lastLogin where id = :userId")
    void setLastLogin(@Param("userId") Long id, @Param("lastLogin") LocalDateTime lastLogin);
}
