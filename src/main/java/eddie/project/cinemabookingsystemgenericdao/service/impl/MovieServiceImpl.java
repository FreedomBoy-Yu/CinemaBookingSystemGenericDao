package eddie.project.cinemabookingsystemgenericdao.service.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.MovieDao;
import eddie.project.cinemabookingsystemgenericdao.dto.movie.MovieDTO;
import eddie.project.cinemabookingsystemgenericdao.entity.Movie;
import eddie.project.cinemabookingsystemgenericdao.exception.CustomNotFoundException;
import eddie.project.cinemabookingsystemgenericdao.exception.DatabaseException;
import eddie.project.cinemabookingsystemgenericdao.service.MovieService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try {
            movieDao.insert(movie);
        }catch(PersistenceException e){
            throw new DatabaseException("資料庫錯誤，新增電影", e);

        }
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


//    @Override
//    public MovieDTO findByMovieName(String movieName) {
//        Movie movie=movieDao.findByMovieName(movieName);
//        MovieDTO movieDTO = new MovieDTO(movie.getMovieName(),movie.getRoomWay(),movie.getBookAble());
////        movieDTO.setMovieName();
////        movieDTO.setRoomWay();
////        movieDTO.setBookable();
//        return movieDTO;
//    }
/*以下方法是GPT建議的做法*/
    @Override
    public MovieDTO findByMovieName(String movieName) {
        // ✅ 避免 `null` 問題，若找不到電影，拋出 `CustomNotFoundException`
        Movie movie = Optional.ofNullable(movieDao.findByMovieName(movieName))
                .orElseThrow(() -> new CustomNotFoundException("找不到電影名稱：" + movieName));

        return new MovieDTO(movie.getMovieName(), movie.getRoomWay(), movie.getBookAble());
    }

    @Override
    public MovieDTO findByMovieId(Integer movieId) {
        MovieDTO movieDto = new MovieDTO();
        Movie movie=movieDao.findById(movieId);
        movieDto.setMovieName(movie.getMovieName());
        movieDto.setRoomWay(movie.getRoomWay());
        movieDto.setBookAble(movie.getBookAble());
        return movieDto;
    }
    /*以下方法是GPT建議的做法*/

    @Override
    public void updateMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setMovieName(movieDTO.getMovieName());
        movie.setRoomWay(movieDTO.getRoomWay());
        movie.setBookAble(movieDTO.getBookAble());
        try {
            movieDao.update(movie);
        }catch(PersistenceException e){
            throw new DatabaseException("資料庫錯誤，更新電影失敗", e);
        }
    }
}
