package HospitalProject.Controller.Domain.HospitalServices.Admissions;

import HospitalProject.Controller.Domain.Patient.Patient;
import HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom.AdmissionRoom;
import HospitalProject.Controller.Domain.Doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionService {
    @Autowired
    private AdmissionRepository repository;

    public List<Admission> listAll() {
        return (List<Admission>) repository.findAll();
    }

    public void save(Admission admission)  {

        repository.save(admission);

        Doctor doctor = admission.getDoctor();
        Patient patient = admission.getPatient();
        AdmissionRoom admissionRoom = admission.getAdmissionRoom();

        List<Admission> doctorAdmissions = doctor.getAdmissions();
        List<Admission> patientAdmissions = patient.getAdmissions();
        List<Admission> roomAdmissions = admissionRoom.getAdmissions();

        doctorAdmissions.add(admission);
        patientAdmissions.add(admission);
        roomAdmissions.add(admission);

        doctor.setAdmissions(doctorAdmissions);
        patient.setAdmissions(patientAdmissions);
        admissionRoom.setAdmissions(roomAdmissions);


    }
    public Admission get(Integer id) throws AdmissionNotFoundException {
        Optional<Admission> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AdmissionNotFoundException("Could not find any admission with ID " + id);
    }

    public void delete(Integer id) throws AdmissionNotFoundException {
        Long count = repository.countById(id);
        if (count == null || count == 0) {
            throw new AdmissionNotFoundException("Could not find any admission with ID " + id);
        }
        repository.deleteById(id);

    }
}
