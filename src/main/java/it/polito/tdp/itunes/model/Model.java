package it.polito.tdp.itunes.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private List<Album> allAlbum;
	private Map<Integer,Album> idMap;
	
	private Graph<Album,DefaultEdge> grafo;
	private List<Album> vertici;
	private List<Coppie> archi;
	
	private Set<Album> migliore;
	private double durataMax;
	
	public Model() {
		this.dao=new ItunesDAO();
		this.allAlbum=new ArrayList<>();
		this.allAlbum=dao.getAllAlbums();
		this.idMap=new HashMap<>();
		for(Album a : this.allAlbum) {
			this.idMap.put(a.getAlbumId(), a);
		}
	}
	
	public Set<Album> calcolaSet(Album a1, double durata) {
		if(a1.getDurata()>durata) {
			return null;
		}
		this.migliore=new HashSet<>();
		this.durataMax=durata;
		List<Album> parziale=new ArrayList<>();
		parziale.add(a1);
		List<Album> conn = new ArrayList<>(this.connesse(a1));
		conn.remove(a1);
		this.migliore=new HashSet<>(parziale);
		ricorsione(parziale, conn);
		return migliore;
	}
	
	private void ricorsione(List<Album> parziale, List<Album> conn){	
		if(parziale.size()>migliore.size()) {
			migliore = new HashSet<>(parziale);
		}
		for(Album a : conn) {
			if(!parziale.contains(a) && (this.calcolaD(parziale)+a.getDurata())<=this.durataMax) {
				parziale.add(a);
				ricorsione(parziale,conn);
				parziale.remove(a);
			}
		}
	}
	
	private double calcolaD(List<Album> parziale) {
		double n = 0.0;
		for(Album a : parziale) {
			n+=a.getDurata();
		}
		return n;
	}
	
	public void creaGrafo(Double duration) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		this.vertici=new ArrayList<>();
		this.vertici=dao.getVertici(duration, idMap);
		Graphs.addAllVertices(this.grafo, this.vertici);
//		System.out.println(this.grafo.vertexSet().size());
		
		this.archi=new ArrayList<>();
		this.archi=dao.getArchi(duration, idMap);
		for(Coppie c : this.archi) {
			Graphs.addEdgeWithVertices(this.grafo, c.getA1(), c.getA2());
		}
//		System.out.println(this.grafo.edgeSet().size());
	}
	
	public Set<Album> connesse(Album a) {
		ConnectivityInspector<Album, DefaultEdge> inspector = new ConnectivityInspector<>(this.grafo);
		return inspector.connectedSetOf(a);
	}
	
	public Integer calcolaConnesse(Album a) {
		ConnectivityInspector<Album, DefaultEdge> inspector = new ConnectivityInspector<>(this.grafo);
		return inspector.connectedSetOf(a).size();
	}
	
	public double pesoConnesse(Album a) {
		ConnectivityInspector<Album, DefaultEdge> inspector = new ConnectivityInspector<>(this.grafo);
		Set<Album> conn = inspector.connectedSetOf(a);
		double n = 0.0;
		for(Album al : conn) {
			n+=al.getDurata();
		}
		return n;
	}

	public int getVerticiSize() {
		return vertici.size();
	}

	public int getArchiSize() {
		return archi.size();
	}
	
	public List<Album> getVertici() {
		vertici.sort(null);
		return vertici;
	}
}
