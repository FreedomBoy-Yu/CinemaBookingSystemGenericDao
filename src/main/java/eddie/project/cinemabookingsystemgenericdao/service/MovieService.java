package eddie.project.cinemabookingsystemgenericdao.service;

import eddie.project.cinemabookingsystemgenericdao.dto.movie.MovieDTO;

import java.util.List;

public interface MovieService {



    public void insertMovie(MovieDTO movieDTO);//Create

    public List<MovieDTO> findAll();//Read

    public MovieDTO findByMovieName(String movieName);//Read
    public MovieDTO findByMovieId(Integer movieId);

    public void updateMovie(MovieDTO movie);//Update

    /*
    public void deleteById(Integer id);
    不做這個功能，因為沒有必要刪除


     */



}
