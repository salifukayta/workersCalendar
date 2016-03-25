package sol.workers.calendar.business.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QVacation is a Querydsl query type for QVacation
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QVacation extends com.querydsl.sql.RelationalPathBase<QVacation> {

    private static final long serialVersionUID = -469986700;

    public static final QVacation Vacation = new QVacation("Vacation");

    public final NumberPath<Integer> dayIndex = createNumber("dayIndex", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> idWorker = createNumber("idWorker", Integer.class);

    public final com.querydsl.sql.PrimaryKey<QVacation> vacationPK = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QWorker> vacationWORKERFK = createForeignKey(idWorker, "id");

    public QVacation(String variable) {
        super(QVacation.class, forVariable(variable), "null", "Vacation");
        addMetadata();
    }

    public QVacation(String variable, String schema, String table) {
        super(QVacation.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QVacation(Path<? extends QVacation> path) {
        super(path.getType(), path.getMetadata(), "null", "Vacation");
        addMetadata();
    }

    public QVacation(PathMetadata metadata) {
        super(QVacation.class, metadata, "null", "Vacation");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(dayIndex, ColumnMetadata.named("DAY INDEX").withIndex(2).ofType(Types.INTEGER).withSize(2000000000).withDigits(10));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(0).ofType(Types.INTEGER).withSize(2000000000).withDigits(10));
        addMetadata(idWorker, ColumnMetadata.named("ID WORKER").withIndex(1).ofType(Types.INTEGER).withSize(2000000000).withDigits(10));
    }

}

