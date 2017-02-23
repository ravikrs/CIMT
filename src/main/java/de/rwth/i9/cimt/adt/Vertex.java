package de.rwth.i9.cimt.adt;

import java.util.Comparator;

public class Vertex implements Comparable<Vertex> {

	private double score;
	private String value;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Vertex(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(final Vertex that) {
		if (this.score < that.getScore()) {
			return -1;
		} else if (this.score > that.getScore()) {
			return 1;
		} else {
			return this.value.compareTo(that.getValue());
		}
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof Vertex) {
			Vertex that = (Vertex) obj;
			if (this.value.equals(that.getValue()))
				return true;
		}
		return false;
	}

	public static Comparator<Vertex> VertexScoreAscComparator = new Comparator<Vertex>() {

		@Override
		public int compare(Vertex o1, Vertex o2) {
			// TODO Auto-generated method stub
			return o1.compareTo(o2);
		}

	};
	public static Comparator<Vertex> VertexScoreDescComparator = new Comparator<Vertex>() {

		@Override
		public int compare(Vertex o1, Vertex o2) {
			// TODO Auto-generated method stub
			return o2.compareTo(o1);
		}

	};
}
