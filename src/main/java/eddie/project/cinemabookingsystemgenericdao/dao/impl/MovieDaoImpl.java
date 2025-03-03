package eddie.project.cinemabookingsystemgenericdao.dao.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.MovieDao;
import eddie.project.cinemabookingsystemgenericdao.entity.Movie;
import eddie.project.cinemabookingsystemgenericdao.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao {

    @Autowired
    private MovieMapper movieMapper;
    @Override
    public void insert(Movie movie) {//C
        movieMapper.insertMovie(movie);
    }

    @Override
    public Movie findById(Integer id) {//R
        return movieMapper.findMovieById(id);
    }


    @Override
    public List<Movie> findAll() {
        return movieMapper.findAllMovie();
    }

    @Override
    public void update(Movie movie) {
        movieMapper.updateMovie(movie);
    }

    @Override
    public void deleteById(Integer integer) {
        //不能刪除任何電影，因為要留下紀錄
    }

    @Override
    public Movie findByMovieName(String movieName) {
        return movieMapper.findMovieByName(movieName);
    }

}
