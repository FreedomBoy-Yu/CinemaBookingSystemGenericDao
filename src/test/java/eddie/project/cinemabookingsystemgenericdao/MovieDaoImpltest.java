package eddie.project.cinemabookingsystemgenericdao;


import eddie.project.cinemabookingsystemgenericdao.dao.MovieDao;
import eddie.project.cinemabookingsystemgenericdao.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MovieDaoImpltest {
    @Autowired
    private MovieDao movieDao;

    @Test
    public void insertTest(){
        Movie movie = new Movie();
        movie.setMovieName("Fate 零");
        movie.setRoomWay("2B");
        movie.setBookAble(Boolean.FALSE);
        movieDao.insert(movie);
    }
    @Test
    public void findByMovieNameTest(){
        String movieName = "寄生獸"; // 測試資料
        Movie movie = movieDao.findByMovieName(movieName);
        System.out.println(movieName+"的影廳號碼是"+movie.getRoomWay());
    }
    @Test
    public void findAllMovieTest(){
        List<Movie> movies = movieDao.findAll();
        for(Movie movie : movies){
            System.out.println(movie.getMovieName());
        }
    }
    @Test
    public void updateMovieTest(){
        Movie movie = new Movie();
        movie.setId(5);
        movie.setMovieName("Fate0");
        movie.setRoomWay("2B");
        movie.setBookAble(Boolean.FALSE);
        movieDao.update(movie);

    }
}
