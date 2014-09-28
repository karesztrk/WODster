package model.blog;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;

@Entity
@Table(name = "comment")
public class Comment extends Activity {

    /** Serial version. */
	private static final long serialVersionUID = -2994994091330003981L;
	
    @ManyToOne
    @Required
    public Post post;
}
