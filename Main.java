import Entities.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        start();
    }
    private static void start(){
        ArrayList<Item>itemList= new ArrayList<>();

        Scanner sc =new Scanner(System.in);
        boolean exit=false;
        System.out.println("welcome to MyFridge");
        do{

            System.out.print("command: ");
            switch (sc.nextLine()){
                case "?": System.out.println("Available commands: exit, fridge, recipes");break;
                case "exit": exit=true;break;
                case "fridge": manageFridge(sc, itemList);break;
                case "recipes": manageRecipes(sc, itemList);break;
                default: System.out.println("invalid command");
            }
        }while (!exit);

    }

    //<editor-fold desc="MANAGERECIPES">
    private static void manageRecipes(Scanner sc, ArrayList<Item> itemList) {
        boolean exit=false;
        System.out.println("we're managing our Recipes");
        do{
            System.out.print("manageRecipes: ");
            switch (sc.nextLine()){
                case "?":
                    System.out.println("available commands: add, search, remove, modify, back");break;
                case "add": addRecipe(sc, itemList);break;
                case "search": findRecipe(sc, itemList);break;
                case "remove": removeRecipe(sc, itemList);break;
                case "modify": modifyRecipe(sc, itemList);break;
                case "back": exit=true;break;
                default: System.out.println("invalid command!");
            }
        }while (!exit);
    }

    private static void modifyRecipe(Scanner sc, ArrayList<Item> itemList) {
        //TODO modifyRecipe
    }

    private static void removeRecipe(Scanner sc, ArrayList<Item> itemList) {
        System.out.print("which recipe? ");
    }

    private static void findRecipe(Scanner sc, ArrayList<Item> itemList) {
        System.out.print("Name of the recipe: ");
        String recipename=sc.nextLine();
        ArrayList<String>loadedrecipe=new ArrayList<>();
        try{
            BufferedReader recipe =new BufferedReader(new FileReader("recipes/"+recipename+".txt"));

            while(recipe.ready()){
                loadedrecipe.add(recipe.readLine());
            }
            System.out.println();
            for(int i=0;i<loadedrecipe.size();i++){
                System.out.println(loadedrecipe.get(i));
            }
            recipe.close();
            System.out.println();
        }catch (IOException e){
            System.out.println("Loading failed! "+e);
        }
        //TODO findRecipe
    }


    //<editor-fold desc="ADDRECIPE">
    private static void addRecipe(Scanner sc, ArrayList<Item> itemList) {
        String name=modifyName(sc);
        ArrayList<String>directions=new ArrayList<>();
        ArrayList<Item>ingredientList = new ArrayList<>();
        boolean exit=false;
        do{
            System.out.print("What do we add next? ");
            switch (sc.nextLine()){
                case "?":System.out.println("available commands: addingredient, modifiyname, removeingredient, adddirection, finalize, cancel");break;
                case "addingredient": newItem(sc, ingredientList);break;
                case "modifyname": name=modifyName(sc);break;
                case "removeingredient": removeIngredient(sc, ingredientList);break;
                case "adddirection": addDirection(sc, directions);break;
                case "finalize": finalizeRecipe(sc ,name, ingredientList, directions);exit=true;break;
                case "cancel": System.out.println("Cancelling recipe");exit=true;break;
            }
        }while (!exit);


    }


    //<editor-fold desc="ADDDIRECTION">
    private static void addDirection(Scanner sc, ArrayList<String>directions) {
        boolean exit=false;
        do{
            System.out.print("We're adding the Direction, what now? ");
            switch (sc.nextLine()){
                case "?": System.out.println("available commands: addstep, removestep, modifystep, finalize, cancel");break;
                case "addstep": addStep(sc, directions);break;
                case "removestep": removeStep(sc, directions);break;
                case "finalize": System.out.println("Finalizing Direction");exit=true;break;
                case "modifystep": modifyStep(sc, directions);break;
                case "cancel": System.out.println("Cancelling Direction");directions.clear();exit=true;break;
            }
        }while(!exit);

    }

    private static void modifyStep(Scanner sc, ArrayList<String>directions) {
        System.out.print("which step? ");
        int step=sc.nextInt();
        System.out.println("step "+step+":");
        String modified=sc.nextLine();
        directions.set((step-1),modified);
        System.out.println("step modified");

    }

    private static void removeStep(Scanner sc, ArrayList<String> directions) {
        System.out.print("which step? ");
        directions.remove(sc.nextInt()-1);
    }

    private static void addStep(Scanner sc, ArrayList<String> directions) {
        System.out.println("step "+(directions.size()+1)+": ");
        directions.add(sc.nextLine());
    }
    //</editor-fold>


    private static void finalizeRecipe(Scanner sc, String name, ArrayList<Item>ingredientList, ArrayList<String>directions) {
        System.out.print("Are you sure? ");
        if(sc.nextLine().equalsIgnoreCase("yes")){

            try{
                PrintWriter recipeout = new PrintWriter(new FileWriter("recipes/"+name+".txt"));
                recipeout.println(name);
                recipeout.println("ingredients:");
                for (Item item : ingredientList) {
                    recipeout.println("- " + item.getQuantity() + "" + item.getUnit() + " " + item.getName());
                }
                recipeout.println("directions:");
                for(int i=0;i<directions.size();i++){
                    recipeout.println((i+1)+". "+directions.get(i));
                }
                System.out.println("Recipe added!");
                recipeout.close();
            }catch (IOException e){
                System.out.println("Failed to finalize recipe "+e);
            }

        }else{

        }
    }

    private static void removeIngredient(Scanner sc, ArrayList<Item>ingredientList) {
        int i=0;
        System.out.println("which ingredient? ");
        String ingredient = sc.nextLine();
        while (i<ingredientList.size() && !ingredient.equals(ingredientList.get(i).getName())){
            i++;
        }
        if(i<ingredientList.size()){
            ingredientList.remove(i);
            System.out.println("ingredient removed");
        }else{
            System.out.println("ingredient not found");
        }
    }

    private static String modifyName(Scanner sc) {
        System.out.print("Name: ");
        return sc.nextLine();
    }
    //</editor-fold>
    //</editor-fold>


    //<editor-fold desc="MANAGEFRIDGE">
    private static void manageFridge(Scanner sc, ArrayList<Item>itemList) {
        System.out.println("We're managing the Fridge");
        do{
            System.out.print("manageFridge: ");
            switch (sc.nextLine()){
                case "back": start();System.exit(0);break;
                case "listItems": listItems(itemList);break;
                case "newItem": newItem(sc, itemList);break;
                case "modifyAmount": modifyAmount(sc, itemList);break;
                default: System.out.println("invalid command!");
            }
        }while (true);
    }

    private static void modifyAmount(Scanner sc, ArrayList<Item> itemList) {
        System.out.println("item name: ");
        String name=sc.nextLine();
        System.out.println("amount: ");
        int amount=sc.nextInt();
        int k=0;
        while (k<itemList.size() && itemList.get(k).getName().equalsIgnoreCase(name)){
            k++;
        }
        if(k<itemList.size()){
            itemList.get(k).setQuantity(itemList.get(k).getQuantity()+amount);
            itemList.set(k, itemList.get(k));
        }

    }

    private static void newItem(Scanner sc, ArrayList<Item> itemList) {
        Scanner scanner =new Scanner(System.in);
        System.out.print("Name: ");
        String name=sc.nextLine();
        System.out.print("Quantity: ");
        int quantity = 0;
        boolean error=false;

        try{

            quantity = sc.nextInt();
        }catch (InputMismatchException e){
            System.out.println("invalid input!");
        }



        System.out.print("Unit: ");
        String unit=scanner.nextLine();
        itemList.add(new Item(name, quantity, unit));
    }

    private static void listItems(ArrayList<Item>itemList) {
        if(itemList.isEmpty()){
            System.out.println("Our fridge is empty :(");
        }else {
            for (Item item : itemList) {
                System.out.println(item.getName() + ": " + item.getQuantity() + " " + item.getUnit());
            }
        }
    }
    //</editor-fold>
}

