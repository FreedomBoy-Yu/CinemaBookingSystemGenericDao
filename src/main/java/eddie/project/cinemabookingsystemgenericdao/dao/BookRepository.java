package eddie.project.cinemabookingsystemgenericdao.dao;

import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findByUserId(Integer userId, Pageable pageable);
}
