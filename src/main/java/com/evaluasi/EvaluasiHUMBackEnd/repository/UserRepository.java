package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    User findByIduser(Long iduser);

    // @Query("SELECT u FROM User u WHERE u.karyawan.idkar = :idkar")
    // Optional<User> findByIdkar(@Param("idkar") Long idkar);

    @Query("select u from User u where u.username=:username")
    User findByUsernameId(@Param("username") String username);

    boolean existsByUsername(String username);
}
