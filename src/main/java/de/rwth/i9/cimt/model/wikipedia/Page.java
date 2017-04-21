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
 * Page generated by hbm2java
 */
@Entity
@Table(name = "page", catalog = "mediawiki", uniqueConstraints = @UniqueConstraint(columnNames = { "page_namespace",
		"page_title" }))
public class Page implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4891829315292238429L;
	private Integer pageId;
	private int pageNamespace;
	private String pageTitle;
	private byte[] pageRestrictions;
	private byte pageIsRedirect;
	private byte pageIsNew;
	private double pageRandom;
	private byte[] pageTouched;
	private byte[] pageLinksUpdated;
	private int pageLatest;
	private int pageLen;
	private byte[] pageContentModel;
	private byte[] pageLang;

	public Page() {
	}

	public Page(int pageNamespace, String pageTitle, byte[] pageRestrictions, byte pageIsRedirect, byte pageIsNew,
			double pageRandom, byte[] pageTouched, int pageLatest, int pageLen) {
		this.pageNamespace = pageNamespace;
		this.pageTitle = pageTitle;
		this.pageRestrictions = pageRestrictions;
		this.pageIsRedirect = pageIsRedirect;
		this.pageIsNew = pageIsNew;
		this.pageRandom = pageRandom;
		this.pageTouched = pageTouched;
		this.pageLatest = pageLatest;
		this.pageLen = pageLen;
	}

	public Page(int pageNamespace, String pageTitle, byte[] pageRestrictions, byte pageIsRedirect, byte pageIsNew,
			double pageRandom, byte[] pageTouched, byte[] pageLinksUpdated, int pageLatest, int pageLen,
			byte[] pageContentModel, byte[] pageLang) {
		this.pageNamespace = pageNamespace;
		this.pageTitle = pageTitle;
		this.pageRestrictions = pageRestrictions;
		this.pageIsRedirect = pageIsRedirect;
		this.pageIsNew = pageIsNew;
		this.pageRandom = pageRandom;
		this.pageTouched = pageTouched;
		this.pageLinksUpdated = pageLinksUpdated;
		this.pageLatest = pageLatest;
		this.pageLen = pageLen;
		this.pageContentModel = pageContentModel;
		this.pageLang = pageLang;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "page_id", unique = true, nullable = false)
	public Integer getPageId() {
		return this.pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	@Column(name = "page_namespace", nullable = false)
	public int getPageNamespace() {
		return this.pageNamespace;
	}

	public void setPageNamespace(int pageNamespace) {
		this.pageNamespace = pageNamespace;
	}

	@Column(name = "page_title", nullable = false)
	public String getPageTitle() {
		return this.pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	@Column(name = "page_restrictions", nullable = false)
	public byte[] getPageRestrictions() {
		return this.pageRestrictions;
	}

	public void setPageRestrictions(byte[] pageRestrictions) {
		this.pageRestrictions = pageRestrictions;
	}

	@Column(name = "page_is_redirect", nullable = false)
	public byte getPageIsRedirect() {
		return this.pageIsRedirect;
	}

	public void setPageIsRedirect(byte pageIsRedirect) {
		this.pageIsRedirect = pageIsRedirect;
	}

	@Column(name = "page_is_new", nullable = false)
	public byte getPageIsNew() {
		return this.pageIsNew;
	}

	public void setPageIsNew(byte pageIsNew) {
		this.pageIsNew = pageIsNew;
	}

	@Column(name = "page_random", nullable = false, precision = 22, scale = 0)
	public double getPageRandom() {
		return this.pageRandom;
	}

	public void setPageRandom(double pageRandom) {
		this.pageRandom = pageRandom;
	}

	@Column(name = "page_touched", nullable = false)
	public byte[] getPageTouched() {
		return this.pageTouched;
	}

	public void setPageTouched(byte[] pageTouched) {
		this.pageTouched = pageTouched;
	}

	@Column(name = "page_links_updated")
	public byte[] getPageLinksUpdated() {
		return this.pageLinksUpdated;
	}

	public void setPageLinksUpdated(byte[] pageLinksUpdated) {
		this.pageLinksUpdated = pageLinksUpdated;
	}

	@Column(name = "page_latest", nullable = false)
	public int getPageLatest() {
		return this.pageLatest;
	}

	public void setPageLatest(int pageLatest) {
		this.pageLatest = pageLatest;
	}

	@Column(name = "page_len", nullable = false)
	public int getPageLen() {
		return this.pageLen;
	}

	public void setPageLen(int pageLen) {
		this.pageLen = pageLen;
	}

	@Column(name = "page_content_model")
	public byte[] getPageContentModel() {
		return this.pageContentModel;
	}

	public void setPageContentModel(byte[] pageContentModel) {
		this.pageContentModel = pageContentModel;
	}

	@Column(name = "page_lang")
	public byte[] getPageLang() {
		return this.pageLang;
	}

	public void setPageLang(byte[] pageLang) {
		this.pageLang = pageLang;
	}

}