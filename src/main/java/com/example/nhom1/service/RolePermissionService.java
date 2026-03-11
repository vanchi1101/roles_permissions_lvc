package com.example.nhom1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.nhom1.model.RolePermission;

import java.util.List;
import java.util.UUID;

public interface RolePermissionService {
    Page<RolePermission> getAll(Pageable pageable);

    RolePermission getById(UUID id);

    RolePermission assignRole(UUID userId, UUID roleId); // POST

    RolePermission update(UUID id, RolePermission updateData); // PUT

    void softDelete(UUID id); // DELETE (xóa mềm)

    List<RolePermission> getRolesByPermission(UUID userId);

    List<RolePermission> getPermissionsByRole(UUID roleId);

    Page<RolePermission> searchByUserId(UUID userId, Pageable pageable);

    RolePermission lock(UUID id);

    RolePermission unlock(UUID id);
}
