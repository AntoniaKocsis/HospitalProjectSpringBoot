package HospitalProject.Controller.Domain.Doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired private DoctorRepository doctorRepository;
    public List<Doctor> listAll() {
        return (List<Doctor>) doctorRepository.findAll();
    }

    public List<Doctor>listOnCall(){
        List<Doctor> doctors = new ArrayList<>();
        for(Doctor doctor:doctorRepository.findAll()){
            if(doctor.isOnCall()){
                doctors.add(doctor);
            }
        }
        return doctors;
    }
    public List<Doctor>listOffCall(){
        List<Doctor> doctors = new ArrayList<>();
        for(Doctor doctor:doctorRepository.findAll()){
            if(!doctor.isOnCall()){
                doctors.add(doctor);
            }
        }
        return doctors;
    }


    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public Doctor get(Integer id) throws DoctorNotFoundException {
        Optional<Doctor> result = doctorRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new DoctorNotFoundException("Could not find any doctors with ID " + id);
    }

    public void delete(Integer id) throws DoctorNotFoundException {
        Long count = doctorRepository.countById(id);
        if (count == null || count == 0) {
            throw new DoctorNotFoundException("Could not find any doctors with ID " + id);
        }
        doctorRepository.deleteById(id);

    }
}

