import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// Abstract class for Room
abstract class Room {
    private int roomNumber;
    private boolean isBooked;
    private boolean hasRoomService;
    private boolean hasBreakfast;
    private boolean hasWifi;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        this.isBooked = true;
    }

    public void vacateRoom() {
        this.isBooked = false;
        this.hasRoomService = false;
        this.hasBreakfast = false;
        this.hasWifi = false;
    }

    public void addServices(boolean roomService, boolean breakfast, boolean wifi) {
        this.hasRoomService = roomService;
        this.hasBreakfast = breakfast;
        this.hasWifi = wifi;
    }

    public double getServiceCharges() {
        double total = 0;
        if (hasRoomService) total += 500;
        if (hasBreakfast) total += 300;
        if (hasWifi) total += 100;
        return total;
    }

    public abstract double calculateCharges(int nights);
}

// Standard Room
class StandardRoom extends Room {
    public StandardRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculateCharges(int nights) {
        return (nights * 1000) + getServiceCharges();
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculateCharges(int nights) {
        return (nights * 2000) + getServiceCharges();
    }    
}

// VIP Room
class VIPRoom extends Room {
    public VIPRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculateCharges(int nights) {
        return (nights * 3500) + getServiceCharges();
    }    
}

// Hotel management system
class Hotel {
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void showAvailableRooms() {
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println("Room " + room.getRoomNumber() + " is available.");
            }
        }
    }
    public void bookRoom(int roomNumber, int nights) {
        Scanner sc = new Scanner(System.in);
    
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && !room.isBooked()) {
                System.out.println("Optional Services (₹ per stay):");
                System.out.print("Add Room Service (₹500)? (yes/no): ");
                boolean rs = sc.next().equalsIgnoreCase("yes");
    
                System.out.print("Add Breakfast (₹300)? (yes/no): ");
                boolean bf = sc.next().equalsIgnoreCase("yes");
    
                System.out.print("Add Wi-Fi (₹100)? (yes/no): ");
                boolean wifi = sc.next().equalsIgnoreCase("yes");
    
                room.addServices(rs, bf, wifi);
                room.bookRoom();
                double total = room.calculateCharges(nights);
    
                System.out.println("\n✅ Room " + roomNumber + " booked for " + nights + " nights.");
                System.out.println("Total charge (with services): ₹" + total);
                return;
            }
        }
        System.out.println("❌ Room not available or already booked.");
    }
    

    public void vacateRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isBooked()) {
                room.vacateRoom();
                System.out.println("Room " + roomNumber + " has been vacated.");
                return;
            }
        }
        System.out.println("Room not found or not booked.");
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        // Adding rooms to the hotel
        hotel.addRoom(new StandardRoom(101));
        hotel.addRoom(new SuiteRoom(102));
        hotel.addRoom(new VIPRoom(103));
        hotel.addRoom(new StandardRoom(104));
        hotel.addRoom(new VIPRoom(105));

        int choice;

        System.out.println();
        System.out.println("🛎️ Welcome to Elite Hotel Reservation System");

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Vacate a Room");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;

                case 2:
                    System.out.print("Enter Room Number to Book: ");
                    int roomNumToBook = scanner.nextInt();
                    System.out.print("Enter number of nights: ");
                    int nights = scanner.nextInt();
                    hotel.bookRoom(roomNumToBook, nights);
                    break;

                case 3:
                    System.out.print("Enter Room Number to Vacate: ");
                    int roomNumToVacate = scanner.nextInt();
                    hotel.vacateRoom(roomNumToVacate);
                    break;

                case 4:
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 4);

        scanner.close();
    }
}
