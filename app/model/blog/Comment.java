package model.blog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    /** Serial version. */
	private static final long serialVersionUID = -2994994091330003981L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	public String author;
    
    public Date date;
     
    @Lob
    public String content;
    
    @ManyToOne
    public Post post;
}
