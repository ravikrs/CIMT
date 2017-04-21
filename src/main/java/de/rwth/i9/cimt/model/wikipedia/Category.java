package de.rwth.i9.cimt.model.wikipedia;
// Generated Mar 14, 2017 3:48:09 PM by Hibernate Tools 4.3.5.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "category", catalog = "mediawiki", uniqueConstraints = @UniqueConstraint(columnNames = "cat_title"))
public class Category implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6737683275473316680L;
	private Integer catId;
	private String catTitle;
	private int catPages;
	private int catSubcats;
	private int catFiles;

	public Category() {
	}

	public Category(String catTitle, int catPages, int catSubcats, int catFiles) {
		this.catTitle = catTitle;
		this.catPages = catPages;
		this.catSubcats = catSubcats;
		this.catFiles = catFiles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "cat_id", unique = true, nullable = false)
	public Integer getCatId() {
		return this.catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	@Column(name = "cat_title", unique = true, nullable = false)
	public String getCatTitle() {
		return this.catTitle;
	}

	public void setCatTitle(String catTitle) {
		this.catTitle = catTitle;
	}

	@Column(name = "cat_pages", nullable = false)
	public int getCatPages() {
		return this.catPages;
	}

	public void setCatPages(int catPages) {
		this.catPages = catPages;
	}

	@Column(name = "cat_subcats", nullable = false)
	public int getCatSubcats() {
		return this.catSubcats;
	}

	public void setCatSubcats(int catSubcats) {
		this.catSubcats = catSubcats;
	}

	@Column(name = "cat_files", nullable = false)
	public int getCatFiles() {
		return this.catFiles;
	}

	public void setCatFiles(int catFiles) {
		this.catFiles = catFiles;
	}

}