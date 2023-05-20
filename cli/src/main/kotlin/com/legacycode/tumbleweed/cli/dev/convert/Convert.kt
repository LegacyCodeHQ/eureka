package com.legacycode.tumbleweed.cli.dev.convert

import com.legacycode.tumbleweed.viz.edgebundling.EdgeBundlingGraph
import com.legacycode.tumbleweed.viz.edgebundling.EdgeBundlingGraph.Link
import com.legacycode.tumbleweed.viz.edgebundling.EdgeBundlingGraph.Node
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

private const val CSV_HEADER = 1

fun EdgeBundlingGraph.Companion.from(
  csv: String,
): EdgeBundlingGraph {
  val csvRecords = CSVParser.parse(csv, CSVFormat.DEFAULT).records.drop(CSV_HEADER)
  val links = csvRecords.map(Link::from).sortedBy { it.source }
  val nodes = links.flatMap(Node::from).distinct().sortedWith(compareBy(Node::group, Node::id))
  return EdgeBundlingGraph(nodes, links)
}

fun Link.Companion.from(csvRecord: CSVRecord): Link {
  val source = csvRecord.get(0)
  val target = csvRecord.get(1)
  return Link(source, target)
}

fun Node.Companion.from(signature: String): Node {
  return if (signature.contains("(") && signature.contains(")")) {
    Node(signature, 2)
  } else {
    Node(signature, 1)
  }
}

fun Node.Companion.from(link: Link): List<Node> {
  return listOf(
    Node.from(link.source),
    Node.from(link.target),
  )
}
