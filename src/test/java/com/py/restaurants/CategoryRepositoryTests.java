package com.py.restaurants;

import com.py.restaurants.domain.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldFindByNameReturnCategory() {
        Category category1 = new Category("Cat1");
        Category category2 = new Category("Cat2");
        Category category3 = new Category("Cat3");

        testEntityManager.persist(category1);
        testEntityManager.persist(category2);
        testEntityManager.persist(category3);
        testEntityManager.flush();

        Optional<Category> maybeCat1 = categoryRepository.findByName("Cat1");
        assertThat(maybeCat1.isPresent()).isTrue();
        assertThat(maybeCat1.get().getName()).isEqualTo("Cat1");

        Optional<Category> maybeCatNotFound = categoryRepository.findByName("Not present :)");
        assertThat(maybeCatNotFound.isPresent()).isFalse();
    }

}
