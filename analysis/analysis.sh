#!/bin/bash

dataDir="data"
plotDir="plots"

runs="1"
batches="9999999999"

# 0: name
# 1: src dir
# 2: filename (graph)
# 3: suffix (batches)
# 4: data dir
# 5: plot dir
# 6: graph type (READ) - sep: __
# 7: edge type (DIRECTED, UNDIRECTED) - sep: __
# 8: weight type (NONE, V_DOUBLE_3D, E_DOUBLE_1D) - sep: __
# 9: metric types (UM4, APSP_W, APSP, RMSD, RMSF, DD, ASS) - sep: __
# 10: metric parameters - sep: __
# 11: runs
# 12: batches

filename="0.dnag"
suffix=".dnab"
mainDataDir="data"
mainPlotDir="plots"
graphType="READ"
edgeType="UNDIRECTED"
runs="1"
batches="99999999"

function analysis {
	name=$1
	srcDir=$2
	weightType=$3
	metricTypes=$4
	metricParameters=$5

	dataDir="$mainDataDir/$weightType/$metricTypes----$metricParameters/"
	plotDir="$mainPlotDir/$weightType/$metricTypes----$metricParameters/"

	java -jar analysis.jar $name $srcDir $filename $suffix $dataDir $plotDir $graphType $edgeType $weightType $metricTypes $metricParameters $runs $batches
}

analysis test1 datasets/unweighted/ NONE DD__ASS__APSP -__-__-