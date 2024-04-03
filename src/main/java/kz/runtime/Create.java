package kz.runtime;

import jakarta.persistence.*;
import kz.runtime.entity.Category;

import java.util.List;
import java.util.Scanner;

public class Create {
    public static  void main(String[] args) {

        //Создать новую категорию в уже имеющейся типа в процессорах добавить третий подвид
        // сначала задача точно так же найти категорию
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        // update Category c set c.left = c.left + 2 where c.left > ?1

        /*
        Query query = manager.createQuery("update Category c set c.left_key = c.left_key + 2 where c.left_key > ?1");
        query.setParameter(1, parent_right_key);
        query.executeUpdate();
        */

        try {
            manager.getTransaction().begin();


            Category category = manager.find(Category.class, 2);
            Long left = category.getLeft_key();
            Long right = category.getRight_key();

            /*TypedQuery<Category> categoryTypedQuery1 = manager.createQuery("select c from Category c where c.right_key>=?1", Category.class);

            categoryTypedQuery1.setParameter(1, right);*/
            Query updateQuery = manager.createQuery("UPDATE Category c set c.left_key=c.left_key+2  where c.left_key >= ?1");
            updateQuery.setParameter(1, right);
            updateQuery.executeUpdate();

            Query updateQuery1 = manager.createQuery("UPDATE Category c set c.right_key=c.right_key+2  where c.right_key >= ?1");
            updateQuery1.setParameter(1, right);
            updateQuery1.executeUpdate();

           /*Query updateQuery2 = manager.createQuery("UPDATE Category c set c.right_key=c.right_key+2 where c.right_key >= ?1");
            updateQuery2.setParameter(1,right);
            updateQuery2.executeUpdate();*/


            /* List<Category> categoryList = categoryTypedQuery1.getResultList();
            for (int i = 0; i < categoryList.size(); i++) {
                Long left_key = categoryList.get(i).getLeft_key();
                Long right_key = categoryList.get(i).getRight_key();
                if(categoryList.get(i).getLeft_key()>=right){

                categoryList.get(i).setLeft_key(left_key += 2);
                categoryList.get(i).setRight_key(right_key += 2);}
                else {
                    categoryList.get(i).setLeft_key(left_key);
                    categoryList.get(i).setRight_key(right_key+=2);
                }

            }*/
            Category category1 = new Category();
            category1.setName("NVIDIA");
            category1.setLeft_key(right);
            category1.setRight_key(right + 1);
            category1.setLevel(Long.valueOf(2));
            manager.persist(category1);

            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);

        }
        manager.close();
        factory.close();


        //категория процессоры все ключи которые больше или равны 7 должны увеличиться на 2
        // у процессора левая не меняется а правая меняется
    }
}

//удалить категорию удалить процессоры и все что под ними находится






