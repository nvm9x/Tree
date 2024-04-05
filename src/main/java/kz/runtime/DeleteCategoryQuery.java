package kz.runtime;

import jakarta.persistence.*;
import kz.runtime.entity.Category;

import java.util.List;

public class DeleteCategoryQuery {


    public static void deleteCategory(){
        EntityManagerFactory factory = CentralFactory.createManager();
        EntityManager manager = factory.createEntityManager();

        try{
            manager.getTransaction().begin();

            Category category = manager.find(Category.class,2);
            Long left = category.getLeft_key();
            Long right =category.getRight_key();

            Query deleteQuery = manager.createQuery("DELETE FROM Category c WHERE c.left_key >= ?1 AND c.right_key <= ?2");
            deleteQuery.setParameter(1, left);
            deleteQuery.setParameter(2, right);

            int deletedCount=deleteQuery.executeUpdate();

            Query updateQuery = manager.createQuery("UPDATE Category c set c.left_key=c.left_key-((?1-?2)+1) where c.left_key>?2");
            updateQuery.setParameter(1,right);
            updateQuery.setParameter(2,left);
            updateQuery.executeUpdate();

            Query updateQuery1 = manager.createQuery("UPDATE Category c set c.right_key=c.right_key-((?1-?2)+1)");
            updateQuery1.setParameter(1,right);
            updateQuery1.setParameter(2,left);
            updateQuery1.executeUpdate();


           /* TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c where "+
                    "c.right_key > ?1", Category.class);
            categoryTypedQuery.setParameter(1,right);

            List<Category> categoryList = categoryTypedQuery.getResultList();
            for(int i =0;i<categoryList.size();i++){
                Long left_key=categoryList.get(i).getLeft_key();
                Long right_key= categoryList.get(i).getRight_key();
                categoryList.get(i).setLeft_key(left_key-deletedCount*2);
                categoryList.get(i).setRight_key(right_key-deletedCount*2);

            }*/

            manager.getTransaction().commit();
        }
        catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }


    }

    }

