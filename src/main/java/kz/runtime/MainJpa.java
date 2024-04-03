package kz.runtime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import kz.runtime.entity.Category;

import java.util.List;

public class MainJpa {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        Category category = manager.find(Category.class,2);
        System.out.println(category.getName());

        /*TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c where c.left_key between 1 and 10",Category.class);
        List<Category> categoryList=  categoryTypedQuery.getResultList();
        for(int i=0;i<categoryList.size();i++) {
            System.out.println(categoryList.get(i).getName());
        }*/
        //вывести категории но чтобы перед каждой категорией был уровень вложенности
        // Комплектующие
        //-Процессоры
        //--Intel
        //--AMD
        //-ОЗУ
        //Аудиотехника

        TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c ",Category.class);
        List<Category> categoryList=  categoryTypedQuery.getResultList();
        String b= "-";

        for(int i=0;i<categoryList.size();i++) {

            for (int j=0;j<categoryList.get(i).getLevel();j++){
            System.out.print(b);

            }
            System.out.println(categoryList.get(i).getName());
            System.out.println();
        }



    }
}
