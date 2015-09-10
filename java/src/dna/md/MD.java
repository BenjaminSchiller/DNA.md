package dna.md;

import java.io.IOException;

import dna.graph.datastructures.GDS;
import dna.graph.datastructures.GraphDataStructure;
import dna.graph.generators.GraphGenerator;
import dna.graph.generators.util.ReadableFileGraph;
import dna.graph.weights.Double3dWeight;
import dna.graph.weights.DoubleWeight;
import dna.graph.weights.Weight.WeightSelection;
import dna.io.filter.SuffixFilenameFilter;
import dna.updates.generators.BatchGenerator;
import dna.updates.generators.util.ReadableDirBatchGenerator;

public abstract class MD {

	public static final String separator = "__";

	public String name;

	public String srcDir;
	public String filename;
	public String suffix;

	public String dataDir;
	public String plotDir;

	public static enum GraphType {
		READ
	}

	public GraphType gt;

	public static enum EdgeType {
		DIRECTED, UNDIRECTED
	}

	public EdgeType et;

	public static enum WeightType {
		NONE, V_DOUBLE_3D, E_DOUBLE_1D
	}

	public WeightType wt;

	public MD(String[] args) {
		int index = 0;
		name = args[index++];
		srcDir = args[index++];
		filename = args[index++];
		suffix = args[index++];
		dataDir = args[index++];
		plotDir = args[index++];
		gt = GraphType.valueOf(args[index++]);
		et = EdgeType.valueOf(args[index++]);
		wt = WeightType.valueOf(args[index++]);
	}

	public static int getMinArgs() {
		return 9;
	}

	public static boolean isOk(String[] args) {
		return args.length >= getMinArgs();
	}

	public static void printHelp(int minArgs) {
		System.err.println("expecting " + minArgs + " arguments:");
		int index = 0;
		printHelp(index++, "name");
		printHelp(index++, "src dir");
		printHelp(index++, "filename (graph)");
		printHelp(index++, "suffix (batches)");
		printHelp(index++, "data dir");
		printHelp(index++, "plot dir");
		printHelp(index++, "graph type", GraphType.values());
		printHelp(index++, "edge type", EdgeType.values());
		printHelp(index++, "weight type", WeightType.values());
	}

	public static void printHelp(int index, String name) {
		System.err.println("  " + index + ": " + name);
	}

	public static void printHelp(int index, String name, Object[] list) {
		StringBuffer buff = new StringBuffer();
		for (Object e : list) {
			if (buff.length() == 0) {
				buff.append(e.toString());
			} else {
				buff.append(", " + e.toString());
			}
		}
		printHelp(index, name + " (" + buff.toString() + ") - sep: "
				+ separator);
	}

	public GraphDataStructure getGds() {
		switch (et) {
		case DIRECTED:
			switch (wt) {
			case NONE:
				return GDS.directed();
			case V_DOUBLE_3D:
				return GDS
						.directedV(Double3dWeight.class, WeightSelection.Zero);
			case E_DOUBLE_1D:
				return GDS.directedE(DoubleWeight.class, WeightSelection.Zero);
			default:
				throw new IllegalArgumentException("unknown weight type: " + wt);
			}
		case UNDIRECTED:
			switch (wt) {
			case NONE:
				return GDS.undirected();
			case V_DOUBLE_3D:
				return GDS.undirectedV(Double3dWeight.class,
						WeightSelection.Zero);
			case E_DOUBLE_1D:
				return GDS
						.undirectedE(DoubleWeight.class, WeightSelection.Zero);
			default:
				throw new IllegalArgumentException("unknown weight type: " + wt);
			}
		default:
			throw new IllegalArgumentException("unknown edge type: " + et);
		}
	}

	public GraphGenerator getGG() throws IOException {
		switch (gt) {
		case READ:
			return new ReadableFileGraph(srcDir, filename, getGds());
		default:
			throw new IllegalArgumentException("unknown graph type: " + gt);
		}
	}

	public BatchGenerator getBG() {
		switch (gt) {
		case READ:
			return new ReadableDirBatchGenerator("batches", srcDir,
					new SuffixFilenameFilter(suffix));
		default:
			throw new IllegalArgumentException("unknown graph type: " + gt);
		}
	}

}
