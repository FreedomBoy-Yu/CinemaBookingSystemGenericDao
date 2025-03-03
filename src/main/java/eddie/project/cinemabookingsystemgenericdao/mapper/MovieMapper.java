package eddie.project.cinemabookingsystemgenericdao.mapper;
import eddie.project.cinemabookingsystemgenericdao.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {
    void insertMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovieById(Movie movie);
    List<Movie> findAllMovie();
    Movie findMovieByName(String movieName);
    Movie findMovieById(Integer movieId);

}
