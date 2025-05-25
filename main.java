import java.util.*;

class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        library.run();
    }
}

class Library {
    private Map<String, Resource> catalog = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n1. Add Resource\n2. Register Member\n3. Borrow Resource\n4. Return Resource\n5. List Resources\n6. List Members\n7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addResource();
                case 2 -> registerMember();
                case 3 -> borrowResource();
                case 4 -> returnResource();
                case 5 -> listResources();
                case 6 -> listMembers();
                case 7 -> running = false;
            }
        }
    }

    private void addResource() {
        System.out.print("Enter Resource ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Type (Book/Journal/Multimedia): ");
        String type = scanner.nextLine();
        catalog.put(id, new Resource(id, title, type));
        System.out.println("Resource added.");
    }

    private void registerMember() {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        members.put(id, new Member(id, name));
        System.out.println("Member registered.");
    }

    private void borrowResource() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter Resource ID: ");
        String resourceId = scanner.nextLine();
        if (members.containsKey(memberId) && catalog.containsKey(resourceId)) {
            Resource res = catalog.get(resourceId);
            if (!res.isAvailable()) {
                System.out.println("Resource is currently borrowed.");
            } else {
                res.setBorrower(memberId);
                System.out.println("Resource borrowed successfully.");
            }
        } else {
            System.out.println("Invalid Member or Resource ID.");
        }
    }

    private void returnResource() {
        System.out.print("Enter Resource ID: ");
        String resourceId = scanner.nextLine();
        if (catalog.containsKey(resourceId)) {
            Resource res = catalog.get(resourceId);
            res.setBorrower(null);
            System.out.println("Resource returned successfully.");
        } else {
            System.out.println("Resource not found.");
        }
    }

    private void listResources() {
        for (Resource r : catalog.values()) {
            System.out.println(r);
        }
    }

    private void listMembers() {
        for (Member m : members.values()) {
            System.out.println(m);
        }
    }
}

class Resource {
    private String id;
    private String title;
    private String type;
    private String borrowerId;

    public Resource(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.borrowerId = null;
    }

    public boolean isAvailable() {
        return borrowerId == null;
    }

    public void setBorrower(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Title: %s, Type: %s, Available: %s", id, title, type, isAvailable() ? "Yes" : "No");
    }
}

class Member {
    private String id;
    private String name;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s", id, name);
    }
}