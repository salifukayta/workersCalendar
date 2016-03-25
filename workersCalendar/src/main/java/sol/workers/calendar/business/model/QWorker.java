package sol.workers.calendar.business.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QWorker is a Querydsl query type for QWorker
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QWorker extends com.querydsl.sql.RelationalPathBase<QWorker> {

    private static final long serialVersionUID = -275792683;

    public static final QWorker Worker = new QWorker("Worker");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Float> hasFixedRestDay = createNumber("hasFixedRestDay", Float.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath lastName = createString("lastName");

    public final NumberPath<Integer> restDay = createNumber("restDay", Integer.class);

    public final com.querydsl.sql.PrimaryKey<QWorker> workerPK = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QPost> _workerPostIFK = createInvForeignKey(id, "id worker");

    public final com.querydsl.sql.ForeignKey<QVacation> _workerVacationIFK = createInvForeignKey(id, "id worker");

    public QWorker(String variable) {
        super(QWorker.class, forVariable(variable), "null", "Worker");
        addMetadata();
    }

    public QWorker(String variable, String schema, String table) {
        super(QWorker.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QWorker(Path<? extends QWorker> path) {
        super(path.getType(), path.getMetadata(), "null", "Worker");
        addMetadata();
    }

    public QWorker(PathMetadata metadata) {
        super(QWorker.class, metadata, "null", "Worker");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(firstName, ColumnMetadata.named("FIRST NAME").withIndex(1).ofType(Types.VARCHAR).withSize(2000000000).withDigits(10));
        addMetadata(hasFixedRestDay, ColumnMetadata.named("HAS FIXED REST DAY").withIndex(3).ofType(Types.FLOAT).withSize(2000000000).withDigits(10));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(0).ofType(Types.INTEGER).withSize(2000000000).withDigits(10));
        addMetadata(lastName, ColumnMetadata.named("LAST NAME").withIndex(2).ofType(Types.VARCHAR).withSize(2000000000).withDigits(10));
        addMetadata(restDay, ColumnMetadata.named("REST DAY").withIndex(4).ofType(Types.INTEGER).withSize(2000000000).withDigits(10));
    }

}

