package hms.service.impl;

import hms.exception.ResourceNotFoundException;
import hms.model.Department;
import hms.repository.DepartmentRepository;
import hms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
    }

    @Override
    public Department createDepartment(Department department) {
        // Check if department with same name already exists (case-insensitive)
        if (department.getName() != null && departmentRepository.existsByNameIgnoreCase(department.getName())) {
            throw new RuntimeException("Department with name '" + department.getName() + "' already exists");
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));

        // Check if another department with the same name already exists (case-insensitive)
        if (department.getName() != null) {
            Optional<Department> existingDepartmentWithName = departmentRepository.findByNameIgnoreCase(department.getName());
            if (existingDepartmentWithName.isPresent() && !existingDepartmentWithName.get().getId().equals(id)) {
                throw new RuntimeException("Department with name '" + department.getName() + "' already exists");
            }
        }

        existingDepartment.setName(department.getName());
        existingDepartment.setDescription(department.getDescription());
        existingDepartment.setLocation(department.getLocation());
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department", "id", id);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Department> getDepartmentsByNameContaining(String name) {
        return departmentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Department> getDepartmentsByLocation(String location) {
        return departmentRepository.findByLocation(location);
    }
}
