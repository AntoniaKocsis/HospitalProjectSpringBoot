package HospitalProject.Controller.Domain.HospitalServices.LabTestResult;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.Patient.PatientNotFoundException;
import HospitalProject.Controller.Domain.Patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class LabTestResultController {
    @Autowired
    private LabTestResultService service;
    @Autowired
    private PatientService patientService;

    @GetMapping("/labTestResults")
    public String showUserList(Model model) {
        List<LabTestResult> listLabTestResults = service.listAll();
        model.addAttribute("listLabTestResults", listLabTestResults);
        return "admin_view/labTestResult/labTestResults";
    }

    @GetMapping("/labTestResults/new")
    public String showNewForm(Model model) {
        List<Patient> patients = patientService.listAll();
        model.addAttribute("patients",patients);
        model.addAttribute("pageTitle", "Add New Lab Test Result");
        return "admin_view/labTestResult/labTestResult_form";
    }

    @PostMapping("/labTestResults/save")
    public String saveUser(@RequestParam("patient") Integer patientId,
                           @RequestParam("name") String name,
                           @RequestParam("result") String result,
                           @RequestParam("date") String dateString, RedirectAttributes ra) throws PatientNotFoundException {
        Patient patient = patientService.get(patientId);
        LocalDateTime date = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        LabTestResult labTestResult = new LabTestResult.LabTestResultBuilder()
                .withPatient(patient)
                .withTestName(name)
                .withTestDate(date)
                .withResult(result)
                .build();

        service.save(labTestResult);
        ra.addFlashAttribute("message", "The labTestResult has been saved successfully.");
        return "redirect:/labTestResults";
    }


    @GetMapping("/labTestResults/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The labTestResult ID " + id + " has been deleted.");
        } catch (LabTestResultNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/labTestResults";
    }
}

