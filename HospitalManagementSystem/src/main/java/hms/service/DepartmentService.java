package hms.service;

import hms.model.Department;
import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department createDepartment(Department department);
    Department updateDepartment(Long id, Department department);
    void deleteDepartment(Long id);

    // Additional methods for searching
    List<Department> getDepartmentsByNameContaining(String name);
    List<Department> getDepartmentsByLocation(String location);
}
