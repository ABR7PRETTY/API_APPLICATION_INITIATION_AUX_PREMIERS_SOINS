package com.example.E_care.Utilisateurs.dao;

import com.example.E_care.Utilisateurs.models.Administrateur;
import com.example.E_care.Utilisateurs.models.Apprenant;
import com.example.E_care.Utilisateurs.models.SuperAdmin;
import com.example.E_care.Utilisateurs.models.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    
    @Query("SELECT a FROM User a WHERE a.role = Role.ADMIN")
    List<Administrateur> findAllAdmins();

    @Query("SELECT a FROM User a WHERE a.role = Role.APPRENANT")
    List<Apprenant> findAllApprenants();

    @Query("SELECT a FROM User a WHERE a.role = Role.ADMIN AND a.username = :username ")
    Optional<Administrateur> findAdmin(@Param("username") String username);

    @Query("SELECT a FROM User a WHERE a.role = Role.SUPERADMIN AND a.username = :username ")
    Optional<SuperAdmin> findSuperAdmin(@Param("username") String username);

}

