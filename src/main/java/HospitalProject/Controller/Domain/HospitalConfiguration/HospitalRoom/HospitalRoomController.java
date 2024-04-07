package HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom;

import HospitalProject.Controller.Domain.Factory.HospitalRoomFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HospitalRoomController {
    @Autowired private HospitalRoomService service;
    @Autowired private HospitalRoomFactory hospitalRoomFactory;

    @GetMapping("/hospitalRooms")
    public String showList(Model model) {
        List<HospitalRoom> listHospitalRooms = service.listAll();
        model.addAttribute("listHospitalRooms", listHospitalRooms);

        return "admin_view/hospitalRoom/hospitalRooms";
    }
    @GetMapping("/hospitalRooms/chooseType")
    public String showChooseTypeForm(Model model) {
        model.addAttribute("pageTitle", "Choose Room Type");
        return "admin_view/hospitalRoom/choose_type_form";
    }
    @GetMapping("/hospitalRooms/new")
    public String showNewForm(@RequestParam String roomType, Model model) {
        HospitalRoom hospitalRoom = hospitalRoomFactory.createHospitalRoom(roomType);
        model.addAttribute("roomType",roomType);
        model.addAttribute("hospitalRoom", hospitalRoom);
        model.addAttribute("pageTitle", "Add New HospitalRoom");
        return "admin_view/hospitalRoom/hospitalRoom_form";
    }

    @PostMapping("/hospitalRooms/save")
    public String saveHospitalRoom(@RequestParam String roomType,HospitalRoom hospitalRoom, RedirectAttributes ra) {
        HospitalRoom hospitalRoom1 = hospitalRoomFactory.createHospitalRoom(roomType);
        hospitalRoom1.setId(hospitalRoom.getId());
        hospitalRoom1.setRoomNumber(hospitalRoom.getRoomNumber());
        hospitalRoom1.setAvailable(hospitalRoom.isAvailable());
        service.save(hospitalRoom1);
        ra.addFlashAttribute("message", "The hospitalRoom has been saved successfully.");
        return "redirect:/hospitalRooms";
    }
    @GetMapping("/hospitalRooms/delete/{id}")
    public String deleteHospitalRoom(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The HospitalRoom ID " + id + " has been deleted.");
        } catch (HospitalRoomNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/hospitalRooms";
    }
}
