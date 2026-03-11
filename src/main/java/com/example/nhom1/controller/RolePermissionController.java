package com.example.nhom1.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.nhom1.model.RolePermission;
import com.example.nhom1.service.RolePermissionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final RolePermissionService service;

    public RolePermissionController(RolePermissionService service) {
        this.service = service;
    }

    @GetMapping
    public Page<RolePermission> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created_at,desc") String sort) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]));
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public RolePermission getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public RolePermission create(@RequestBody RolePermission RolePermission) { // body chỉ cần userId, roleId
        return service.assignRole(RolePermission.getId(), RolePermission.getRoleId());
    }

    @PutMapping("/{id}")
    public RolePermission update(@PathVariable UUID id, @RequestBody RolePermission updateData) {
        return service.update(id, updateData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/roles")
    public List<RolePermission> getRolesByUser(@PathVariable UUID userId) {
        return service.getRolesByPermission(userId);
    }

    @GetMapping("/roles/{roleId}/users")
    public List<RolePermission> getUsersByRole(@PathVariable UUID roleId) {
        return service.getPermissionsByRole(roleId);
    }

    @GetMapping("/search")
    public Page<RolePermission> search(@RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.searchByUserId(userId, PageRequest.of(page, size));
    }

    @GetMapping("/isActive=true") // hỗ trợ query param isActive
    public List<RolePermission> getActive() {
        return service.getAll(PageRequest.of(0, 1000)).getContent(); // hoặc custom
    }

    @PutMapping("/{id}/lock")
    public RolePermission lock(@PathVariable UUID id) {
        return service.lock(id);
    }

    @PutMapping("/{id}/unlock")
    public RolePermission unlock(@PathVariable UUID id) {
        return service.unlock(id);
    }
}
