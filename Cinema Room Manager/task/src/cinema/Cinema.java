package cinema;

import java.util.Scanner;

public class Cinema {

    private static final Scanner scanner = new Scanner(System.in);

    private int rows;
    private int seatsPerRow;
    private boolean[][] seating;
    private int ticketsPurchased;
    private int currentIncome;

    public Cinema(int rows, int columns){
        this.rows = rows;
        this.seatsPerRow = columns;
        this.seating = new boolean[columns][rows];
        this.ticketsPurchased = 0;
        this.currentIncome = 0;
    }

    private void showMenu(){
        System.out.printf(
                "1. Show the seats \n" +
                "2. Buy a ticket \n" +
                "3. Statistics \n" +
                "0. Exit\n"
        );
    }

    //When the item Statistics is chosen, your program should print the following information:
    //
    //The number of purchased tickets;
    //The number of purchased tickets represented as a percentage. Percentages should be rounded to 2 decimal places;
    //Current income;
    //The total income that shows how much money the theatre will get if all the tickets are sold.
    private void showStatistics(){
        System.out.printf(
                "Number of purchased tickets: %d%nPercentage: %.2f%%%nCurrent income: $%d%nTotal income: $%d%n",
                this.ticketsPurchased,
                ((double)(this.ticketsPurchased / ((double)this.seatsPerRow * (double)this.rows) * 100.0)),
                this.currentIncome,
                getTotalIncome()
        );
    }

    private int getMenuOption(){
        int option = -1;
        while(option < 0){
            this.showMenu();
            option = Integer.parseInt(scanner.nextLine());
            if(option > 3){
                option = -1;
            }
        }
        return option;
    }

    private void run(){
        int menuOption = -1; // initial state to avoid ending program
        while(menuOption != 0){
            switch (getMenuOption()){
                case 0:
                    return;
                case 1:
                    showCustomerSeatLocations();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    showStatistics();
                    break;
                default:
                    break;
            }
        }
    }

    private void buyTicket() {
        int colPos = 0, rowPos = 0;
        boolean seatTaken = true;
        while(seatTaken){
            System.out.println("Enter a row number:");
            rowPos = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter a seat number in that row:");
            colPos = Integer.parseInt(scanner.nextLine());

            if(colPos < 0 || colPos > this.seatsPerRow || rowPos < 0 || rowPos > this.rows){
                System.out.println("Wrong input!");
                continue;
            }

            seatTaken = this.seating[colPos-1][rowPos-1];

            if(seatTaken){
                System.out.println("That ticket has already been purchased!");
                continue;
            }
        }

        this.seating[colPos-1][rowPos-1] = true;
        this.showCustomerSeatLocations();
        int cost = this.calculateTicketCost(rowPos);
        System.out.println("\nTicket Price: $" + cost);
        this.currentIncome += cost;
        this.ticketsPurchased++;
    }


    public static void main(String[] args) {
        // Write your code here

        System.out.println("Enter the number of rows:");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = Integer.parseInt(scanner.nextLine());

        Cinema cinema =  new Cinema(rows, seatsPerRow);
        cinema.showCustomerSeatLocations();
        cinema.run();


    }

    private void showCustomerSeatLocations() {
        System.out.println("Cinema:");
        System.out.print("  ");
        for(int c = 1; c <= this.seatsPerRow; c++){
            System.out.print(c + " ");
        }
        System.out.println();
        for (int i = 0; i < this.rows; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < this.seatsPerRow; j++) {
                System.out.print((this.seating[j][i]) ? "B " : "S ");
            }
            System.out.println();
        }
    }
    private int getTotalIncome(){
        if(this.rows * this.seatsPerRow <= 60){
            return 10 * this.seatsPerRow * this.rows;
        }else {
            return (int) (Math.floor((double) this.rows / 2) * 10 * this.seatsPerRow + Math.ceil((double) this.rows / 2) * 8 * this.seatsPerRow);
        }
    }
    private int calculateTicketCost(int rowPos){
        if(this.rows * this.seatsPerRow <= 60){
            return 10;
        }else if(this.rows * this.seatsPerRow > 60 & rowPos <= Math.floor((double) this.rows / 2)){
            return 10;
        }else{
            return 8;
        }
    }
}