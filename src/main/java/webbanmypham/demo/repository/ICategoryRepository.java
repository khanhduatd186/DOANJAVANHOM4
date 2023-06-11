package webbanmypham.demo.repository;

import webbanmypham.demo.enity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository  extends JpaRepository<Category, Long> {
}
