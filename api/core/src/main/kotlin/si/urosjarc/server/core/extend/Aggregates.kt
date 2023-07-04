package si.urosjarc.server.core.extend

import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonString
import org.bson.conversions.Bson
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

fun Aggregates_lookup(
    from: KMutableProperty1<*, *>,
    to: KMutableProperty1<*, *>,
    pipeline: List<Bson>? = null
): Bson {
    val local_class = (to as CallableReference).owner as KClass<*>
    val local_collection = local_class.simpleName.toString()
    val local_class_name = local_collection.lowercase()

    val foreign_class = (from as CallableReference).owner as KClass<*>
    val foreign_collection = foreign_class.simpleName.toString()
    val foreign_class_name = foreign_collection.lowercase()

    var newField = foreign_class_name
    if (from.name.count { it.equals('_') } == 2)
        newField += "_${from.name.split("_").getOrNull(1)}"

    val inside = BsonDocument("from", BsonString(foreign_collection))
        .append("foreignField", BsonString(from.name))
        .append("localField", BsonString("${local_class_name}.${to.name}"))
        .append("as", BsonString(newField))

    if (pipeline != null) {
        val arr = BsonArray()
        arr.add(Aggregates_project_root(key = foreign_class).toBsonDocument())
        pipeline.forEach { arr.add(it.toBsonDocument()) }
        inside.append("pipeline", arr)
    }

    return BsonDocument("\$lookup", inside)
}

fun Aggregates_project_root(key: KClass<*>): Bson {
    return BsonDocument(
        "\$project", BsonDocument(
            key.simpleName.toString().lowercase(), BsonString("\$\$ROOT")
        )
    )
}
