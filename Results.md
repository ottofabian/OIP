# Results

## Algorithms
- [Rosenbrock](#rosenbrock)
- [Reichwald](#reichwald)
- [Rastrigin](#rastrigin)
- [Styblinski-Tang](#styblinski-tang)

---

## Rosenbrock
### Genetic Result
- FTYPE: `1`
- Distance: `6.063562737341757`
- Best solution vector: `[0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9270101074449641, 0.9074224141254552, 0.8412666283154362, 0.7410040524275487, 0.5329871309742966]`
- According f(x): `6.063562737341757`
- Feasibility: `true`
- Variables:
  - private double MUTATION = `0.1`;
  - private int MUTATIONS_PER_CREATURE = `6`;
  - SolutionCandidate c = new GeneticAlgorithm(300, 17).evolveXOR((int) Math.pow(2, 10), 0.25);
  - SolutionCandidate sc = mateXoverRandom(c.get(j), c.get(rand)));
  - mutateSwitchN(sc, MUTATIONS_PER_CREATURE);

### ParticleSwarm Result
- FTYPE: `1`
- Distance: `3.3966216191171715E-5`
- Best solution vector: `[0.9966014132516507, 0.9936878304230561]`
- According f(x): `3.3966216191171715E-5`
- Feasibility: `true`
- Variables:
  - private int c1 = `1`;
  - private int c2 = `2`;
  - iterations int = 100;
  - amountOfParicles int = 1000;

---
## Reichwald
- FTYPE: `2`
- Best solution vector: `[0.8237783384896942, 0.3518892063520629, 0.9109868547459707, 0.2444912907568737, 0.2250453742414631, 0.5269987710239545, 0.6336547184838799, 0.13094444432211771, 0.04700293982106696, 0.05395867963736878, 1.1233258944999869, 0.47344365464473204, 0.26759786383082274, 1.5721195312664522, 1.2428699555231855, 1.3576547910272112, 1.9036254679196905]`
- According f(x): `26.760134619286482`
- Feasibility: `true`
- Variables:
  - private double MUTATION = `0.3`;
  - private int MUTATIONS_PER_CREATURE = `4`;
  - SolutionCandidate c = new GeneticAlgorithm(5000, 17).evolveXOR((int) Math.pow(2, 11), 0.25);
  - sc = mateAvg(c.get(j), c.get(rand));
  - sc = mutateRandom(sc, MUTATIONS_PER_CREATURE);

---
## Rastrigin

- FTYPE: `3`
- Distance: `6.10608478012864E-4`
- Best solution vector: `[-4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4, -4.254954387219456E-4]`
- According f(x): `6.10608478012864E-4`
- Feasibility: `true`
- Variables:
  - private double MUTATION = `0.1`;
  - private int MUTATIONS_PER_CREATURE = `6`;
  - SolutionCandidate c = new GeneticAlgorithm(300, 17).evolveXOR((int) Math.pow(2, 10), 0.25);
  - SolutionCandidate sc = mateXover(c.get(j), c.get(rand));
  - mutateSwitchN(sc, MUTATIONS_PER_CREATURE);

---
## Styblinski-Tang

- FTYPE: `4`
- Distance: `1.0864232956464548E-4`
- Best solution vector: `[-2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117, -2.9031859722600117]`
- According f(x): `-665.8247813576704`
- Feasibility: `true`
- Variables:
  - private double MUTATION = `0.1`;
  - private int MUTATIONS_PER_CREATURE = `6`;
  - SolutionCandidate c = new GeneticAlgorithm(300, 17).evolveOR((int) Math.pow(2, 10), 0.25);
  - SolutionCandidate sc = mateXover(c.get(j), c.get(rand));
  - mutateSwitchN(sc, MUTATIONS_PER_CREATURE);
