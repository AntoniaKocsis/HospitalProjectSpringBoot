package HospitalProject.Controller.Domain.HospitalConfiguration.HospitalRoom;

import javax.persistence.*;

@Entity
@Table(name = "HospitalRooms")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "room_type", discriminatorType = DiscriminatorType.STRING)
public class HospitalRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false, name = "available")
    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    private int roomNumber;

    public HospitalRoom() {
        // Default constructor for JPA
    }

    public HospitalRoom(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "HospitalRoom{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
