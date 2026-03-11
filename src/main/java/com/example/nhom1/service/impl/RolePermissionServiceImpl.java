package com.example.nhom1.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nhom1.model.RolePermission;
import com.example.nhom1.respository.RolePermissionRepository;
import com.example.nhom1.service.RolePermissionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository repository;

    public RolePermissionServiceImpl(RolePermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<RolePermission> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public RolePermission getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy"));
    }

    @Override
    public RolePermission assignRole(UUID userId, UUID roleId) {
        RolePermission ur = new RolePermission();
        ur.setId(UUID.randomUUID());
        ur.setRoleId(roleId);
        ur.setCreatedAt(LocalDateTime.now());
        ur.setIsActive(true);
        // created_by = null hoặc lấy từ SecurityContext nếu có
        return repository.save(ur);
    }

    @Override
    public RolePermission update(UUID id, RolePermission updateData) {
        RolePermission existing = getById(id);
        if (updateData.getRoleId() != null)
            existing.setRoleId(updateData.getRoleId());
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    @Override
    public void softDelete(UUID id) {
        RolePermission ur = getById(id);
        ur.setDeletedAt(LocalDateTime.now());
        ur.setIsActive(false);
        repository.save(ur);
    }

    @Override
    public List<RolePermission> getRolesByPermission(UUID userId) {
        return repository.findActiveRolesByUserId(userId);
    }

    @Override
    public List<RolePermission> getPermissionsByRole(UUID roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public Page<RolePermission> searchByUserId(UUID userId, Pageable pageable) {
        return repository.findByIsActive(true, pageable); // có thể tinh chỉnh query
    }

    @Override
    public RolePermission lock(UUID id) {
        RolePermission ur = getById(id);
        ur.setIsActive(false);
        ur.setUpdatedAt(LocalDateTime.now());
        return repository.save(ur);
    }

    @Override
    public RolePermission unlock(UUID id) {
        RolePermission ur = getById(id);
        ur.setIsActive(true);
        ur.setUpdatedAt(LocalDateTime.now());
        return repository.save(ur);
    }
}