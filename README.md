# DNA.md

In this repo, we provide a set of scripts we use for the analysis of dynamic graph generated from MD (Molecular Dynamics) simulations.
Here, amino acids represent nodes and edges are formed in case their distance is below a certain threshold (unit-sphere graphs).
The analysis of these graphs is performed with [DNA (Dynamic Network Analyzer)](http://github.com/BenjaminSchiller/DNA).

The graph are read from the DNA format, each from a separate directory containing initial graph and batches.

## Java Code

The java code used for the execution can be found in the `java/` directory.
The two main execution classes (`Analysis.java` and `Vis.java`) are all located in the package `dna.md`.

## Ant Build File

To build jars for the three main Java classes (`analysis.jar` and `vis.jar`), an ant build file is provided in `analysis/build.xml`.

## Bash Scripts

In `analysis/`, we provide a set of bash scripts that can be used for performing the afforementioned analyses.

### Execution

For the execution of the analysis, we use the [JOBS scripts](https://github.com/BenjaminSchiller/jobs).

	analysis.sh
Performing the analyses using `analysis.jar`.

	vis.sh
Generation of animated gifs of a given graph using `vis.jar`.

## Results

Some results can be found on [dynamic-networks.org](http://dynamic-networks.org/use_cases/md/).

## jar Parameters

### analysis.jar

	...

### vis.jar

	...