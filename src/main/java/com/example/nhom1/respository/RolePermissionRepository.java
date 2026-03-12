package com.example.nhom1.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.nhom1.model.RolePermission;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {

    Page<RolePermission> findAll(Pageable pageable);

    List<RolePermission> findByIsActiveTrue();

    Page<RolePermission> findByIsActive(Boolean isActive, Pageable pageable);

    List<RolePermission> findByRoleId(UUID roleId);

    List<RolePermission> findByPermissionId(UUID permissionId);

    @Query("SELECT rp FROM RolePermission rp WHERE rp.roleId = :roleId AND rp.deletedAt IS NULL")
    List<RolePermission> findActivePermissionsByRoleId(UUID roleId);

    @Query("SELECT rp FROM RolePermission rp WHERE rp.permissionId = :permissionId AND rp.deletedAt IS NULL")
    List<RolePermission> findActiveRolesByPermissionId(UUID permissionId);
}