package sol.workers.calendar.business.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QSqliteSequence is a Querydsl query type for QSqliteSequence
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QSqliteSequence extends com.querydsl.sql.RelationalPathBase<QSqliteSequence> {

    private static final long serialVersionUID = -1447128508;

    public static final QSqliteSequence sqliteSequence = new QSqliteSequence("sqlite_sequence");

    public final StringPath name = createString("name");

    public final StringPath seq = createString("seq");

    public QSqliteSequence(String variable) {
        super(QSqliteSequence.class, forVariable(variable), "null", "sqlite_sequence");
        addMetadata();
    }

    public QSqliteSequence(String variable, String schema, String table) {
        super(QSqliteSequence.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSqliteSequence(Path<? extends QSqliteSequence> path) {
        super(path.getType(), path.getMetadata(), "null", "sqlite_sequence");
        addMetadata();
    }

    public QSqliteSequence(PathMetadata metadata) {
        super(QSqliteSequence.class, metadata, "null", "sqlite_sequence");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(name, ColumnMetadata.named("name").withIndex(0).ofType(Types.VARCHAR).withSize(2000000000).withDigits(10));
        addMetadata(seq, ColumnMetadata.named("seq").withIndex(1).ofType(Types.VARCHAR).withSize(2000000000).withDigits(10));
    }

}

