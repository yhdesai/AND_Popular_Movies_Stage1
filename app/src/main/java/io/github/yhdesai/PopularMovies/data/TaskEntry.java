package io.github.yhdesai.PopularMovies.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



@Entity(tableName = "movie")
public class TaskEntry {



    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String id;
    private String title;
    private String moviePoster;
    private String plot;
    private String rating;
    private String releaseDate;
    private String backdropPoster;
   /* @ColumnInfo(name = "updated_at")
    private Date updatedAt;
*/

    @Ignore
    public TaskEntry(String title, String moviePoster, String plot, String rating, String releaseDate, String backdropPoster) {

        this.title = title;
        this.moviePoster = moviePoster;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.backdropPoster = backdropPoster;
       /* this.updatedAt = updatedAt;*/
    }

    public TaskEntry(String id, String title, String moviePoster, String plot, String rating, String releaseDate, String backdropPoster) {
        this.id = id;
        this.title = title;
        this.moviePoster = moviePoster;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.backdropPoster = backdropPoster;

    }




    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPoster() {
        return backdropPoster;
    }

    public void setBackdropPoster(String backdropPoster) {
        this.backdropPoster = backdropPoster;
    }
}




