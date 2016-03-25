package sol.workers.calendar.business.model;

import javax.annotation.Generated;

/**
 * VacationDTO is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
public class VacationDTO {

    private Integer dayIndex;

    private Integer id;

    private Integer idWorker;

    public Integer getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(Integer dayIndex) {
        this.dayIndex = dayIndex;
    }

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

}

