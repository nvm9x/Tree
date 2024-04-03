package kz.runtime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import kz.runtime.entity.Category;

import java.util.Scanner;

public class Replace {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();

            System.out.println("Выберите категорию которую вы хотите перенести");
            Scanner scanner = new Scanner(System.in);
            int id = scanner.nextInt();

            Category category = manager.find(Category.class, id);
            Long left = category.getLeft_key();
            Long right = category.getRight_key();

            //Поменяли узлы категории которую хотим перенести на отрицательный знак чтобы изолировать

            // Сделать отрицательными левый и правый при условии left > ? and right < ?

            Query updateLeft = manager.createQuery("update Category c set c.left_key=c.left_key*(-1), c.right_key = c.right_key * (-1) where c.right_key<=?1" +
                    "and c.left_key>=?2");
            updateLeft.setParameter(1, right);
            updateLeft.setParameter(2, left);
            updateLeft.executeUpdate();


            //Восстановили числовой порядок узлов без категории которую изолировали
            Query updateQuery = manager.createQuery("update Category c set c.left_key = c.left_key-((?2-?1)+1) where c.left_key>?1");
            updateQuery.setParameter(1, left);
            updateQuery.setParameter(2, right);
            updateQuery.executeUpdate();

            Query updateQuery1 = manager.createQuery("update Category c set c.right_key=c.right_key-((?2-?1)+1) where c.right_key>?2");
            updateQuery1.setParameter(1, left);
            updateQuery1.setParameter(2, right);
            updateQuery1.executeUpdate();

//            //условия только на больше
//
//
            System.out.println("Выберите категорию в которую нужно переместить");
            int id2 = scanner.nextInt();

            Category category1 = manager.find(Category.class, id2); //выбрали категорию в которую хочу добавить изолированную
            Long left1 = category1.getLeft_key();
            Long right1 = category1.getRight_key();
            Long level = category1.getLevel();

            System.out.println(left1);
            System.out.println(right1);
            System.out.println(level);

            //освободили место для новой катгории,сдвинули все узлы на 6
            Query leftQuery = manager.createQuery("update Category c set c.left_key=c.left_key+(?1-?2)+1 where c.left_key>?3");
            leftQuery.setParameter(1,right);
            leftQuery.setParameter(2,left);
            leftQuery.setParameter(3, left1);
            leftQuery.executeUpdate();

            Query rightQuery = manager.createQuery("update Category c set c.right_key=c.right_key+(?1-?2)+1 where c.right_key<=?3 ");
            rightQuery.setParameter(1,right);
            rightQuery.setParameter(2,left);
            rightQuery.setParameter(3, right1);
            rightQuery.executeUpdate();


            //вставляем категорию на новое место

            // -2 -7 -> 6 17 -> 11 16
            // -3 -4            12 13
            // -5 -6            14 15

            // 0 - (-2) + (17 - 7 - 1) = 11
            // 0 - (-6) + (17 - 7 - 1) = 15

//            Query newQuery = manager.createQuery("update Category c set c.left_key=c.left_key*(-1)+4 where c.left_key<0");
//            newQuery.executeUpdate();
//
//            Query newQuery1 = manager.createQuery("update Category c set c.right_key=c.right_key*(-1)+4 where c.right_key<0");
//            newQuery1.executeUpdate();
//
//            Query levelQuery = manager.createQuery("update Category c set c.level=?1+1 where c.id=?2");
//            levelQuery.setParameter(1, level);
//            levelQuery.setParameter(2, id);
//            levelQuery.executeUpdate();


            manager.getTransaction().commit();

        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);

        }


    }
}
