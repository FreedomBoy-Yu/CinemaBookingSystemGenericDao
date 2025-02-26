package eddie.project.cinemabookingsystemgenericdao.service.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.MovieDao;
import eddie.project.cinemabookingsystemgenericdao.dto.movie.MovieDTO;
import eddie.project.cinemabookingsystemgenericdao.entity.Movie;
import eddie.project.cinemabookingsystemgenericdao.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//商業邏輯
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Override
    public void insertMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setMovieName(movieDTO.getMovieName());
        movie.setRoomWay(movieDTO.getRoomWay());
        movie.setBookAble(movieDTO.getBookAble());
        movieDao.insert(movie);
    }

    @Override
    public List<MovieDTO> findAll() {
        List<Movie> movies = movieDao.findAll(); // 取得所有 Movie
        List<MovieDTO> movieDTOs = new ArrayList<>();

        for (Movie movie : movies) {
            MovieDTO movieDTO = new MovieDTO(movie.getMovieName(), movie.getRoomWay(), movie.getBookAble());
            movieDTO.setId(movie.getId()); // 確保 ID 也被設置
            movieDTOs.add(movieDTO);
        }

        return movieDTOs;
    }


    @Override
    public MovieDTO findByMovieName(String movieName) {
        Movie movie=movieDao.findByMovieName(movieName);
        MovieDTO movieDTO = new MovieDTO(movie.getMovieName(),movie.getRoomWay(),movie.getBookAble());
//        movieDTO.setMovieName();
//        movieDTO.setRoomWay();
//        movieDTO.setBookable();
        return movieDTO;
    }

    @Override
    public void updateMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setMovieName(movieDTO.getMovieName());
        movie.setRoomWay(movieDTO.getRoomWay());
        movie.setBookAble(movieDTO.getBookAble());
        movieDao.update(movie);
    }
}
