package com.taskify.repositories;

import com.taskify.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {

    public List<RoleModel> findByUserId(Integer userId);

}
