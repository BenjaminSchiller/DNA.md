package dna.md.test;

import dna.graph.Graph;
import dna.graph.datastructures.GDS;
import dna.graph.generators.GraphGenerator;
import dna.graph.generators.random.RandomGraph;
import dna.graph.weights.Double3dWeight;
import dna.graph.weights.DoubleWeight;
import dna.graph.weights.Weight.WeightSelection;
import dna.io.BatchWriter;
import dna.io.GraphWriter;
import dna.updates.batch.Batch;
import dna.updates.batch.BatchSanitization;
import dna.updates.generators.BatchGenerator;
import dna.updates.generators.random.RandomBatch;

public class TestDataGeneration {

	public static void main(String[] args) {
		int batches = 20;
		String data = "../analysis/datasets/";

		generate(data + "unweighted/", new RandomGraph(GDS.undirected(), 100,
				200), new RandomBatch(0, 0, 20, 10), batches);
		generate(
				data + "node-3d-weighted/",
				new RandomGraph(GDS.undirectedV(Double3dWeight.class,
						WeightSelection.RandTrim3), 100, 200), new RandomBatch(
						0, 0, 30, WeightSelection.RandTrim3, 0, 0, 0, null),
				batches);
		generate(
				data + "edge-1d-weighted/",
				new RandomGraph(GDS.undirectedE(DoubleWeight.class,
						WeightSelection.RandTrim3), 100, 200), new RandomBatch(
						0, 0, 0, null, 20, 10, 20, WeightSelection.RandTrim3),
				batches);
	}

	public static void generate(String dir, GraphGenerator gg,
			BatchGenerator bg, int batches) {
		Graph g = gg.generate();
		GraphWriter.write(g, dir, "0.dnag");
		System.out.println("0: => " + dir + "0.dnag");
		for (int i = 1; i <= batches; i++) {
			if (!bg.isFurtherBatchPossible(g)) {
				System.out.println(i + ": no further batch possible");
				break;
			}
			Batch b = bg.generate(g);
			BatchSanitization.sanitize(b);
			BatchWriter.write(b, dir, i + ".dnab");
			b.apply(g);
			System.out.println(i + ": => " + dir + i + ".dnab");
		}
	}
}
