package sol.workers.calendar.business.model;

import javax.annotation.Generated;

/**
 * PostDTO is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class PostDTO {

    private Integer id;

    private Integer idWorker;

    private String postName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Integer idWorker) {
        this.idWorker = idWorker;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

}

