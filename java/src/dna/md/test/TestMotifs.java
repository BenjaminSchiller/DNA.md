package dna.md.test;

import java.io.IOException;

import dna.graph.datastructures.GDS;
import dna.graph.generators.GraphGenerator;
import dna.graph.generators.random.RandomGraph;
import dna.metrics.Metric;
import dna.metrics.MetricNotApplicableException;
import dna.metrics.motifs.UndirectedMotifsPerNodesR;
import dna.plot.Plotting;
import dna.plot.PlottingConfig.PlotFlag;
import dna.series.Series;
import dna.series.data.SeriesData;
import dna.updates.generators.BatchGenerator;
import dna.updates.generators.random.RandomBatch;
import dna.util.Config;

public class TestMotifs {

	public static void main(String[] args) throws IOException,
			MetricNotApplicableException, InterruptedException {
		Config.zipRuns();

		// test("R.vs.U", 20, new UndirectedMotifsR(), new UndirectedMotifsU());

		test("per-nodes-1", 20, new UndirectedMotifsPerNodesR(0),
				new UndirectedMotifsPerNodesR(1),
				new UndirectedMotifsPerNodesR(2));
		test("per-nodes-2", 50, new UndirectedMotifsPerNodesR(0, 1),
				new UndirectedMotifsPerNodesR(1, 0));
		test("per-nodes-3", 50, new UndirectedMotifsPerNodesR(0, 1, 2),
				new UndirectedMotifsPerNodesR(0, 2, 1),
				new UndirectedMotifsPerNodesR(1, 0, 2),
				new UndirectedMotifsPerNodesR(1, 2, 0),
				new UndirectedMotifsPerNodesR(2, 0, 1),
				new UndirectedMotifsPerNodesR(2, 1, 0));
		test("per-nodes-3--single", 50, new UndirectedMotifsPerNodesR(0, 1, 2));
	}

	public static void test(String name, int batches, Metric... metrics)
			throws IOException, MetricNotApplicableException,
			InterruptedException {
		String data = "data/test/" + name + "/";
		String plots = "plots/test/" + name + "/";

		// if (new File(data).exists()) {
		// System.out.println(data + " exists...");
		// return;
		// }

		GraphGenerator gg = new RandomGraph(GDS.undirected(), 20, 10);
		BatchGenerator bg = new RandomBatch(0, 0, 5, 0);
		Series s = new Series(gg, bg, metrics, data, name);
		SeriesData sd = s.generateRuns(0, 0, batches);
		Plotting.plot(sd, plots, PlotFlag.plotMetricValues);
	}

}
