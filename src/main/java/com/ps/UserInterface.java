package com.ps;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private SalesContractDAO salesContractDAO;
    private LeaseContractsDAO leaseContractsDAO;
    private VehicleDAO vehicleDAO;
    private DealershipDAO dealershipDAO;
    private Scanner scanner = new Scanner(System.in);

    private  void init() {
        dealershipDAO = new DealershipDAO();
        vehicleDAO = new VehicleDAO();
        salesContractDAO = new SalesContractDAO();
        leaseContractsDAO = new LeaseContractsDAO();

        try {
            this.dealership = dealershipDAO.getDealership();
            if (this.dealership == null) {
                System.out.println("No dealership found in database.");
                Dealership defaultDealership = new Dealership("123 Main St", "555-123-4567");
                dealershipDAO.saveDealership(defaultDealership);
                this.dealership = dealershipDAO.getDealership();

                if (this.dealership == null) {
                    System.out.println("Error: failed to load or create a default dealership.");
                    System.out.println("");
                    System.exit(1);
                } else {
                    System.out.println("Default dealership created and loaded!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public UserInterface() {
        init();
    }

    public void display() {
        System.out.println("---Welcome---\n");
        System.out.println(" " +
                "              #####                   \n" +
                "        ########  ######              \n" +
                "     ### ####  # # ########           \n" +
                "    ##  ##     # #    ########        \n" +
                "    ###  ####### #########    ####    \n" +
                "   ##                          ####   \n" +
                "   #   ######           ######   ##   \n" +
                "   ## ########         ########  ##   \n" +
                "    ##############################    \n" +
                "       ######           ######        \n");
        System.out.println("Press Enter to begin.");

        int mainMenuCommand;
        scanner.nextLine();

        do {

            System.out.println("---Main Menu---\n");
            System.out.println("1. Get by Price");
            System.out.println("2. Get by Make/Model");
            System.out.println("3. Get by Year");
            System.out.println("4. Get by Color");
            System.out.println("5. Get by Mileage");
            System.out.println("6. Get by Type");
            System.out.println("7. Get All");
            System.out.println("8. Add Vehicle");
            System.out.println("9. Remove Vehicle");
            System.out.println("10. Sell or Lease a Vehicle");
            System.out.println("0. Exit");

            System.out.println("Command: ");
            mainMenuCommand = scanner.nextInt();

            switch (mainMenuCommand) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10: processSellOrLeaseVehicle();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Command not found. Try again.");
            }

        } while (mainMenuCommand != 0);

        try {
            DealershipDAO.shutdown();
        } catch (SQLException e) {
            System.out.println("Error shutting down database: " + e.getMessage());
        }

    }

    private void processSellOrLeaseVehicle() {
        System.out.println("---Sell or Lease Vehicle---");

        System.out.println("Enter the vehicles VIN: ");
        String vin = scanner.next();

        Vehicle vehicle = dealership.findVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.println("Customer name: ");
        String customerName = scanner.nextLine();

        System.out.println("Customer email: ");
        String customerEmail = scanner.nextLine();

        System.out.println("would you like to (1) Sale or (2) Lease? ");
        int choice = scanner.nextInt();

        if (scanner.hasNext()) {
            choice = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Invalid input, try again.");
            scanner.nextLine();
            return;
        }

        Contract contract;
        String date = LocalDate.now().toString();

        //Finance
        if (choice == 1) {
            //this is sales
            System.out.println("Do you want to finance? (Yes or No): ");
            scanner.nextLine();
            String finance = scanner.nextLine();
            boolean wantsFinance = finance.equalsIgnoreCase("Yes");
            contract = new SalesContract(date, customerName, customerEmail, vehicle, wantsFinance);
            salesContractDAO.saveSalesContract((SalesContract) contract);


            if (wantsFinance) {
                System.out.println("Total Price: $%.2f\n" + contract.getTotalPrice());
                System.out.println("Monthly Payment: $%2.f\n" + contract.getMonthlyPayment());
            } else {
                System.out.println("Total Price: $%.2f\n" + contract.getTotalPrice());
            }
        } else if (choice == 2) {
            //lease
            int vehicleAge = LocalDate.now().getYear() - vehicle.getYear();
            if (vehicleAge > 3) {
                System.out.println("Cant lease a vehicle older than 3 years.");
                return;
            }

            contract = new LeaseContract(date, customerName, customerEmail, vehicle);
            leaseContractsDAO.saveLeaseContract((LeaseContract) contract);

            System.out.println("Total Lease: $%2.f\n" + contract.getTotalPrice());
            System.out.println("Monthly Payment: $%2.f\n" + contract.getMonthlyPayment());

        } else {
            System.out.println("Invaild option.");
        }
    }

    private void processGetByPriceRequest() {
        System.out.println("---Display vehicle by price---");

        System.out.println("Enter Price Min: ");
        double min = scanner.nextDouble();

        System.out.println("Enter Price Max: ");
        double max = scanner.nextDouble();
        scanner.nextLine();

        List<Vehicle> filteredVehicles = dealership.vehiclesByPrice(min, max);
        displayVehicles(filteredVehicles);

    }

    private void processGetByMakeModelRequest() {
        System.out.println("---Display vehicles by make and model---");
        scanner.nextLine();

        System.out.println("Enter Make: ");
        String make = scanner.nextLine();

        System.out.println("Enter Model: ");
        String model = scanner.nextLine();

        List<Vehicle> filteredVehicles = dealership.vehiclesByMakeModel(make, model);
        displayVehicles(filteredVehicles);
    }
    private void processGetByYearRequest() {
        System.out.println("---Display Vehicles by year---");

        System.out.println("Enter Year Min: ");
        int min = scanner.nextInt();
        System.out.println("Enter Year Max: ");
        int max = scanner.nextInt();
        scanner.nextLine();

        List<Vehicle> filteredVehicles = dealership.vehiclesByYear(min, max);
        displayVehicles(filteredVehicles);

    }
    private void processGetByColorRequest() {
        System.out.println("---Display vehicles by color---");
        scanner.nextLine();

        System.out.println("Enter Color: ");
        String color = scanner.nextLine();


        List<Vehicle> filteredVehicles = dealership.vehiclesByColor(color);
        displayVehicles(filteredVehicles);

    }
    private void processGetByMileageRequest() {
        System.out.println("---Display vehicles by mileage---");

        System.out.println("Enter Mileage Min: ");
        int min = scanner.nextInt();
        System.out.println("Enter Mileage Max: ");
        int max = scanner.nextInt();
        scanner.nextLine();

        List<Vehicle> filteredVehicles = dealership.vehiclesByMileage(min, max);
        displayVehicles(filteredVehicles);
    }
    private void processGetByVehicleTypeRequest() {
        System.out.println("---Display vehicles by type---");
        scanner.nextLine();

        System.out.println("Enter Type (Ex: car, Truck, SUV, etc...): ");
        String type = scanner.nextLine();


        List<Vehicle> filteredVehicles = dealership.vehiclesByType(type);
        displayVehicles(filteredVehicles);

    }
    private void processGetAllVehiclesRequest() {
        System.out.println("---Display All Vehicles---");
        List<Vehicle> all = dealership.getAllVehicles();
        displayVehicles(all);


    }
    private void processAddVehicleRequest() {
        System.out.println("---Add Vehicles");

        System.out.println("Enter Vin: ");
        String vin = scanner.nextLine();

        System.out.println("Enter Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Make: ");
        String make = scanner.nextLine();

        System.out.println("Enter Model: ");
        String model = scanner.nextLine();

        System.out.println("Enter Type: ");
        String type = scanner.nextLine();

        System.out.println("Enter Color: ");
        String color = scanner.nextLine();

        System.out.println("Enter Odometer: ");
        int odometer = scanner.nextInt();

        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, make, model, year, color, odometer, price, type);
        dealership.addVehicle(vehicle);

        System.out.println("Vehicle added successfully!");


    }
    private void processRemoveVehicleRequest() {
        System.out.println("---Remove Vehicle---\n");

        System.out.println("Enter VIN of the vehicle to remove: ");
        String vin = scanner.nextLine();

        dealership.removeVehicle(vin);

        System.out.println("Vehicle Removed successfully!");


    }
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

}