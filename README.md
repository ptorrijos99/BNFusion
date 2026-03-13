# Genetic Algorithms for Tractable Bayesian Network Consensus

This repository contains the source code for the paper **"Genetic Algorithms for Tractable Bayesian Network Consensus: A Unified Treewidth-Constrained Framework"**.

## Overview
Bayesian network structural fusion combines locally learned networks into a consensus model, which is central to federated and distributed learning. However, the unrestricted fusion creates dense graphs whose high treewidth makes exact inference intractable.

This repository implements a unified framework that tackles two complementary problems under a treewidth constraint:
1. **Fusion approximation (P1)**: Approximating the unrestricted fusion under a treewidth bound.
2. **Structural consensus (P2)**: Finding the consensus network most representative of the inputs under the same constraint.

Four genetic algorithm variants parameterize the search space differently:
* **GA-$\mathcal{E}_a$**: Pruning arcs from the post-fusion graph.
* **GA-$\mathcal{E}_c$**: Pruning unique arcs from the input networks before fusion.
* **GA-$\mathcal{E}_b$**: Pruning arcs with an independent decision per network.
* **GA-$\mathcal{E}_b^{MC}$**: A hybrid variant that uses a min-cut-based greedy method as a warm start to seed the GA-$\mathcal{E}_b$ population.

## Prerequisites
- Java 17
- Maven

## Build Instructions
To compile the project and build the executable JAR with dependencies, run:

```bash
mvn clean package
```

This will generate a JAR file in the `target/` directory (e.g., `bnfusion-1.0-jar-with-dependencies.jar`).

## Running the Experiments
The main entry point for running the experiments is the `consensusBN.Experiments.ExperimentsJournal` class. This class runs all the algorithms (greedy and genetic variants) for a given network and parameters at different treewidth values.

Run the compiled JAR:

```bash
java -cp target/bnfusion-1.0-jar-with-dependencies.jar consensusBN.Experiments.ExperimentsJournal [index] [params_file]
```

Or just run without arguments to execute a quick local test (by default it will run `alarm` with 10 clients and output parameters):

```bash
java -cp target/bnfusion-1.0-jar-with-dependencies.jar consensusBN.Experiments.ExperimentsJournal
```

### Parameters File
The program reads a text file line by line using the `index` (0-indexed) to select the specific run's configuration. Each line should be space-separated with the following parameters:

1. `net`: Name of the network (e.g., `alarm.0`)
2. `nClients`: Number of clients/input DAGs (e.g., `10`)
3. `popSize`: Population size for the GA (e.g., `100`)
4. `nIterations`: Number of generations for the GA (e.g., `100`)
5. `twLimit`: Minimum Treewidth limit setting (e.g., `2`)
6. `seed`: Random seed for reproducibility (e.g., `0`)
7. `useGES` (Optional): `true` to learn input DAGs using GES from data, `false` to generate perturbations (default: `false`).
8. `optimizeSMHD` (Optional): `true` to use SMHD fitness, `false` to use FSim fitness (default: `true`).
9. `optimizeAgainstOriginals` (Optional): `true` to measure against input DAGs (P2), `false` to measure against G+ (P1) (default: `true`).
10. `useGreedyWarmstart` (Optional): `true` to inject greedy result into population, `false` for random init (default: `true`).

### Output
The results are saved as `.csv` files inside the `results/Server/` folder, and the convergence curves for the genetic algorithms are stored in the `results/Server/convergence/` subfolder.
