package io.redgreen.tumbleweed.cli.dev.convert

import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph.Link
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph.Node
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

private const val CSV_HEADER = 1

fun BilevelEdgeBundlingGraph.Companion.from(
  csv: String,
): BilevelEdgeBundlingGraph {
  val csvRecords = CSVParser.parse(csv, CSVFormat.DEFAULT).records.drop(CSV_HEADER)
  val links = csvRecords.map(Link::from)
  val nodes = links.flatMap(Node::from).distinct()
  return BilevelEdgeBundlingGraph(nodes, links)
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
