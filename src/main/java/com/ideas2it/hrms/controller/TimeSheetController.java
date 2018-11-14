package com.ideas2it.hrms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideas2it.hrms.exception.AppException;
import com.ideas2it.hrms.model.Employee;
import com.ideas2it.hrms.model.Project;
import com.ideas2it.hrms.model.TimeSheet;
import com.ideas2it.hrms.service.ProjectService;
import com.ideas2it.hrms.service.TimeSheetService;
import com.ideas2it.hrms.service.impl.ProjectServiceImpl;
import com.ideas2it.hrms.service.impl.TimeSheetServiceImpl;

import static com.ideas2it.hrms.common.ProjectConstants.MSG_CREATED;
import static com.ideas2it.hrms.common.ProjectConstants.MSG_DELETED;
import static com.ideas2it.hrms.common.ProjectConstants.MSG_UPDATED;

/**
 * Provides functionality to manage tasks of projects
 * 
 * @author Ganesh Venkat S
 */
@Controller 
public class TimeSheetController {            
    @PostMapping("projectTask/create")
    public ModelAndView createTask(@ModelAttribute("task") TimeSheet task, 
            HttpServletRequest request) {
        TimeSheetService taskService = new TimeSheetServiceImpl();
        ModelAndView modelAndView = new ModelAndView(); 

        try {
            // Check if duplicate task exists including deleted tasks
            Project project = new Project();
            project.setId(Integer.parseInt(request.getParameter("projectId")));
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(request.getParameter("empId")));
            task.setProject(project);
            task.setEmployee(employee);
            task = taskService.createTask(task);   
            modelAndView.addObject("Success", MSG_CREATED);
            // redirect him to the same page; the tasks must also be sent
            // alert box is optional for now
            modelAndView.setViewName("tasks");
        } catch (AppException appException) {
            modelAndView.addObject("Error", appException.getMessage());
        }
       return modelAndView;
    }
    
    @PostMapping("projectTask/update")
    public ModelAndView updateTask(@ModelAttribute("task") TimeSheet task, HttpServletRequest request) {
        TimeSheetService sheetService = new TimeSheetServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();
        ModelAndView modelAndView = new ModelAndView(); 

        try {
            List<TimeSheet> timeSheet = new ArrayList<TimeSheet>();
            // Check if the task to be updated exists 
            HttpSession session = request.getSession();
            Employee currentEmployee = (Employee) session.getAttribute("employee");
            Integer projectId = Integer.parseInt(request.getParameter("projectId"));
            String projectName = request.getParameter("projectName");
            
            
            Project project = projectService.getProjectById(projectId);
            //System.out.println(task.getProject().getName());
            task.setProject(project);
            task.setEmployee(currentEmployee);
            task.getProject().setName(projectName);
            task = sheetService.updateTask(task);   
            // Get this employee's tasks
            timeSheet = sheetService.getAllTasks();

            modelAndView.addObject("Success", MSG_UPDATED);
            modelAndView.addObject("timeSheets", timeSheet);
            modelAndView.setViewName("employeeView");
        } catch (AppException appException) {
            modelAndView.addObject("Error", appException.getMessage());  
        }
        return modelAndView;
    }
        
    @PostMapping("projectTask/delete")
    public ModelAndView deleteTask(HttpServletRequest request) {
        TimeSheetService sheetService = new TimeSheetServiceImpl();
        ModelAndView modelAndView = new ModelAndView(); 
        List<TimeSheet> timeSheet = new ArrayList<TimeSheet>();

        try {
            // Check if the task to be deleted exists - rare case, db inconsistent with application
            Integer id = Integer.parseInt(request.getParameter("id"));
            TimeSheet task = sheetService.getTaskById(id);
            
            if (null != task) {
                task = sheetService.removeTask(task);    
                timeSheet = sheetService.getAllTasks();
            }            
            modelAndView.addObject("Success", MSG_DELETED);
            modelAndView.addObject("timeSheets", timeSheet);
            modelAndView.setViewName("employeeView");
        } catch (AppException appException) {
            modelAndView.addObject("Error", appException.getMessage());  
        }
        return modelAndView;
    }
    
    // Check if this method is redundant
    @GetMapping("displayAll")
    public ModelAndView displayAllTasks(HttpServletRequest request) {
        TimeSheetService taskService = new TimeSheetServiceImpl();
        ModelAndView modelAndView = new ModelAndView(); 

        try {
            List<TimeSheet> allTasks = taskService.getAllTasks();
            modelAndView.addObject("allTasks", allTasks);
            // redirect him to the same page; the tasks must also be sent
            // alert box is optional for now
            modelAndView.setViewName("tasks");
        } catch (AppException appException) {
            modelAndView.addObject("Error", appException.getMessage());
        }
        return modelAndView;
    }
}