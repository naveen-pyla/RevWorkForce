package com.revworkforce.dao.impl;

import com.revworkforce.dao.IDepartmentDao;
import com.revworkforce.model.Department;
import com.revworkforce.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements IDepartmentDao {

    @Override
    public boolean addDepartment(Department department) {
        String sql = "INSERT INTO department (department_name) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, department.getDepartmentName());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDepartment(Department department) {
        String sql = "UPDATE department SET department_name = ? WHERE department_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, department.getDepartmentName());
            ps.setInt(2, department.getDepartmentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        String sql = "SELECT department_id, department_name FROM department WHERE department_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, departmentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Department(
                        rs.getInt("department_id"),
                        rs.getString("department_name")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT department_id, department_name FROM department";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(
                        new Department(
                                rs.getInt("department_id"),
                                rs.getString("department_name")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public boolean deleteDepartment(int departmentId) {
        String sql = "DELETE FROM department WHERE department_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, departmentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
