package HospitalProject.Controller.Domain.HospitalConfiguration.Department;

import HospitalProject.Controller.Domain.Doctor.Doctor;
import HospitalProject.Controller.Domain.Doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired private DepartmentService service;
    @Autowired private DoctorService doctorService;

    @GetMapping("/departments")
    public String showList(Model model) {
        List<Department> listDepartments = service.listAll();
        model.addAttribute("listDepartments", listDepartments);

        return "admin_view/department/departments";
    }

    @GetMapping("/departments/new")
    public String showNewForm(Model model) {
        List<Doctor>doctorList = doctorService.listAll();
        model.addAttribute("department", new Department());
        model.addAttribute("doctorList",doctorList);
        model.addAttribute("pageTitle", "Add New Department");
        return "admin_view/department/department_form";
    }

    @PostMapping("/departments/save")
    public String save(Department department, RedirectAttributes ra) {
        service.save(department);
        ra.addFlashAttribute("message", "The Department has been saved successfully.");
        return "redirect:/departments";
    }

    @GetMapping("/departments/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            List<Doctor>doctorList = doctorService.listAll();
            Department department = service.get(id);
            model.addAttribute("department", department);
            model.addAttribute("doctorList",doctorList);
            model.addAttribute("pageTitle", "Edit Department (ID: " + id + ")");

            return "admin_view/department/department_edit_form";
        } catch (DepartmentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/departments";
        }
    }

    @GetMapping("/departments/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The Department ID " + id + " has been deleted.");
        } catch (DepartmentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/departments";
    }
}
