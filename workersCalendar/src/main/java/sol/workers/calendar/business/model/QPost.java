package sol.workers.calendar.business.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPost is a Querydsl query type for QPost
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPost extends com.querydsl.sql.RelationalPathBase<QPost> {

    private static final long serialVersionUID = -594908233;

    public static final QPost Post = new QPost("Post");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> idWorker = createNumber("idWorker", Integer.class);

    public final StringPath postName = createString("postName");

    public final com.querydsl.sql.PrimaryKey<QPost> postPK = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QWorker> postWORKERFK = createForeignKey(idWorker, "id");

    public QPost(String variable) {
        super(QPost.class, forVariable(variable), "null", "Post");
        addMetadata();
    }

    public QPost(String variable, String schema, String table) {
        super(QPost.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPost(Path<? extends QPost> path) {
        super(path.getType(), path.getMetadata(), "null", "Post");
        addMetadata();
    }

    public QPost(PathMetadata metadata) {
        super(QPost.class, metadata, "null", "Post");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("ID").withIndex(0).ofType(Types.INTEGER).withSize(2000000000).withDigits(10));
        addMetadata(idWorker, ColumnMetadata.named("ID WORKER").withIndex(1).ofType(Types.INTEGER).withSize(2000000000).withDigits(10));
        addMetadata(postName, ColumnMetadata.named("POST NAME").withIndex(2).ofType(Types.VARCHAR).withSize(2000000000).withDigits(10));
    }

}

