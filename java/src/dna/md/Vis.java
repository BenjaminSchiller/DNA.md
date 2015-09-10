package dna.md;

public class Vis extends MD {

	public static void main(String[] args) {
		if (!isOk(args)) {
			printHelp();
			return;
		}
	}

	public Vis(String[] args) {
		super(args);
		int index = MD.getMinArgs();
	}

	public static int getMinArgs() {
		return MD.getMinArgs() + 0;
	}

	public static void printHelp() {
		MD.printHelp(getMinArgs());
		int index = MD.getMinArgs();
	}

}
