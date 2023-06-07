package it.polito.tdp.itunes.db;

public class TestItunesDAO {

	public static void main(String[] args) {
		ItunesDAO dao = new ItunesDAO();
		System.out.println(dao.getAllAlbums().size());
		System.out.println(dao.getAllArtists().size());
		System.out.println(dao.getAllPlaylists().size());
		System.out.println(dao.getAllTracks().size());
		System.out.println(dao.getAllGenres().size());
		System.out.println(dao.getAllMediaTypes().size());


	}

}
