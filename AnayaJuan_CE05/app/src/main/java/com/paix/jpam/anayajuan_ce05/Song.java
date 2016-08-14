// Juan Pablo Anaya
// MDF3 ­ 201608
// Song

package com.paix.jpam.anayajuan_ce05;

import java.io.Serializable;

public class Song implements Serializable {

    /*Properties*/
    String artist;
    String album;
    String songName;
    int artwork;
    int song;

    /*Constructor*/
    public Song(String artist, String album, String songName, int artwork, int song) {
        this.artist = artist;
        this.album = album;
        this.artwork = artwork;
        this.song = song;
        this.songName = songName;


    }

    /*Getters*/
    //Album Artwork
    public int getArtwork() {
        return artwork;
    }

    //Album Name
    public String getAlbum() {
        return album;
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
