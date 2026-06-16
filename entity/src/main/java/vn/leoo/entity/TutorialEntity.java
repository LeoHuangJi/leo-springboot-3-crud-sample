package vn.leoo.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tutorials", schema = "SHOPLI")
@Setter
@Getter
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
	private Boolean published;

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

	@Column(name = "category_id")
	private Long category_id;
}
