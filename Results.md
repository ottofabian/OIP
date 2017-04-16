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
  - iterations int = `100`;
  - amountOfParticles int = `1000`;

---
## Reichwald
### Genetic Result
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
  
### ParticleSwarm Result
- FTYPE: `2`
- Best solution vector: `[1.0525931022970099, 0.7839979591822157, 0.578063312870114, 0.8906128065671055, 0.3771300772799009, 1.0991589213448378, 0.9901904336035159, 1.308854054125959, 0.3918259184040096, 1.0847002381092352, 1.5500184150862708, 1.2864478203558463, 1.5524252593240533, 0.3840372433395629, 0.428774109775361, 0.7829396575148166, 1.7611026345301752]`
- According f(x): `26.76513908834576]`
- Feasibility: `true`
- Variables:
  - private int c1 = `1`;
  - private int c2 = `2`;
  - iterations int = 100;
  - amountOfParticles int = 1000;

---
## Rastrigin
### Genetic Result
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

### ParticleSwarm Result
- FTYPE: `3`
- Best solution vector: `[6.349064899691115E-4, 0.007741637351010855, 0.01053419324491145, 0.011063045456239373, -0.006536394422350433, -5.20710516909606E-4, 0.009837328793600886, 0.008591702735498252, -0.0019971058032519085, -0.009863785778636558, 0.004850723833421444, 0.003759134423799715, 0.006201482706935713, -0.003960651512426305, 0.012359162972353843, 0.014634114849655155, 0.014353456533152897]`
- Feasibility: `true`
- According f(x): `0.2556568483`
- Variables:
  - private int c1 = `1`;
  - private int c2 = `2`;
  - iterations int = 100;
  - amountOfParticles int = 1000;

---
## Styblinski-Tang

### Genetic Result
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
  
  ### ParticleSwarm Result
 - FTYPE: `4`
 - Best solution vector: `[-3.067416453983263, -2.5296817295763248, -2.927195170286744, -2.5724909017812365, -2.8902715921709152, -2.6529514505560066, -3.097014002793145, -3.1763056109684875, -2.945038506948203, -2.8436533514030273, -2.7857785056519226, -2.6461111747458546, -2.8546981827692406, -3.0177214201937503, -3.0515507023417623, -2.597487069756916, -2.8329519293541168]`
 - According f(x): `-654.8300459991199`
- Feasibility: `true`
- Variables:
  - private int c1 = `1`;
  - private int c2 = `2`;
  - iterations int = 100;
  - amountOfParticles int = 1000;
