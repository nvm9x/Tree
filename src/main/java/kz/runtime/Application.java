package kz.runtime;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выбрать действие");
        System.out.println("1-создать категорию");
        System.out.println("2- проверить наличие категории в списке");
        System.out.println("3- перемещение категорию");
        System.out.println("4- удалить категорию");


        int num = Integer.parseInt(scanner.nextLine());

        switch (num) {
            case 1:
                System.out.println("Создаем категорию");
                Create.createCategory();
                break;
            case 2:
                System.out.println("проверить наличие категории в списке");
                Find.find();
                break;

            case 3:
                System.out.println("перемещение категорию");
                Replace.move();
                break;

            case 4:
                System.out.println("удалить категорию");
                DeleteCategoryQuery.deleteCategory();

                break;

            default:
                System.out.println("Вариант не существует,введите еще раз");
        }
    }
}
