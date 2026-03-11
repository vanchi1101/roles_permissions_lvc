package com.example.nhom1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.nhom1.model.UserRole;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    Page<UserRole> getAll(Pageable pageable);

    UserRole getById(UUID id);

    UserRole assignRole(UUID userId, UUID roleId); // POST

    UserRole update(UUID id, UserRole updateData); // PUT

    void softDelete(UUID id); // DELETE (xóa mềm)

    List<UserRole> getRolesByUser(UUID userId);

    List<UserRole> getUsersByRole(UUID roleId);

    Page<UserRole> searchByUserId(UUID userId, Pageable pageable);

    UserRole lock(UUID id);

    UserRole unlock(UUID id);
}
