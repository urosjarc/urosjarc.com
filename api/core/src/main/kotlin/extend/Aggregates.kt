package extend

import com.mongodb.ExplainVerbosity
import com.mongodb.kotlin.client.AggregateIterable
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonString
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.json.JsonWriterSettings
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

fun Aggregates_lookup(
    from: KMutableProperty1<*, *>,
    to: KMutableProperty1<*, *>,
    pipeline: List<Bson>? = null,
    dataclasses: Boolean = true
): Bson {
    val local_class = (to as CallableReference).owner as KClass<*>
    val local_collection = local_class.simpleName.toString()
    val local_class_name = local_collection.lowercase()
    val local_class_name_capitalize = local_class_name.replaceFirstChar { it.uppercaseChar() }

    val foreign_class = (from as CallableReference).owner as KClass<*>
    val foreign_collection = foreign_class.simpleName.toString()
    val foreign_class_name = foreign_collection.lowercase()
    val foreign_class_name_capitalize = foreign_class_name.replaceFirstChar { it.uppercaseChar() }

    var newField = foreign_class_name
    val db_ref = from.name.count { it.equals('_') } == 2
    if (db_ref) newField += "_${from.name.split("_").getOrNull(1)}"
    newField += "_refs"

    if (dataclasses) {
        println("@Serializable")
        println("data class ${local_class_name_capitalize}Db (")
        println("\tval $local_class_name : ${local_class_name_capitalize},")
        if (pipeline != null) println("\tval ${newField} : List<${foreign_class_name_capitalize}Db> = listOf()")
        else println("\tval ${newField} : List<${foreign_class_name_capitalize}> = listOf()")
        println(")\n")
    }

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

fun AggregateIterable<*>.explain_aggregation() {
    val explanation = this.explain(ExplainVerbosity.QUERY_PLANNER).get("command") as Document
    val jsonSetting = JsonWriterSettings.builder().indent(true).build()
    println(explanation.toJson(jsonSetting))
}
