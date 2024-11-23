import java.util.ArrayList;
import java.util.Scanner;

class Order {
    private int id;
    private String customerName;
    private String address;
    private String status; // "Đang xử lý", "Đang giao", "Hoàn thành"

    public Order(int id, String customerName, String address, String status) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Tên KH: " + customerName +
                ", Địa chỉ: " + address +
                ", Trạng thái: " + status;
    }
}

public class Main {
    private static final ArrayList<Order> orders = new ArrayList<>();
    private static int nextOrderId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== HỆ THỐNG ĐẶT HÀNG TRỰC TUYẾN =====");
            System.out.println("1. Tạo đơn hàng mới");
            System.out.println("2. Hiển thị danh sách đơn hàng");
            System.out.println("3. Cập nhật trạng thái đơn hàng");
            System.out.println("4. Xóa đơn hàng theo ID");
            System.out.println("5. Tìm kiếm đơn hàng theo ID");
            System.out.println("6. Tìm kiếm đơn hàng theo tên khách hàng");
            System.out.println("7. Thống kê đơn hàng theo trạng thái");
            System.out.println("8. Hiển thị đơn hàng theo trạng thái");
            System.out.println("9. Xóa toàn bộ đơn hàng");
            System.out.println("10. Thoát");
            System.out.print("Nhập lựa chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Loại bỏ ký tự xuống dòng

            switch (choice) {
                case 1 -> addOrder(scanner);
                case 2 -> displayOrders();
                case 3 -> updateOrderStatus(scanner);
                case 4 -> deleteOrder(scanner);
                case 5 -> searchOrderById(scanner);
                case 6 -> searchOrderByCustomerName(scanner);
                case 7 -> countOrdersByStatus();
                case 8 -> displayOrdersByStatus(scanner);
                case 9 -> clearAllOrders();
                case 10 -> System.out.println("Cảm ơn đã sử dụng hệ thống!");
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
            }
        } while (choice != 10);
    }

    private static void addOrder(Scanner scanner) {
        System.out.println("\n--- Tạo Đơn Hàng Mới ---");
        System.out.print("Nhập tên khách hàng: ");
        String customerName = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        String address = scanner.nextLine();
        String status = "Đang xử lý";
        orders.add(new Order(nextOrderId++, customerName, address, status));
        System.out.println("Đơn hàng đã được tạo thành công!");
    }

    private static void displayOrders() {
        System.out.println("\n--- Danh Sách Đơn Hàng ---");
        if (orders.isEmpty()) {
            System.out.println("Chưa có đơn hàng nào.");
        } else {
            orders.forEach(System.out::println);
        }
    }

    private static void updateOrderStatus(Scanner scanner) {
        System.out.print("\nNhập ID đơn hàng cần cập nhật: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Order order : orders) {
            if (order.getId() == id) {
                System.out.println("Trạng thái hiện tại: " + order.getStatus());
                System.out.println("1. Đang xử lý");
                System.out.println("2. Đang giao");
                System.out.println("3. Hoàn thành");
                System.out.print("Chọn trạng thái mới: ");
                int statusChoice = scanner.nextInt();
                scanner.nextLine();
                switch (statusChoice) {
                    case 1 -> order.setStatus("Đang xử lý");
                    case 2 -> order.setStatus("Đang giao");
                    case 3 -> order.setStatus("Hoàn thành");
                    default -> System.out.println("Lựa chọn không hợp lệ.");
                }
                System.out.println("Cập nhật trạng thái thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy đơn hàng với ID đã nhập.");
    }

    private static void deleteOrder(Scanner scanner) {
        System.out.print("\nNhập ID đơn hàng cần xóa: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (orders.removeIf(order -> order.getId() == id)) {
            System.out.println("Xóa đơn hàng thành công!");
        } else {
            System.out.println("Không tìm thấy đơn hàng với ID đã nhập.");
        }
    }

    private static void searchOrderById(Scanner scanner) {
        System.out.print("\nNhập ID đơn hàng cần tìm: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Order order : orders) {
            if (order.getId() == id) {
                System.out.println(order);
                return;
            }
        }
        System.out.println("Không tìm thấy đơn hàng với ID đã nhập.");
    }

    private static void searchOrderByCustomerName(Scanner scanner) {
        System.out.print("\nNhập tên khách hàng: ");
        String name = scanner.nextLine();
        boolean found = false;
        for (Order order : orders) {
            if (order.getCustomerName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(order);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy đơn hàng nào với tên khách hàng đã nhập.");
        }
    }

    private static void countOrdersByStatus() {
        int processing = 0, delivering = 0, completed = 0;
        for (Order order : orders) {
            switch (order.getStatus()) {
                case "Đang xử lý" -> processing++;
                case "Đang giao" -> delivering++;
                case "Hoàn thành" -> completed++;
            }
        }
        System.out.println("\n--- Thống Kê Đơn Hàng ---");
        System.out.println("Đang xử lý: " + processing);
        System.out.println("Đang giao: " + delivering);
        System.out.println("Hoàn thành: " + completed);
    }

    private static void displayOrdersByStatus(Scanner scanner) {
        System.out.print("\nNhập trạng thái cần hiển thị: ");
        String status = scanner.nextLine();
        boolean found = false;
        for (Order order : orders) {
            if (order.getStatus().equalsIgnoreCase(status)) {
                System.out.println(order);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy đơn hàng với trạng thái \"" + status + "\".");
        }
    }

    private static void clearAllOrders() {
        orders.clear();
        System.out.println("\nTất cả đơn hàng đã được xóa.");
    }
}
