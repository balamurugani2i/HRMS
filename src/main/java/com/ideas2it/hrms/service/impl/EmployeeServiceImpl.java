package com.ideas2it.hrms.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.hrms.dao.EmployeeDao;
import com.ideas2it.hrms.dao.impl.EmployeeDaoImpl;
import com.ideas2it.hrms.exception.AppException;
import com.ideas2it.hrms.model.Attendance;
import com.ideas2it.hrms.model.Designation;
import com.ideas2it.hrms.model.Employee;
import com.ideas2it.hrms.model.Project;
import com.ideas2it.hrms.model.ProjectTask;
import com.ideas2it.hrms.service.DesignationService;
import com.ideas2it.hrms.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    
    /** {@inheritDoc}*/
    public Boolean createEmployee(Employee employee) throws AppException {
        return employeeDao.createEmployee(employee);

    }
    
    public List<Attendance> markPresent(Employee employee) throws AppException {
        AttendanceServiceImpl attendService = new AttendanceServiceImpl();
        return attendService.markPresent(employee);
    }
    
    public List<Attendance> markAbsent(Employee employee) throws AppException {
        AttendanceServiceImpl attendService = new AttendanceServiceImpl();
        return attendService.markAbsent(employee);
    }
    
    /** {@inheritDoc}*/
    public Boolean updateEmployee(Employee employee) throws AppException {
        return employeeDao.updateEmployee(employee);

    }
    
    /** {@inheritDoc}*/
    public List<Employee> displayEmployees() throws AppException {
        return employeeDao.retrieveEmployees();
    }
    
    /** {@inheritDoc}*/
    public Boolean deleteEmployee(Integer id) throws AppException {
        return employeeDao.deleteEmployee(id);
    }
    
    /** {@inheritDoc}*/
    public Boolean isEmployeeExist(String emailId) throws AppException {
        return (null == employeeDao.searchEmployee(emailId));
    }
    
    /** {@inheritDoc}*/
    public Employee searchEmployee(String emailId) throws AppException {
        return employeeDao.searchEmployee(emailId);
    }
    
    /** {@inheritDoc}*/
    public List<Designation> getDesignations() throws AppException {
        DesignationService designationService = new DesignationServiceImpl();
        return designationService.displayDesignations();
    }
    
    /** {@inheritDoc}*/
    public List<Project> getEmpProjects(List<ProjectTask> tasks) {
        List<Project> projects = new ArrayList<Project>();
        for(ProjectTask task:tasks) {
            if(! projects.contains(task.getProject())) {
                projects.add(task.getProject());
            }
        }
        return projects;
    }
    
    public Integer calculateNetProfit(Employee employee) {
            Integer billableAmount = 0;
            Integer costToCompany = 0;
            Integer netProfit = 0;
            
            billableAmount = calculateBillableAmount(employee);            
            costToCompany = calculateCostToCompany(employee);            
            netProfit = billableAmount - costToCompany;

            return netProfit;
    }
    
    public Integer calculateBillableAmount(Employee employee) {
        ProjectTaskServiceImpl taskService = new ProjectTaskServiceImpl();
        List<ProjectTask> empTasks = employee.getProjectTasks();
        List<ProjectTask> curMonthTasks = new ArrayList<ProjectTask>();
        Integer empHourlyRate = employee.getDesignation().getHourlyRate();
        Integer curMonthHoursWorked = 0;
        Integer billableAmount = 0;
        
        curMonthTasks = taskService.getCurrentMonthTasks(empTasks);
        curMonthHoursWorked = calculateHoursWorkedEmp(curMonthTasks);
        billableAmount = curMonthHoursWorked * empHourlyRate;
        
        return billableAmount;        
    }
    
    public Integer calculateHoursWorkedEmp(List<ProjectTask> curMonthTasks) {
        ProjectTaskServiceImpl taskService = new ProjectTaskServiceImpl();
        Integer numHoursWorkedTask = 0;
        Integer totalHoursWorked = 0;
            
        for (ProjectTask task : curMonthTasks) {           
            numHoursWorkedTask = taskService.calculateTaskDuration(task);
            totalHoursWorked = totalHoursWorked + numHoursWorkedTask;
        }
        
        return totalHoursWorked;
    }
        
    public Integer calculateCostToCompany(Employee employee) {
        Integer costToCompany = 0;
        Integer monthlySalary = employee.getDesignation().getSalary();
        Integer dailySalary = monthlySalary / 30; 
        Integer numLeaveDays = 0;
        
        numLeaveDays = getNumLeaves(employee);
        costToCompany = monthlySalary - (numLeaveDays * dailySalary);

        return costToCompany;
    }

    public Integer getNumLeaves(Employee employee) {
        Integer numLeaves = 0;

        for (Attendance attendance : employee.getAttendance()) {
            if (isEmpLeave(attendance)) {
                numLeaves ++ ;
            }
        }
        
        return numLeaves;
    }

    public boolean isEmpLeave(Attendance attendance) {
        LocalDate today = LocalDate.now();
        LocalDate attendDate = attendance.getAttendDate();
        boolean isAbsent = false;

        if (attendDate.getMonth() == today.getMonth() &&
                attendDate.getYear() == today.getYear()
                    && !attendance.getStatus()) {
            isAbsent = true;
        }

        return isAbsent;
    }
}