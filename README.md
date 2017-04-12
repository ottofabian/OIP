# Optimization of industrial processes WI 14 SE A

## Overview

- [Group members](#group-members)
- [Docker](#docker)
- [Algorithms](#algorithms)
- [Results](#results)
- [Validation/Optimal values](#validationoptimal-values)

## Group members:
- [Alexander Baum](https://github.com/4lexBaum)
- [Gedeon Moritz](https://github.com/GedeonMoritz)
- [Philip Riecks](https://github.com/PharmaPhil)
- [Fabian Otto](https://github.com/BoboDance)
- [Daniel Wehner](https://github.com/DaWe1992)

---
## Docker
- https://hub.docker.com/r/jreichwald/dhbw_oip/
- [docker-compose.yml](docker-compose.yml)
- [RabbitMQ Client](http://127.0.0.1:15672/)
  - User: guest
  - Password: guest

---
## Algorithms

Three algorithms were contemplated:

### Genetic Algorithm
- [Documentation](/doc/Genetic_Algorithm.pdf)
- [Code](/src/main/java/algorithms/GeneticAlgorithm.java)

### Particle Swarm
- [Documentation](/doc/Particle_Swarm_Algorithm.pdf)
- [Code](/src/main/java/algorithms/particleswarm/ParticleSwarm.java)

### Simulated Annealing
- [Code](/src/main/java/algorithms/simulatedannealing/SimulatedAnnealing.java)

---
## Results

The best values found for each function can be viewed here [Results](https://github.com/DaWe1992/OIP/blob/master/Results.md)

---
## Validation/Optimal values

### Rosenbrock
- FTYPE: 1
- Minimum: 0
- Interval: [-5,+5]

### Reichwald
- FTYPE: 2
- Minimum: ?
- Interval: [-5,+5]

### Rastrigin
- FTYPE: 3
- Minimum: 0
- Interval: [-5,+5]

### Styblinski-Tang
- FTYPE: 4
- Minimum: -39.16617 * n < ... < -39.16616 * n
- Interval: [-5,+5]
