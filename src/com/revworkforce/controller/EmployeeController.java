package com.revworkforce.controller;

import com.revworkforce.model.*;
import com.revworkforce.service.IEmployeeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeController {

    private final IEmployeeService employeeService;
    private final Scanner scanner;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
        this.scanner = new Scanner(System.in);
    }

    public void start(Users user) {

        Employee emp = employeeService.getMyProfile(user.getUserId());

        if (emp == null) {
            System.out.println("❌ Employee profile not found.");
            return;
        }

        while (true) {
            printMenu();

            System.out.print("👉 Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewProfile(emp);
                case 2 -> applyLeave(emp);
                case 3 -> viewMyLeaves(emp);
                case 4 -> viewNotifications(user);
                case 0 -> {
                    System.out.println("\n🔒 Logging out...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    /* ================= MENU ================= */

    private void printMenu() {
        System.out.println("\n====================================");
        System.out.println("          EMPLOYEE MENU              ");
        System.out.println("====================================");
        System.out.println("1️⃣  View My Profile");
        System.out.println("2️⃣  Apply Leave");
        System.out.println("3️⃣  View My Leaves");
        System.out.println("4️⃣  View Notifications");
        System.out.println("0️⃣  Logout");
        System.out.println("====================================");
    }

    /* ================= FEATURES ================= */

    private void viewProfile(Employee emp) {
        System.out.println("\n--- My Profile ---");
        printEmployee(emp);
    }

    private void applyLeave(Employee emp) {

        System.out.println("\n--- Apply Leave ---");

        LeaveApplication leave = new LeaveApplication();
        leave.setEmpId(emp.getEmpId());

        System.out.print("🗂 Leave Type ID     : ");
        leave.setLeaveTypeId(scanner.nextInt());
        scanner.nextLine();

        System.out.print("📅 Start Date (yyyy-mm-dd): ");
        leave.setStartDate(LocalDate.parse(scanner.nextLine()));

        System.out.print("📅 End Date   (yyyy-mm-dd): ");
        leave.setEndDate(LocalDate.parse(scanner.nextLine()));

        System.out.print("📝 Reason            : ");
        leave.setReason(scanner.nextLine());

        try {
            employeeService.applyLeave(leave);
            System.out.println("✅ Leave applied successfully.");
        } catch (RuntimeException e) {
            System.out.println("❌ Failed to apply leave: " + e.getMessage());
        }
    }

    private void viewMyLeaves(Employee emp) {

        System.out.println("\n--- My Leave Applications ---");

        List<LeaveApplication> leaves =
                employeeService.getMyLeaves(emp.getEmpId());

        if (leaves.isEmpty()) {
            System.out.println("No leave applications found.");
            return;
        }

        for (LeaveApplication l : leaves) {
            printLeave(l);
        }
    }

    private void viewNotifications(Users user) {

        System.out.println("\n--- Notifications ---");

        List<Notifications> notifications =
                employeeService.getMyNotifications(user.getUserId());

        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (Notifications n : notifications) {
            printNotification(n);
        }
    }

    /* ================= PRINT HELPERS ================= */

    private void printEmployee(Employee e) {
        System.out.println("------------------------------------");
        System.out.println("Employee ID   : " + e.getEmpId());
        System.out.println("Name          : " + e.getFirstName() + " " + e.getLastName());
        System.out.println("Department ID : " + e.getDepartmentId());
        System.out.println("Designation   : " + e.getDesignation());
        System.out.println("Status        : " + e.getStatus());
        System.out.println("------------------------------------");
    }

    private void printLeave(LeaveApplication l) {
        System.out.println("------------------------------------");
        System.out.println("Leave ID     : " + l.getLeaveId());
        System.out.println("Type ID      : " + l.getLeaveTypeId());
        System.out.println("From         : " + l.getStartDate());
        System.out.println("To           : " + l.getEndDate());
        System.out.println("Reason       : " + l.getReason());
        System.out.println("Status       : " + formatStatus(l.getStatus()));

        if (l.getManagerComment() != null) {
            System.out.println("Manager Note : " + l.getManagerComment());
        }
        System.out.println("------------------------------------");
    }

    private void printNotification(Notifications n) {
        System.out.println("------------------------------------");
        System.out.println("📢 " + n.getMessage());
        System.out.println("Type : " + n.getType());
        System.out.println("Date : " + n.getCreatedAt());
        System.out.println("------------------------------------");
    }

    private String formatStatus(String status) {
        return switch (status) {
            case "PENDING" -> "⏳ PENDING";
            case "APPROVED" -> "✅ APPROVED";
            case "REJECTED" -> "❌ REJECTED";
            default -> status;
        };
    }
}
