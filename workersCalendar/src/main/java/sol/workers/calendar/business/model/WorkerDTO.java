package sol.workers.calendar.business.model;

import javax.annotation.Generated;

/**
 * WorkerDTO is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class WorkerDTO {

    private String firstName;

    private Float hasFixedRestDay;

    private Integer id;

    private String lastName;

    private Integer restDay;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Float getHasFixedRestDay() {
        return hasFixedRestDay;
    }

    public void setHasFixedRestDay(Float hasFixedRestDay) {
        this.hasFixedRestDay = hasFixedRestDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRestDay() {
        return restDay;
    }

    public void setRestDay(Integer restDay) {
        this.restDay = restDay;
    }

}

