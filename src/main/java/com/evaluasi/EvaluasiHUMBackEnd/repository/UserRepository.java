package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT p FROM User p WHERE p.role = :role")
    Page<User> findByRole(String role, Pageable pageable);

    @Query("SELECT p FROM User p WHERE p.username = :username")
    Page<User> findByUsername(String username, Pageable pageable);

    Page<User> findByUsernameAndRole(String username, String role, Pageable pageable);

    Optional<User> findByKaryawan(Karyawan karyawan);
    boolean existsByUsername(String username);

    @Query("SELECT k FROM User k WHERE LOWER(k.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

    @Query("SELECT k FROM User k WHERE LOWER(k.role) LIKE LOWER(CONCAT('%', :role, '%'))")
    Page<User> findByRoleContainingIgnoreCase(String role, Pageable pageable);

    @Query("SELECT k FROM User k WHERE LOWER(k.username) = LOWER(:username) AND LOWER(k.role) LIKE LOWER(CONCAT('%', :role, '%'))")
    Page<User> findByUsernameAndRoleContainingIgnoreCase(String username, String role, Pageable pageable);
}
