package eddie.project.cinemabookingsystemgenericdao.dao;

import eddie.project.cinemabookingsystemgenericdao.entity.Movie;

public interface MovieDao extends GenericDao<Movie, Integer> {
    Movie findByMovieName(String movieName);
}
