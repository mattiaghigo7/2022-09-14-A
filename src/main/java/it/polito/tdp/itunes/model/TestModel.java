package it.polito.tdp.itunes.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo(120.0);
		System.out.println(model.getVerticiSize());
		System.out.println(model.getArchiSize());
	}

}
