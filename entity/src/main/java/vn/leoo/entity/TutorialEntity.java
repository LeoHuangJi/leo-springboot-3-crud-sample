package vn.leoo.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tutorials", schema = "SHOPLI")
public class TutorialEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	
	@Column(name = "title")
	private String title;
	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;

	@Column(name = "parent_id")
	private String parent_id;
	@Column(name = "CREATEDDATE")
	private Timestamp createdDate;

	public TutorialEntity() {
		super();
	}
	@PrePersist
    protected void onCreate() {
        createdDate =  new Timestamp(System.currentTimeMillis());
    }

	public TutorialEntity(String id, String title, String code, String description, boolean published, String parent_id,
			Timestamp createdDate, long category_id) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
		this.description = description;
		this.published = published;
		this.parent_id = parent_id;
		this.createdDate = createdDate;
		this.category_id = category_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	@Column(name = "category_id")
	private Long category_id;

}
