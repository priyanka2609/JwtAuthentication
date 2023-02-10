package com.pri.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pri.auth.payload.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

}
