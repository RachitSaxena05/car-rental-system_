import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;
class Car{
    private String carID;
    private String brand;
    private String Model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carID, String brand, String Model, double basePricePerDay){
        this.carID=carID;
        this.brand=brand;
        this.Model=Model;

        this.basePricePerDay=basePricePerDay;
        this.isAvailable=true;
    }
    public String getcarID(){
        return carID;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return Model;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable=false;
    }
    public void returnCar(){
        isAvailable=true;
    }
}
class Customer{
    private String CustomerID;
    private String name;
    public Customer(String CustomerID,String name){
        this.CustomerID=CustomerID;
        this.name=name;
    }
    public String getCustomerID(){
        return CustomerID;
    }
    public String getname(){
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;
    public Rental(Car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }
    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getdays(){
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public void rentCar(Car car,Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else{
            System.out.println("CAR IS NOT AVAILABLE FOR RENT");
        }
    }
    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove=null;
        for(Rental rental:rentals){
            if(rental.getCar()==car){
                rentalToRemove=rental;
                break;
            }
        }
        if(rentalToRemove!=null){
            rentals.remove(rentalToRemove);
        }
        else{
            System.out.println("CAR WAS NOT RENTED");
        }
    }
    public void menu(){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("===== CAR RENTAL SYSTEM =====");
            System.out.println("1. rent a car");
            System.out.println("2. return a car");
            System.out.println("3. EXIT");
            System.out.print("ENTER YOUR CHOICE");
            int choice =sc.nextInt();
            sc.nextLine();
            if(choice==1){
                System.out.println("\n==RENT A CAR==\n");
                System.out.print("enter your name:");
                String customerName=sc.nextLine();
                System.out.println("\nAVAILABLE CARS:");
                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getcarID()+" - "+ car.getBrand()+" "+ car.getModel());
                    }
                }
                System.out.println("|n ENTER THE CAR ID YOU WANT TO RENT:");
                String carID=sc.nextLine();
                System.out.println("enter number of days for rental");
                int rentalDays=sc.nextInt();
                sc.nextLine();
                Customer newCustomer= new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(newCustomer);
                Car selectedCar=null;
                for(Car car:cars){
                    if(car.getcarID().equals(carID)&&car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }
                if(selectedCar!=null){
                    double totalPrice=selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n==Rental Information==\n");
                    System.out.println("Customer ID:"+newCustomer.getCustomerID());
                    System.out.println("Customer Name:"+newCustomer.getname());
                    System.out.println("Car:"+selectedCar.getBrand()+" "+selectedCar.getModel());
                    System.out.println("Rental days="+rentalDays);
                    System.out.printf("Total price: $%.2f%n",totalPrice);
                    System.out.println("confirm rental(Y/N):");
                    String confirm=sc.nextLine();
                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\nCar rented successfully\n");
                    }
                    else{
                        System.out.println("\nRental Cancelled\n");
                    }
                }
                else{
                    System.out.println("Invalid Car Selection\n");
                }
            }
            else if(choice==2){
                System.out.println("\n===Return a Car===\n");
                System.out.println("enter car ID you want to return:");
                String carID=sc.nextLine();
                Car carToReturn=null;
                for(Car car: cars){
                    if(car.getcarID().equals(carID)&& !car.isAvailable()){
                        carToReturn=car;
                        break;
                    }
                }
                if(carToReturn!=null){
                    Customer customer=null;
                    for(Rental rental: rentals){
                        if(rental.getCar()==carToReturn){
                            customer=rental.getCustomer();
                            break;
                        }
                    }
                    if(customer!=null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by "+customer.getname());
                    }
                    else{
                        System.out.println("car was not rented or rental information missing");
                    }
                }
                else{
                    System.out.println("invalid carID or car is not rented");
                }
            }
            else if(choice==3){
                break;
            }
            else{
                System.out.println("INVALID CHOICE");
            }

        }
        System.out.println("=====Thankyou for using the Car Rental System");
    }
}
public class Main{
    public static void main(String[]Args){
        CarRentalSystem rentalsystem=new CarRentalSystem();
        Car car1=new Car("C001","TOYOTA","CAMRY",60.0);
        Car car2=new Car("C002","HONDA","ACCORD",70.0);
        Car car3=new Car("C003","MAHINDRA","THAR",150.0);
        rentalsystem.addCar(car1);
        rentalsystem.addCar(car2);
        rentalsystem.addCar(car3);
        rentalsystem.menu();
    }
}