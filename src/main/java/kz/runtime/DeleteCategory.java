package kz.runtime;

import jakarta.persistence.*;
import kz.runtime.entity.Category;

import java.util.List;

public class DeleteCategory {
    public static void main(String[] args) {
        //удалить категорию удалить процессоры и все что под ними находится

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            Category category = manager.find(Category.class, 2);
            Long left = category.getLeft_key();
            Long right = category.getRight_key();

            TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c " +
                    "where c.right_key<=?1 and c.left_key>=?2", Category.class);

            categoryTypedQuery.setParameter(1, right);
            categoryTypedQuery.setParameter(2, left);

            List<Category> categoriesToRemove = categoryTypedQuery.getResultList();
            for (Category categoryToRemove : categoriesToRemove) {
                manager.remove(categoryToRemove);
            }

            TypedQuery<Category> categoryQuery = manager.createQuery("select c from Category c", Category.class);
            List<Category> categoryList = categoryQuery.getResultList();
            for (Category categoryToUpdate : categoryList) {

                System.out.println(categoryToUpdate.getName());
               Long left_key = categoryToUpdate.getLeft_key();
                Long right_key = categoryToUpdate.getRight_key();
                if (categoryToUpdate.getLeft_key() < left) {
                    categoryToUpdate.setRight_key(right_key - 6);
                } else {
                    categoryToUpdate.setLeft_key(left_key - 6);
                    categoryToUpdate.setRight_key(right_key - 6);
                }

            }
            manager.getTransaction().commit();

        }
        catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }


    }
}
