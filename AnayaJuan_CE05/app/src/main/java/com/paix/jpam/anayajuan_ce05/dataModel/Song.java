// Juan Pablo Anaya
// MDF3 ­ 201608
// Song

package com.paix.jpam.anayajuan_ce05.dataModel;

import java.io.Serializable;

public class Song implements Serializable {

    /*Properties*/
    private final String artist;
    private final String songName;
    private final int artwork;
    private final int song;

    /*Constructor*/
    public Song(String artist, String songName, int artwork, int song) {
        this.artist = artist;
        this.artwork = artwork;
        this.song = song;
        this.songName = songName;


    }

    /*Getters*/
    //Album Artwork
    public int getArtwork() {
        return artwork;
    }

    //Artist
    public String getArtist() {
        return artist;
    }

    //Song
    public int getSong() {
        return song;
    }

    //Song Name
    public String getSongName() {
        return songName;
    }

}
