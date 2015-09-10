package dna.md;

import java.io.File;

import dna.metrics.Metric;
import dna.metrics.assortativity.AssortativityR;
import dna.metrics.degree.DegreeDistributionR;
import dna.metrics.motifs.UndirectedMotifsPerNodesR;
import dna.metrics.motifs.UndirectedMotifsU;
import dna.metrics.paths.IntWeightedAllPairsShortestPathsR;
import dna.metrics.paths.UnweightedAllPairsShortestPathsR;
import dna.metrics.weights.RootMeanSquareDeviationR;
import dna.metrics.weights.RootMeanSquareFluctuationR;
import dna.plot.Plotting;
import dna.plot.PlottingConfig.PlotFlag;
import dna.series.Series;
import dna.series.data.SeriesData;

public class Analysis extends MD {

	public static final String separator2 = "_";

	public static enum MetricType {
		UM4, UM4_PNS, APSP_W, APSP, RMSD, RMSF, DD, ASS
	}

	public MetricType[] mts;
	public String[] mps;

	public int runs;
	public int batches;

	public static void main(String[] args) throws Exception {
		if (!isOk(args)) {
			printHelp(args);
			return;
		}

		Analysis a = new Analysis(args);
		if (new File(a.dataDir).exists()) {
			System.out.println(a.dataDir + " exists... skipping");
			return;
		}
		a.execute();
	}

	public void execute() throws Exception {
		Series s = new Series(getGG(), getBG(), getMetrics(), dataDir, name);
		SeriesData sd = runs == 1 ? s.generateRuns(0, 0, batches) : s.generate(
				runs, batches);
		Plotting.plot(sd, plotDir, PlotFlag.plotSingleScalarValues);
	}

	public Analysis(String[] args) {
		super(args);
		int index = MD.getMinArgs();
		if (args[index++].equals(separator)) {
			mts = new MetricType[0];
		} else {
			String[] temp = args[index - 1].split(separator);
			mts = new MetricType[temp.length];
			for (int i = 0; i < temp.length; i++) {
				mts[i] = MetricType.valueOf(temp[i]);
			}
		}
		if (args[index++].equals(separator)) {
			mps = new String[0];
		} else {
			mps = args[index - 1].split(separator);

		}
		runs = Integer.parseInt(args[index++]);
		batches = Integer.parseInt(args[index++]);
	}

	public static int getMinArgs() {
		return MD.getMinArgs() + 4;
	}

	public static void printHelp(String[] args) {
		MD.printHelp(getMinArgs());
		int index = MD.getMinArgs();
		printHelp(index++, "metric types", MetricType.values());
		printHelp(index++, "metric parameters - sep: " + separator);
		printHelp(index++, "runs");
		printHelp(index++, "batches");
		System.err.println("only received thw following " + args.length
				+ " parameters:");
		for (int i = 0; i < args.length; i++) {
			System.err.println("  " + i + ": " + args[i]);
		}
	}

	public Metric[] getMetrics() {
		Metric[] metrics = new Metric[mts.length];
		for (int i = 0; i < mts.length; i++) {
			metrics[i] = getMetric(mts[i], mps[i]);
		}
		return metrics;
	}

	public Metric getMetric(MetricType mt, String p) {
		switch (mt) {
		case APSP:
			return new UnweightedAllPairsShortestPathsR();
		case APSP_W:
			return new IntWeightedAllPairsShortestPathsR();
		case ASS:
			return new AssortativityR();
		case DD:
			return new DegreeDistributionR();
		case RMSD:
			return new RootMeanSquareDeviationR();
		case RMSF:
			return new RootMeanSquareFluctuationR(Integer.parseInt(p));
		case UM4:
			return new UndirectedMotifsU();
		case UM4_PNS:
			String[] temp = p.split(separator2);
			int[] indexes = new int[temp.length];
			for (int i = 0; i < temp.length; i++) {
				indexes[i] = Integer.parseInt(temp[i]);
			}
			return new UndirectedMotifsPerNodesR(indexes);
		default:
			throw new IllegalArgumentException("unknown metric type: " + mt);
		}
	}

}
