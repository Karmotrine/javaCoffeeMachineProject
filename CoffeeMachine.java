package machine;
import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String strAct = " ";
        //Action
        while (!strAct.equals("exit")) {
            System.out.println("Write action (buy, fill, take, remaining, exit:  ");
            String response = s.next().toLowerCase();
            strAct = response;
            switch (strAct) {
                case "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, " +
                                        "3 - cappuciccino, back - to main menu: ");
                    String flavorOfCoffee = s.next();
                    if (flavorOfCoffee.equals("back")) {
                        break;
                    }
                    CoffeeMaker.buy(flavorOfCoffee);
                    break;
                case "fill":
                    CoffeeMaker.fill();
                    break;
                case "take":
                    CoffeeMaker.take();
                    break;
                case "remaining":
                    CoffeeMaker.remaining();
                    break;
            }
        }
    }
}

enum FlavorTemplate {
    ESPRESSO(250, 0, 16, 4, 1),
    LATTE(350, 75, 20, 7, 1),
    CAPPUCINO(200, 100, 12, 6,1);

    int water, milk, coffee, price, cups;

    FlavorTemplate(int water, int milk, int coffee, int price, int cups) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.price = price;
        this.cups = cups;
    }

}

class CoffeeMaker {

    enum machineStock {
        RESOURCE(400, 540, 120, 550, 9);
        int water, milk, coffee, price, cups;

        machineStock(int water, int milk, int coffee, int price, int cups) {
            this.water = water;
            this.milk = milk;
            this.coffee = coffee;
            this.price = price;
            this.cups = cups;
        }
    }

    static void buy(String flavorOfCoffee) {
        switch (flavorOfCoffee) {
            case "1":
                flavorOfCoffee = "ESPRESSO";
                break;
            case "2":
                flavorOfCoffee = "LATTE";
                break;
            case "3":
                flavorOfCoffee = "CAPPUCINO";
                break;
        }
        System.out.println(brewCoffee(flavorOfCoffee));
    }

    static void fill() {
        Scanner s = new Scanner(System.in);
        System.out.println("Write how many ml of water do you want to add:");
        int addWater = s.nextInt();
        machineStock.RESOURCE.water += addWater;
        System.out.println("Write how many ml of milk do you want to add:");
        int addMilk = s.nextInt();
        machineStock.RESOURCE.milk += addMilk;
        System.out.println("Write how many grams of coffee beans do you want to add:");
        int addCoffee = s.nextInt();
        machineStock.RESOURCE.coffee += addCoffee;
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        int addCups = s.nextInt();
        machineStock.RESOURCE.cups += addCups;
    }

    static void take() {
        System.out.println("I gave you $ " + machineStock.RESOURCE.price);
        machineStock.RESOURCE.price = 0;
    }

    static void remaining() {
        System.out.println("The coffee machine has:\n" +
                machineStock.RESOURCE.water + " of water\n" +
                machineStock.RESOURCE.milk + " of milk\n" +
                machineStock.RESOURCE.coffee + " of coffee beans\n" +
                machineStock.RESOURCE.cups + " of disposable cups\n" +
                "$" + machineStock.RESOURCE.price + " of money");
    }

    static String brewCoffee(String flavorOfCoffee) {
        int water = machineStock.RESOURCE.water;
        int milk = machineStock.RESOURCE.milk;
        int coffee = machineStock.RESOURCE.coffee;
        int cups = machineStock.RESOURCE.cups;
        String prompt = " ";

        if (water >= FlavorTemplate.valueOf(flavorOfCoffee).water   &&
            milk >= FlavorTemplate.valueOf(flavorOfCoffee).milk     &&
            coffee >= FlavorTemplate.valueOf(flavorOfCoffee).coffee &&
            cups > 0)
        {
            prompt = "I have enough Resource, making you a Coffee!";

            machineStock.RESOURCE.water -= FlavorTemplate.valueOf(flavorOfCoffee).water;
            machineStock.RESOURCE.milk -= FlavorTemplate.valueOf(flavorOfCoffee).milk;
            machineStock.RESOURCE.coffee -= FlavorTemplate.valueOf(flavorOfCoffee).coffee;
            machineStock.RESOURCE.cups -= 1;
            machineStock.RESOURCE.price += FlavorTemplate.valueOf(flavorOfCoffee).price;

        } else if (water < FlavorTemplate.valueOf(flavorOfCoffee).water) {
            prompt = "Sorry, not enough water!";
        } else if (milk < FlavorTemplate.valueOf(flavorOfCoffee).milk) {
            prompt = "Sorry, not enough milk!";
        } else if (coffee < FlavorTemplate.valueOf(flavorOfCoffee).milk) {
            prompt = "Sorry, not enough coffee!";
        } else if (cups == 0) {
            prompt = "Sorry, not enough cups!";
        }
        return prompt;
    }

}
