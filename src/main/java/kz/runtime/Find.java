package kz.runtime;

import jakarta.persistence.*;
import kz.runtime.entity.Category;

import java.util.List;
import java.util.Scanner;

public class Find {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        // Введите название категории: Процессоры

        // Процессоры
        // - Intel
        // - AMD

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название категории");
        String name = scanner.nextLine();
        TypedQuery<Category> categoryTypedQuery = manager.createQuery("select с from Category с where с.name =?1", Category.class);
        categoryTypedQuery.setParameter(1, name);
        Category category;

        while (true) {
            try {
                category = categoryTypedQuery.getSingleResult();

                break;


            } catch (NoResultException e) {
                System.out.println("Категория не существует,введите еще раз");
                name = scanner.nextLine();
                categoryTypedQuery.setParameter(1, name);

            }
        }
        Long left = category.getLeft_key();
        Long right = category.getRight_key();


        TypedQuery<Category> categoryTypedQuery1 = manager.createQuery("select c from Category c where c.left_key>?1 and c.right_key<?2", Category.class);
        categoryTypedQuery1.setParameter(1,left);
        categoryTypedQuery1.setParameter(2,right);

        System.out.println(category.getName());
        //
        //
        //
        //






        List<Category> listc= categoryTypedQuery1.getResultList();

        for (int i=0;i<listc.size();i++){
            System.out.println(listc.get(i).getName());
        }


    }
}
